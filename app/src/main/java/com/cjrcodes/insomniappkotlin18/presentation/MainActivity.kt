/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.cjrcodes.insomniappkotlin18.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.rememberScalingLazyListState
import com.cjrcodes.insomniappkotlin18.data.db.AlarmDao
import com.cjrcodes.insomniappkotlin18.data.db.AlarmDatabase
import com.cjrcodes.insomniappkotlin18.data.model.Alarm
import com.cjrcodes.insomniappkotlin18.presentation.composable.AddAlarmButton
import com.cjrcodes.insomniappkotlin18.presentation.composable.AlarmChip
import com.cjrcodes.insomniappkotlin18.presentation.destinations.NewAlarmScreenDestination
import com.cjrcodes.insomniappkotlin18.presentation.theme.InsomniappKotlin18Theme
import com.cjrcodes.insomniappkotlin18.utility.RandomAlarmDataGenerator
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {
    private lateinit var alarmDao: AlarmDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         val database =
            Room.databaseBuilder(applicationContext, AlarmDatabase::class.java, "alarm-database")
                .build()


        val alarmDao = database.alarmDao()

        deleteAllAlarms(alarmDao)

        val alarmDataGenerator = RandomAlarmDataGenerator()

        val generatedAlarms = alarmDataGenerator.generateRandomData(5)

        for(alarm in generatedAlarms) {
            insertAlarm(alarmDao, alarm)
        }

        lifecycleScope.launch {
            val alarms = withContext(Dispatchers.IO) { alarmDao.getAll() }
            setContent {
                WearApp(alarms)
            }
        }
        // Perform other initialization tasks and set up the UI
        initializeComponents()
        setupListeners()

    }
}

private fun insertAlarm(alarmDao: AlarmDao, alarm: Alarm) {
    runBlocking {
        withContext(Dispatchers.IO) {
            alarmDao.insert(alarm)
        }
    }
}

private fun deleteAllAlarms(alarmDao: AlarmDao) {
    runBlocking {
        withContext(Dispatchers.IO) {
            alarmDao.deleteAll()
        }
    }
}

private fun initializeComponents() {
    // Initialize your components here
}

private fun setupListeners() {
    // Set up listeners for user interactions here
}

@RootNavGraph(start = true) // sets this as the start destination of the default nav graph
@Destination
@Composable
fun WearApp(alarms: List<Alarm>) {
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

            val scalingLazyListState: ScalingLazyListState = rememberScalingLazyListState();

            ScalingLazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colors.onPrimary,
                    ),
                verticalArrangement = Arrangement.Center,
                state = scalingLazyListState
            ) {

                items(alarms.size) { alarm ->
                    AlarmChip(alarm = alarms[alarm])
                }

                //if(alarms.size < maxAlarms) {
                    item {
                        AddAlarmButton {

                        }
                    }
                //}

            }
        }
    }
}

@Destination
@Composable
fun NewAlarmScreen(navigator: DestinationsNavigator) {
    navigator.navigate(NewAlarmScreenDestination)
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun MainPreview() {
    val alarm = Alarm("5 : 2" , 75)
    val alarm2 = Alarm(" 10 : 30", 50)
    val alarm3 = Alarm(" 12 : 3", 120)

    val alarms = listOf(alarm, alarm2, alarm3)

    WearApp(alarms)
}