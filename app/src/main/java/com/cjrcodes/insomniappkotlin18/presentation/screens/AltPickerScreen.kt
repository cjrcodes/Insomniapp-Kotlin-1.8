package com.cjrcodes.insomniappkotlin18.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyColumnDefaults
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@Destination
@Composable
fun AltPickerScreen(navController: DestinationsNavigator) {




}



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MinutePicker(selectedMinute: MutableState<Int>) {
    val scalingLazyListState = rememberScalingLazyListState(initialCenterItemIndex = selectedMinute.value - 1)
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    ScalingLazyColumn(
        state = scalingLazyListState,
        scalingParams = ScalingLazyColumnDefaults.scalingParams(
            edgeScale = 0.5f,
            minTransitionArea = 0.65f,
            maxTransitionArea = 0.70f
        ),
        modifier = Modifier
            .fillMaxSize()
            .onRotaryScrollEvent { event ->
                coroutineScope.launch {
                    val delta = event.verticalScrollPixels
                    val newIndex = (scalingLazyListState.centerItemScrollOffset + delta).coerceIn(0F,
                        59F
                    )
                    scalingLazyListState.animateScrollToItem(newIndex.toInt())
                }
                true
            }
            .focusRequester(focusRequester)
            .focusable(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(60) { index ->
            val minute = index + 1
            Text(
                text = minute.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedMinute.value = minute
                        coroutineScope.launch {
                            scalingLazyListState.animateScrollToItem(index)
                        }
                    }
                    .height(80.dp),
                style = if (minute == selectedMinute.value) {
                    MaterialTheme.typography.body1.copy(fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
                } else {
                    MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Normal, fontSize = 24.sp)
                },
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun AltPickerScreenPreview() {
        val selectedMinute = remember { mutableStateOf(0) }
        MinutePicker(selectedMinute = selectedMinute)
    }