package com.cjrcodes.insomniappkotlin18.presentation.composable

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import com.cjrcodes.insomniappkotlin18.data.model.Alarm

@Composable
fun AlarmButton(modifier: Modifier, alarm: Alarm, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier.size(ButtonDefaults.DefaultButtonSize)
    ) {
        Text(
            text = "${
                if (alarm.hour != 0) if (alarm.minute == 0) {
                    alarm.hour.toString() + "h"
                } 
                else if (alarm.hour < 10) {
                    "0" + alarm.hour + ":"
                } else  alarm.hour.toString() + ":" else ""
            }${
                if (alarm.minute == 0) {
                    ""
                } else if (alarm.minute < 10 && alarm.hour != 0) {
                    "0" + alarm.minute
                } else alarm.minute
            }"
        )
    }
}

@Preview
@Composable
fun AlarmButtonPreview() {
    val alarm = Alarm(0, 5)
    AlarmButton(modifier = Modifier, alarm, onClick = {})
}

@Preview
@Composable
fun AlarmButtonWithHourSub10HoursPreview() {
    val alarm = Alarm(1, 5)
    AlarmButton(modifier = Modifier, alarm, onClick = {})
}

@Preview
@Composable
fun AlarmButtonWithHourPreview() {
    val alarm = Alarm(15, 25)
    AlarmButton(modifier = Modifier, alarm, onClick = {})
}