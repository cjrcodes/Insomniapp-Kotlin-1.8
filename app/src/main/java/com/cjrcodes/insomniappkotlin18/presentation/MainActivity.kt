/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.cjrcodes.insomniappkotlin18.presentation

//import com.cjrcodes.insomniappkotlin18.presentation.destinations.NewAlarmScreenDestination
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import com.cjrcodes.insomniappkotlin18.data.db.AlarmDao
import com.cjrcodes.insomniappkotlin18.data.db.mock.MockAlarmDao
import com.cjrcodes.insomniappkotlin18.data.model.Alarm
import com.cjrcodes.insomniappkotlin18.domain.viewmodel.NewAlarmViewModel
import com.cjrcodes.insomniappkotlin18.domain.viewmodel.mock.MockNewAlarmViewModel
import com.cjrcodes.insomniappkotlin18.presentation.destinations.WearAppDestination
import com.cjrcodes.insomniappkotlin18.presentation.screens.TileAlarmScreen
import com.cjrcodes.insomniappkotlin18.presentation.theme.InsomniappKotlin18Theme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var alarmDao: AlarmDao

    @Inject
    lateinit var newAlarmViewModel: NewAlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {
            //alarmDao.deleteAll();
            val alarms = alarmDao.getAll().first()
            if (alarms.isEmpty()) {
                val alarmMinutes = listOf(5, 10, 15, 20, 30, 45, 60)
                alarmMinutes.forEach { minute ->
                    insertAlarm(newAlarmViewModel, Alarm(minute))
                }
            }

        }
        setContent {
            DestinationsNavHost(navGraph = NavGraphs.root) {
                composable(WearAppDestination) {
                    WearApp(newAlarmViewModel, navigator = destinationsNavigator)
                }
            }
        }
    }


    private fun insertAlarm(newAlarmViewModel: NewAlarmViewModel, alarm: Alarm) {
        lifecycleScope.launch(Dispatchers.IO) {
            newAlarmViewModel.createNewAlarm(alarm)
        }
    }
}

@RootNavGraph(start = true) // sets this as the start destination of the default nav graph
@Destination
@Composable
fun WearApp(viewModel: NewAlarmViewModel, navigator: DestinationsNavigator) {
    val alarms by viewModel.alarms.collectAsState(emptyList())

    InsomniappKotlin18Theme {
        Scaffold(timeText = {
            TimeText()
            Text(
                text = "Minutes",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 15.dp)
            )
        }) {

            TileAlarmScreen(
                viewModel,
                navController = navigator,
                onAlarmClick = {},
                onAddAlarmClick = {},
                onStatisticsMenuClick = {})
        }


    }
}


@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true, name = "Small Round Screen")
@Composable
fun MainPreviewSmallRound() {
    WearApp(MockNewAlarmViewModel(MockAlarmDao()), EmptyDestinationsNavigator)
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true, name = "Large Round Screen")
@Composable
fun MainPreviewLargeRound() {
    WearApp(MockNewAlarmViewModel(MockAlarmDao()), EmptyDestinationsNavigator)
}

@Preview(device = Devices.WEAR_OS_SQUARE, showSystemUi = true, name = "Square Screen")
@Composable
fun MainPreviewSquare() {
    WearApp(MockNewAlarmViewModel(MockAlarmDao()), EmptyDestinationsNavigator)
}

