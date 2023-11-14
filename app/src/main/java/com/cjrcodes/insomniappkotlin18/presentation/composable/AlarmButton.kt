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
fun AlarmButton(modifier: Modifier, alarm: Alarm, onClick: () -> Unit){
        Button(
            onClick = { alarm },
            modifier = Modifier.size(ButtonDefaults.DefaultButtonSize)
        ) {
            Text(text = alarm.time.toString())
        }
    }

    @Preview
    @Composable
    fun AlarmButtonPreview() {
        val alarm = Alarm(5)
        AlarmButton(modifier = Modifier, alarm, onClick = {})
    }