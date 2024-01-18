package com.cjrcodes.insomniappkotlin18.presentation.screens

import android.text.format.DateFormat
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.tooling.preview.devices.WearDevices
import com.cjrcodes.insomniappkotlin18.data.model.Alarm
import com.cjrcodes.insomniappkotlin18.presentation.theme.InsomniappKotlin18Theme
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.format.DateTimeFormatter

@Destination
@Composable
fun ActiveAlarmScreen(modifier: Modifier, alarm: Alarm) {
    ActiveAlarmScreenContent(alarm)
}

@Composable
fun ActiveAlarmScreenContent(alarm: Alarm) {
    val coroutineScope = rememberCoroutineScope()
    val totalSeconds = alarm.hour * 3600 + alarm.minute * 60 // Convert hours and minutes to seconds
    val timerState = remember { mutableStateOf(totalSeconds) } // Start from total seconds
    val isPaused = remember { mutableStateOf(false) }
    val isAwake = remember { mutableStateOf(true) }
    val context = LocalContext.current // Get the context
    Scaffold {
        TimeText()
        DisposableEffect(key1 = timerState, key2 = isPaused.value) {
            val job =
                coroutineScope.launch(Dispatchers.Default) { // Use Dispatchers.Default to move the timer logic to a background thread
                    while (true) { // Check if the coroutine is still active
                        if (!isActive || timerState.value <= 0) {
                            Log.d("ActiveAlarmScreen", "Coroutine is not active, breaking the loop")
                            break
                        }
                        if (!isPaused.value) {
                            delay(1000L)
                            withContext(Dispatchers.Main) { // Switch back to the main thread to update the UI
                                timerState.value -= 1 // Decrement the timer
                                Log.d(
                                    "ActiveAlarmScreen",
                                    "Timer decremented, current value: ${timerState.value}"
                                )
                                if (timerState.value <= 0) { // When the timer reaches 0
                                    isAwake.value = false
                                    Log.d(
                                        "ActiveAlarmScreen",
                                        "Timer reached 0, setting isAwake to false"
                                    )
                                }
                            }
                        }
                    }
                }

            onDispose {
                job.cancel()
                Log.d("ActiveAlarmScreen", "Coroutine job cancelled")
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val hours = timerState.value / 3600
            val minutes = (timerState.value % 3600) / 60
            val seconds = timerState.value % 60
            val timeString = if (hours > 0) String.format(
                "%02d:%02d:%02d",
                hours,
                minutes,
                seconds
            ) else String.format("%02d:%02d", minutes, seconds)
            Text(text = "Time: $timeString")

            // Calculate the future time when the alarm rings
            val currentTime = java.time.LocalTime.now()
            val alarmTime = currentTime.plusSeconds(timerState.value.toLong())
            val is24HourFormat = DateFormat.is24HourFormat(context)
            val formatter = if (is24HourFormat)
                DateTimeFormatter.ofPattern("HH:mm") else DateTimeFormatter.ofPattern("h:mm a")
            Text(text = "Alarm set around: ${formatter.format(alarmTime)}")

            Text(text = if (isAwake.value) "User is awake" else "User is asleep")
            Button(onClick = {
                isPaused.value = !isPaused.value
                Log.d("ActiveAlarmScreen", "Button clicked, isPaused set to ${isPaused.value}")
            }) {
                if (isPaused.value) {
                    Icon(Icons.Filled.PlayArrow, contentDescription = "Resume")
                    Log.d("Alarm Time Paused Value", "alarmTime: $alarmTime")
                } else {
                    Icon(Icons.Filled.Pause, contentDescription = "Pause")
                    Log.d("New Alarm Time Resumed Value", "alarmTime: $alarmTime")

                }
            }
        }
    }
}


@Preview(showBackground = true, device = WearDevices.LARGE_ROUND)
@Composable
fun ActiveAlarmScreenPreview() {
    InsomniappKotlin18Theme {

        ActiveAlarmScreenContent(Alarm(0, 1))

    }
}

@Preview(showBackground = true, device = WearDevices.LARGE_ROUND)
@Composable
fun ActiveAlarmScreenWithHourPreview() {
    InsomniappKotlin18Theme {
        ActiveAlarmScreenContent(Alarm(1, 5))
    }
}