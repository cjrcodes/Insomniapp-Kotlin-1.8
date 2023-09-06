package com.cjrcodes.insomniappkotlin18.presentation.composable

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Picker
import androidx.wear.compose.material.PickerState
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberPickerState

@Composable
fun VerticalPicker(
    onMinuteSelected: (PickerState) -> Unit
) {
    val minutes = (1..60).map { it.toString() }
    val pickerState = rememberPickerState(minutes.size)
    val contentDescription by remember { derivedStateOf { "${pickerState.selectedOption + 1}" } }


    val focusRequester = remember { FocusRequester() }


            Picker(
                modifier = Modifier
                    .size(100.dp, 100.dp)
                    .onRotaryScrollEvent { true }
                    .focusRequester(focusRequester)
                    .focusable(),
                state = pickerState,
                separation = 10.dp,
                contentDescription = contentDescription,
            ) {
                Text(minutes[it])
            }
        }



@Preview
@Composable
fun VerticalPickerPreview(){

    VerticalPicker { selectedMinute ->
        // Handle the selected minute here
        // For example, you can print the selected minute to the console
        println("Selected minute: $selectedMinute")
    }
}