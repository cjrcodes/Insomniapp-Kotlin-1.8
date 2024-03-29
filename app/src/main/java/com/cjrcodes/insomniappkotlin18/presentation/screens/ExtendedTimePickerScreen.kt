package com.cjrcodes.insomniappkotlin18.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Snackbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.rememberPickerState
import androidx.wear.tooling.preview.devices.WearDevices.LARGE_ROUND
import androidx.wear.tooling.preview.devices.WearDevices.SMALL_ROUND
import androidx.wear.tooling.preview.devices.WearDevices.SQUARE
import com.cjrcodes.insomniappkotlin18.data.db.mock.MockAlarmDao
import com.cjrcodes.insomniappkotlin18.data.model.Alarm
import com.cjrcodes.insomniappkotlin18.domain.viewmodel.AlarmViewModel
import com.cjrcodes.insomniappkotlin18.domain.viewmodel.mock.MockAlarmViewModel
import com.google.android.horologist.composables.TimePicker
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.TextButton

@Destination
@Composable
fun ExtendedTimePickerScreen(
    navController: DestinationsNavigator, alarmViewModel: AlarmViewModel = hiltViewModel()

) {

    ExtendedTimePickerScreenContent(
        navController = navController,
        onTimeConfirm = {},
        time = LocalTime.of(0, 5),
        modifier = Modifier,
        showSeconds = false,
        alarmViewModel
    )
}

@Composable
fun ExtendedTimePickerScreenContent(
    navController: DestinationsNavigator,
    onTimeConfirm: (LocalTime) -> Unit,
    time: LocalTime,
    modifier: Modifier,
    showSeconds: Boolean,
    alarmViewModel: AlarmViewModel

) {

    var pickerState by remember { mutableStateOf(Pair(time.hour, time.minute)) }
    var projectedTime by remember {
        mutableStateOf(
            LocalTime.now().plusHours(pickerState.first.toLong())
                .plusMinutes(pickerState.second.toLong())
        )
    }

    val hourState = rememberPickerState(
        initialNumberOfOptions = 24,
        initiallySelectedOption = time.hour,
    )
    val minuteState = rememberPickerState(
        initialNumberOfOptions = 60,
        initiallySelectedOption = time.minute,
    )

    // Create derived states for each PickerState
    val selectedHour by remember {
        derivedStateOf {
            hourState.selectedOption
        }
    }
    val selectedMinute by
    remember {
        derivedStateOf { minuteState.selectedOption }
    }

    Scaffold(timeText = {

        Row {
            TimeText()
        }


    }) {
        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()
        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center // Center the elements vertically
            ) {

                Spacer(
                    modifier = Modifier.padding(
                        0.dp,
                        8.dp,
                        0.dp,
                        0.dp
                    )
                ) // Add space between the TimeText and the TimePicker


                TimePicker(
                    onTimeConfirm = { newTime ->

                        if (newTime.hour == 0 && newTime.minute == 0) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    "Cannot add alarm with 0 hours and 0 minutes",
                                    "OK"
                                )
                            }
                            Log.d("Alarm", "Cannot add alarm with 0 hours and 0 minutes")
                            return@TimePicker
                        }
                        pickerState = Pair(newTime.hour, newTime.minute)
                        onTimeConfirm(newTime)

                        val success = alarmViewModel.addAlarm(Alarm(newTime.hour, newTime.minute))
                        if (success) {
                            Log.d(
                                "Alarm",
                                "Alarm added successfully " + (newTime.hour.toString() + ":" + newTime.minute.toString())
                            )
                            "with ID: ${alarmViewModel.alarms.value.last().id}"
                        } else {
                            Log.d("Alarm", "Failed to add alarm")
                        }
                    },
                    /*
                                time = LocalTime.of(pickerState.first, pickerState.second),
                */
                    time = LocalTime.of(selectedHour, selectedMinute),

                    modifier = Modifier.height(150.dp),
                    showSeconds = false
                )

                Spacer(modifier = Modifier.height(8.dp)) // Add space between the TimePicker and the AM/PM text

                LaunchedEffect(Unit) {
                    while (true) {
                        delay(1000L)

                        Log.d("pickerState Hours", pickerState.first.toString())
                        Log.d("pickerState Minutes", pickerState.second.toString())

                        Log.d("Time", projectedTime.toString())
                        projectedTime = LocalTime.now().plusHours(pickerState.first.toLong())
                            .plusMinutes(pickerState.second.toLong())

                    }
                }

                Text(
                    text = "Alarm for: " + projectedTime.format(DateTimeFormatter.ofPattern("h:mm a")),
                    modifier = Modifier.align(Alignment.CenterHorizontally) // Center the projected time text
                ) // Display the projected time


            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
        )
    }
}


@Preview(showBackground = true, device = LARGE_ROUND)
@Composable
fun ExtendedTimePickerScreenLargeRoundPreview() {
    ExtendedTimePickerScreen(EmptyDestinationsNavigator, MockAlarmViewModel(MockAlarmDao()))
}

@Preview(showBackground = true, device = SMALL_ROUND)
@Composable
fun ExtendedTimePickerScreenSmallRoundPreview() {
    ExtendedTimePickerScreen(EmptyDestinationsNavigator, MockAlarmViewModel(MockAlarmDao()))
}

@Preview(showBackground = true, device = SQUARE)
@Composable
fun ExtendedTimePickerScreenSquarePreview() {
    ExtendedTimePickerScreen(EmptyDestinationsNavigator, MockAlarmViewModel(MockAlarmDao()))
}
