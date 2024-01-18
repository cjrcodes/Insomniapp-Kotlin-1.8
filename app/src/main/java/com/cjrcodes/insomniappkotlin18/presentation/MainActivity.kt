package com.cjrcodes.insomniappkotlin18.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.TimeText
import androidx.wear.tooling.preview.devices.WearDevices.LARGE_ROUND
import androidx.wear.tooling.preview.devices.WearDevices.SMALL_ROUND
import androidx.wear.tooling.preview.devices.WearDevices.SQUARE
import com.cjrcodes.insomniappkotlin18.data.db.AlarmDao
import com.cjrcodes.insomniappkotlin18.data.db.AlarmDatabase
import com.cjrcodes.insomniappkotlin18.data.db.mock.MockAlarmDao
import com.cjrcodes.insomniappkotlin18.domain.viewmodel.AlarmViewModel
import com.cjrcodes.insomniappkotlin18.domain.viewmodel.mock.MockAlarmViewModel
import com.cjrcodes.insomniappkotlin18.presentation.destinations.ExtendedTimePickerScreenDestination
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
    lateinit var alarmViewModel: AlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alarmDao = AlarmDatabase.getDatabase(this).alarmDao()

       

        lifecycleScope.launch(Dispatchers.IO) {
            val alarms = alarmDao.getAll().first()
            Log.d("Alarms", alarmDao.getAll().first().toString())

        }
        setContent {
            DestinationsNavHost(navGraph = NavGraphs.root) {
                composable(WearAppDestination) {
                    WearApp(alarmViewModel, navigator = destinationsNavigator)
                }
            }
        }
    }
}

@RootNavGraph(start = true) // sets this as the start destination of the default nav graph
@Destination
@Composable
fun WearApp(viewModel: AlarmViewModel, navigator: DestinationsNavigator) {
    val alarms by viewModel.alarms.collectAsState(emptyList())

    InsomniappKotlin18Theme {
        Scaffold(timeText = {
            TimeText()

        }) {

            TileAlarmScreen(
                viewModel,
                navigator,
                onAlarmClick = {},
                onQuickAlarmClick = {
                    navigator.navigate(ExtendedTimePickerScreenDestination)
                },
                onStatisticsMenuClick = {})
        }
    }
}

@Preview(device = SMALL_ROUND, showSystemUi = true, name = "Small Round Screen")
@Composable
fun MainPreviewSmallRound() {
    WearApp(MockAlarmViewModel(MockAlarmDao()), EmptyDestinationsNavigator)
}

@Preview(device = LARGE_ROUND, showSystemUi = true, name = "Large Round Screen")
@Composable
fun MainPreviewLargeRound() {
    WearApp(MockAlarmViewModel(MockAlarmDao()), EmptyDestinationsNavigator)
}

@Preview(device = SQUARE, showSystemUi = true, name = "Square Screen")
@Composable
fun MainPreviewSquare() {
    WearApp(MockAlarmViewModel(MockAlarmDao()), EmptyDestinationsNavigator)
}

