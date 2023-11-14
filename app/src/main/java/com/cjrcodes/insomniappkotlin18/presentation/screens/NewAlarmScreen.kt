package com.cjrcodes.insomniappkotlin18.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.scrollBy
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
import com.cjrcodes.insomniappkotlin18.domain.viewmodel.NewAlarmViewModel
import com.cjrcodes.insomniappkotlin18.domain.viewmodel.mock.MockNewAlarmViewModel
import com.cjrcodes.insomniappkotlin18.presentation.composable.UpdateAlarmButton
import com.cjrcodes.insomniappkotlin18.presentation.destinations.WearAppDestination
import com.cjrcodes.insomniappkotlin18.presentation.theme.InsomniappKotlin18Theme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch


@Destination
@Composable
fun NewAlarmScreen(newAlarmViewModel: NewAlarmViewModel, navController: DestinationsNavigator) {

    val newAlarmViewModel: NewAlarmViewModel = hiltViewModel()


    val alarmCreated: State<Boolean> =
        newAlarmViewModel.alarmCreated.collectAsState(initial = false)

// When alarmCreated becomes true, navigate to the next screen
    LaunchedEffect(alarmCreated.value) {
        if (alarmCreated.value) {
            navController.navigate(WearAppDestination)
        }
    }

    val minutesVal = remember { mutableIntStateOf(5) }

    InsomniappKotlin18Theme {

        NewAlarmContent(
            minutesVal = minutesVal
        ) { updateAlarm(newAlarmViewModel, minutesVal) }
    }
}


@Composable
fun NewAlarmContent(
    minutes: List<String> = (1..60).map { it.toString() },
    minutesVal: MutableState<Int>,
    onCreateAlarmClick: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val pickerState = rememberPickerState(minutes.size, 4, true)
    val focusRequester = remember { FocusRequester() }
    val contentDescription by remember { derivedStateOf { "${pickerState.selectedOption + 1}" } }

    Scaffold(


        modifier = Modifier
            .background(
                color = MaterialTheme.colors.onPrimary,
            ),

        timeText = { TimeText() }
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colors.onPrimary,
                ),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {


            Text(
                modifier = Modifier
                    .padding(start = 0.dp, top = 24.dp, end = 0.dp, bottom = 0.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Minutes",
                style = TextStyle(color = Color.White)
            )

            Picker(
                modifier = Modifier

                    .size(100.dp, 100.dp)
                    .align(Alignment.CenterHorizontally)
                    .onRotaryScrollEvent {
                        coroutineScope.launch {
                            pickerState.scrollBy(
                                it.verticalScrollPixels
                            )
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


            UpdateAlarmButton(onClick = onCreateAlarmClick)

        }

    }
}


fun updateAlarm(newAlarmViewModel: NewAlarmViewModel, minutesVal: State<Int>) {
    newAlarmViewModel.updateAlarm(Alarm(minutesVal.value))
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun NewAlarmScreenPreview() {

    //NewAlarmScreen(EmptyDestinationsNavigator)
    val mockNewAlarmViewModel = MockNewAlarmViewModel(MockAlarmDao())
    val minutesVal = remember { mutableIntStateOf(5) }

    NewAlarmContent(
        minutesVal = minutesVal,
        onCreateAlarmClick = {}
    )
}