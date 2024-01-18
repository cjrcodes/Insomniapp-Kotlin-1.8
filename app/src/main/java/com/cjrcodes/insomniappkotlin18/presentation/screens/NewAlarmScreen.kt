package com.cjrcodes.insomniappkotlin18.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Picker
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.rememberPickerState
import com.cjrcodes.insomniappkotlin18.data.db.mock.MockAlarmDao
import com.cjrcodes.insomniappkotlin18.data.model.Alarm
import com.cjrcodes.insomniappkotlin18.domain.viewmodel.AlarmViewModel
import com.cjrcodes.insomniappkotlin18.domain.viewmodel.mock.MockAlarmViewModel
import com.cjrcodes.insomniappkotlin18.presentation.composable.UpdateAlarmButton
import com.cjrcodes.insomniappkotlin18.presentation.destinations.WearAppDestination
import com.cjrcodes.insomniappkotlin18.presentation.theme.InsomniappKotlin18Theme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch


@Destination
@Composable
fun NewAlarmScreen(navController: DestinationsNavigator) {

    val alarmViewModel: AlarmViewModel = hiltViewModel()

    val alarmCreated: State<Boolean> =
        alarmViewModel.alarmUpdated.collectAsState(initial = false)

// When alarmCreated becomes true, navigate to the next screen
    LaunchedEffect(alarmCreated) {
        if (alarmCreated.value) {
            navController.navigate(WearAppDestination)
        }
    }

    val minutesVal = remember { mutableIntStateOf(5) }

    InsomniappKotlin18Theme {

        NewAlarmContent(
            minutesVal = minutesVal
        ) {
                //alarmViewModel.updateAlarm(Alarm(minutesVal.intValue))
        }
    }
}


// Define string resources and constants
object Strings {
    const val Minutes = "Minutes"
}

object Dimensions {
    val PickerSize = 100.dp
}

@Composable
fun NewAlarmContent(
    minutes: List<String> = (1..60).map { it.toString() },
    minutesVal: MutableState<Int>,
    onUpdateQuickAlarmClick: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val pickerState = rememberPickerState(minutes.size, 4, true)
    val contentDescription by remember { derivedStateOf { "${pickerState.selectedOption + 1}" } }

    val focusRequester = remember { FocusRequester() }

    // Request focus when the composable is first composed
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        modifier = Modifier.background(color = MaterialTheme.colors.onPrimary),
        timeText = { TimeText() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colors.onPrimary),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display the minutes text
            Text(
                modifier = Modifier.padding(start = 0.dp, top = 24.dp, end = 0.dp, bottom = 0.dp).align(Alignment.CenterHorizontally),
                text = Strings.Minutes,
                style = TextStyle(color = Color.White)
            )

            // Display the picker
            Picker(
                modifier = Modifier
                    .size(Dimensions.PickerSize)
                    .align(Alignment.CenterHorizontally)
                    .onRotaryScrollEvent {
                        coroutineScope.launch {
                            Log.d("Picker", "onRotaryScrollEvent: ${it.verticalScrollPixels}")
                            if(it.verticalScrollPixels > 0)
                                pickerState.animateScrollToOption(pickerState.selectedOption + 1)
                            else
                                pickerState.animateScrollToOption(pickerState.selectedOption - 1)
                        }
                        true
                    }
                    .focusRequester(focusRequester)
                    .focusable(),
                state = pickerState,
                separation = 10.dp,
                contentDescription = contentDescription,
                userScrollEnabled = true
            ) {
                Text(minutes[it])
                minutesVal.value = it - 1
            }

            // Display the update alarm button
            UpdateAlarmButton(onClick = onUpdateQuickAlarmClick)
        }
    }
}

fun updateQuickAlarm(alarmViewModel: AlarmViewModel, hoursVal: State<Int>, minutesVal: State<Int>) {
    alarmViewModel.updateAlarm(Alarm(hoursVal.value, minutesVal.value))
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun NewAlarmScreenPreview() {

    //NewAlarmScreen(EmptyDestinationsNavigator)
    val mockAlarmViewModel = MockAlarmViewModel(MockAlarmDao())
    val minutesVal = remember { mutableIntStateOf(5) }

    NewAlarmContent(
        minutesVal = minutesVal,
        onUpdateQuickAlarmClick = {}
    )
}