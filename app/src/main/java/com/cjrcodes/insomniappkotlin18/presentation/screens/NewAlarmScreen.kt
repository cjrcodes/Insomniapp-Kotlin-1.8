package com.cjrcodes.insomniappkotlin18.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Picker
import androidx.wear.compose.material.PickerState
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.rememberScalingLazyListState
import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.Hours
import com.chargemap.compose.numberpicker.HoursNumberPicker
import com.chargemap.compose.numberpicker.NumberPicker
import com.cjrcodes.insomniappkotlin18.R
import com.cjrcodes.insomniappkotlin18.data.db.AlarmDao
import com.cjrcodes.insomniappkotlin18.presentation.composable.AddAlarmButton
import com.cjrcodes.insomniappkotlin18.presentation.composable.AlarmChip
import com.cjrcodes.insomniappkotlin18.presentation.composable.VerticalPicker
import com.cjrcodes.insomniappkotlin18.presentation.theme.InsomniappKotlin18Theme
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch

@Destination
@Composable
fun NewAlarmScreen() {

    InsomniappKotlin18Theme {
        Scaffold(
            timeText = {
                TimeText()
            }
        ) {
            /* If you have enough items in your list, use [ScalingLazyColumn] which is an optimized
             * version of LazyColumn for wear devices with some added features. For more information,
             * see d.android.com/wear/compose.
             */

            val scalingLazyListState = rememberScalingLazyListState();
            val focusRequester = remember { FocusRequester() }
            val coroutineScope = rememberCoroutineScope()

            ScalingLazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colors.onPrimary,
                    ),

                verticalArrangement = Arrangement.Center,
            state = scalingLazyListState
            ) {

                item {
                    //var numberPickerState = remember { mutableStateOf(NumberPicker.State()) }
                    val numbers = (1..60).toList()
                    val numbersState = remember { mutableStateOf(numbers) }
                    val pickerState by remember { mutableStateOf(PickerState(1)) }

                    val contentDescription by remember { derivedStateOf { "${pickerState.selectedOption + 1}" } }

                    //VerticalPicker()
                }
            }
        }
    }

}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun NewAlarmScreenPreview() {
    NewAlarmScreen()
}