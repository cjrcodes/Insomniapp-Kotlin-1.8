package com.cjrcodes.insomniappkotlin18.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.MaterialTheme
import com.cjrcodes.insomniappkotlin18.data.model.Alarm
import com.cjrcodes.insomniappkotlin18.presentation.composable.AddAlarmButton
import com.cjrcodes.insomniappkotlin18.presentation.composable.AlarmChip
import com.cjrcodes.insomniappkotlin18.presentation.destinations.NewAlarmScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ListAlarmScreen(alarms : List<Alarm>, navController: DestinationsNavigator) {
    val scalingLazyListState = rememberScalingLazyListState()

    ScalingLazyColumn(
    modifier = Modifier
    .fillMaxSize()
    .background(
    color = MaterialTheme.colors.onPrimary,
    ), verticalArrangement = Arrangement.Center, state = scalingLazyListState,
    userScrollEnabled = true
    ) {
        /*val alarmsFlow: MutableStateFlow<List<Alarm>> = MutableStateFlow(emptyList())
        val alarmsList by viewModel.alarms.collectAsState()*/

        items(alarms.size) { it ->
            val alarm = alarms[it]
            AlarmChip(alarm = alarm)
        }

            item {
                AddAlarmButton {
                    navController.navigate(NewAlarmScreenDestination)
                }
            }


    }
}