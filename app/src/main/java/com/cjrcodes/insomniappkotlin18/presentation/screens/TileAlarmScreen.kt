package com.cjrcodes.insomniappkotlin18.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyColumnDefaults
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import com.cjrcodes.insomniappkotlin18.data.db.mock.MockAlarmDao
import com.cjrcodes.insomniappkotlin18.data.model.Alarm
import com.cjrcodes.insomniappkotlin18.domain.viewmodel.NewAlarmViewModel
import com.cjrcodes.insomniappkotlin18.domain.viewmodel.mock.MockNewAlarmViewModel
import com.cjrcodes.insomniappkotlin18.presentation.composable.AddAlarmButton
import com.cjrcodes.insomniappkotlin18.presentation.composable.AlarmButton
import com.cjrcodes.insomniappkotlin18.presentation.composable.StatisticsMenuButton
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator


@Destination
@Composable
fun TileAlarmScreen(
    viewModel: NewAlarmViewModel,
    navController: DestinationsNavigator,
    onAlarmClick: (Alarm) -> Unit,
    onAddAlarmClick: () -> Unit,
    onStatisticsMenuClick: () -> Unit
) {
    val scalingLazyListState = rememberScalingLazyListState(initialCenterItemIndex = 0)

    val alarms by viewModel.alarms.collectAsState(initial = emptyList())


    ScalingLazyColumn(
        state = scalingLazyListState,
        scalingParams = ScalingLazyColumnDefaults.scalingParams(
            edgeScale = 0.5f,
            minTransitionArea = 0.65f,
            maxTransitionArea = 0.70f
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize() // fill the entire screen
    ) {

        items(alarms.dropLast(1).chunked(3)) { alarms ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp), // space between items
                verticalAlignment = Alignment.CenterVertically // center items vertically
            ) {
                alarms.forEach { alarm ->
                    AlarmButton(alarm) { onAlarmClick(alarm) }
                }
            }
        }

        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp), // space between items
                verticalAlignment = Alignment.CenterVertically // center items vertically
            ) {
                if (alarms.isNotEmpty()) {
                    val lastAlarm = alarms.last()
                    AlarmButton(lastAlarm) { onAlarmClick(lastAlarm) }
                }
                AddAlarmButton(onClick = onAddAlarmClick)
                StatisticsMenuButton(onClick = onStatisticsMenuClick)
            }
        }

        /*item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp), // space between items
                verticalAlignment = Alignment.CenterVertically // center items vertically
            ) {
                AlarmButton(Alarm(60)) { }
                AddAlarmButton(onClick = onAddAlarmClick)
                StatisticsMenuButton(onClick = onStatisticsMenuClick)
            }
        }*/
    }
}

/*fun TileAlarmScreen(alarms: List<Alarm>, navController: DestinationsNavigator) {

    val scalingLazyListState = rememberScalingLazyListState()

    //val alarms by viewModel.alarms.collectAsState(initial = emptyList())

    ScalingLazyColumn(
        state = scalingLazyListState,
        scalingParams = ScalingLazyColumnDefaults.scalingParams(
            edgeScale = 0.5f,
            minTransitionArea = 0.65f,
            maxTransitionArea = 0.70f
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize() // fill the entire screen
    ) {
        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp), // space between items
                verticalAlignment = Alignment.CenterVertically // center items vertically
            ) {
                AlarmButton(alarms[0], onClick = { *//*TODO*//* })

                AlarmButton(alarms[1], onClick = { *//*TODO*//* })

            }
        }

        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp), // space between items
                verticalAlignment = Alignment.CenterVertically // center items vertically
            ) {
                AlarmButton(alarms[2], onClick = { *//*TODO*//* })
                AlarmButton(alarms[3], onClick = { *//*TODO*//* })
                AlarmButton(alarms[4], onClick = { *//*TODO*//* })

            }
        }

        // Add the other rows in a similar way

        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp), // space between items
                verticalAlignment = Alignment.CenterVertically // center items vertically
            ) {
                AlarmButton(alarms[5], onClick = { *//*TODO*//* })


                AddAlarmButton {
                    // Handle click event
                }

                StatisticsMenuButton(onClick = { *//*TODO*//* })

            }
        }
    }
}*/

val mockNewAlarmViewModel = MockNewAlarmViewModel(MockAlarmDao())


@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun PreviewTileList() {
    TileAlarmScreen(
        mockNewAlarmViewModel,
        EmptyDestinationsNavigator,
        onAlarmClick = {},
        onAddAlarmClick = {},
        onStatisticsMenuClick = {})
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun SmallPreviewTileList() {
    TileAlarmScreen(
        mockNewAlarmViewModel,
        EmptyDestinationsNavigator,
        onAlarmClick = {},
        onAddAlarmClick = {},
        onStatisticsMenuClick = {})
}

@Preview(device = Devices.WEAR_OS_SQUARE, showSystemUi = true)
@Composable
fun SquarePreviewTileList() {
    TileAlarmScreen(
        mockNewAlarmViewModel,
        EmptyDestinationsNavigator,
        onAlarmClick = {},
        onAddAlarmClick = {},
        onStatisticsMenuClick = {})
}


/*
@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun PreviewTileList() {
    val mockNewAlarmViewModel = MockNewAlarmViewModel(mockAlarmDao = MockAlarmDao())
    TileAlarmScreen(  listOf(
        Alarm(5),
        Alarm(10),
        Alarm(15),
        Alarm(30),
        Alarm(45),
        Alarm(60)
    )
    , EmptyDestinationsNavigator)
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun SmallPreviewTileList() {
    val mockNewAlarmViewModel = MockNewAlarmViewModel(mockAlarmDao = MockAlarmDao())
    TileAlarmScreen(listOf(
        Alarm(5),
        Alarm(10),
        Alarm(15),
        Alarm(30),
        Alarm(45),
        Alarm(60)
    ), EmptyDestinationsNavigator)
}

@Preview(device = Devices.WEAR_OS_SQUARE, showSystemUi = true)
@Composable
fun SquarePreviewTileList() {
    val mockNewAlarmViewModel = MockNewAlarmViewModel(mockAlarmDao = MockAlarmDao())
    TileAlarmScreen(listOf(
        Alarm(5),
        Alarm(10),
        Alarm(15),
        Alarm(30),
        Alarm(45),
        Alarm(60)
    ), EmptyDestinationsNavigator)
}*/
