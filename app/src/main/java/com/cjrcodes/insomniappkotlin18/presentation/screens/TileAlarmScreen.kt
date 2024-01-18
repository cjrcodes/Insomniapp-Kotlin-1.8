package com.cjrcodes.insomniappkotlin18.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyColumnDefaults
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import com.cjrcodes.insomniappkotlin18.data.db.mock.MockAlarmDao
import com.cjrcodes.insomniappkotlin18.data.model.Alarm
import com.cjrcodes.insomniappkotlin18.domain.viewmodel.AlarmViewModel
import com.cjrcodes.insomniappkotlin18.domain.viewmodel.mock.MockAlarmViewModel
import com.cjrcodes.insomniappkotlin18.presentation.composable.AlarmButton
import com.cjrcodes.insomniappkotlin18.presentation.composable.AddAlarmButton
import com.cjrcodes.insomniappkotlin18.presentation.composable.StatisticsMenuButton
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.tooling.preview.devices.WearDevices
import com.cjrcodes.insomniappkotlin18.presentation.destinations.ExtendedTimePickerScreenDestination


@Destination
@Composable
fun TileAlarmScreen(
    viewModel: AlarmViewModel = viewModel(),
    navigator: DestinationsNavigator,
    onAlarmClick: (Alarm) -> Unit,
    onQuickAlarmClick: () -> Unit,
    onStatisticsMenuClick: () -> Unit
) {
    val scalingLazyListState = rememberScalingLazyListState(initialCenterItemIndex = 0)

    val alarms by viewModel.alarms.collectAsState()
    val userAlarm by viewModel.userAlarm.collectAsState() // Collect userAlarm state


    ScalingLazyColumn(
        state = scalingLazyListState,
        scalingParams = ScalingLazyColumnDefaults.scalingParams(
            edgeScale = 0.5f,
            minTransitionArea = 0.65f,
            maxTransitionArea = 0.70f
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        userScrollEnabled = false

    ) {

        items(alarms.chunked(3)) { alarms ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp), // space between items
                verticalAlignment = Alignment.CenterVertically // center items vertically
            ) {
                alarms.forEach { alarm ->
                    AlarmButton(
                        Modifier.background(Color.Red), alarm
                    ) { onAlarmClick(alarm) }
                }
            }
        }

        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp), // space between items
                verticalAlignment = Alignment.CenterVertically // center items vertically
            ) {
                userAlarm?.let { alarm ->
                    AlarmButton(
                        modifier = Modifier.background(Color.Green),
                        alarm,
                    ) { onAlarmClick(alarm) }
                }
                AddAlarmButton(
                    modifier = Modifier,
                    onClick = { navigator.navigate(ExtendedTimePickerScreenDestination) })
                StatisticsMenuButton(onClick = onStatisticsMenuClick)

            }

        }
    }
}


val mockAlarmViewModel = MockAlarmViewModel(MockAlarmDao())

@Preview(showBackground = true, device = WearDevices.LARGE_ROUND)
@Composable
fun TileAlarmScreenPreview() {
    TileAlarmScreen(
        mockAlarmViewModel,
        EmptyDestinationsNavigator,
        onAlarmClick = {},
        onQuickAlarmClick = {},
        onStatisticsMenuClick = {})
}

@Preview(showBackground = true, device = WearDevices.SMALL_ROUND)
@Composable
fun SmallPreviewTileList() {
    TileAlarmScreen(
        mockAlarmViewModel,
        EmptyDestinationsNavigator,
        onAlarmClick = {},
        onQuickAlarmClick = {},
        onStatisticsMenuClick = {})
}

@Preview(showBackground = true, device = WearDevices.SQUARE)
@Composable
fun SquarePreviewTileListPreview() {
    TileAlarmScreen(
        mockAlarmViewModel,
        EmptyDestinationsNavigator,
        onAlarmClick = {},
        onQuickAlarmClick = {},
        onStatisticsMenuClick = {})
}
