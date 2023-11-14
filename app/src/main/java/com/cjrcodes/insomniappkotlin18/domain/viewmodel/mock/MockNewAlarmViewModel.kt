package com.cjrcodes.insomniappkotlin18.domain.viewmodel.mock

import androidx.lifecycle.viewModelScope
import com.cjrcodes.insomniappkotlin18.data.db.mock.MockAlarmDao
import com.cjrcodes.insomniappkotlin18.data.model.Alarm
import com.cjrcodes.insomniappkotlin18.domain.viewmodel.NewAlarmViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class MockNewAlarmViewModel(private val mockAlarmDao: MockAlarmDao) : NewAlarmViewModel(mockAlarmDao){

    private val _alarmCreated = MutableStateFlow(false)
    override val alarmCreated: StateFlow<Boolean> get() = _alarmCreated

    override val alarms: Flow<List<Alarm>> = flowOf(
        listOf(
            Alarm(5),
            Alarm(10),
            Alarm(15),
            Alarm(20),
            Alarm(30),
            Alarm(45),
            Alarm(60)
        )
    )


    // Override the createNewAlarm method to simulate the creation of a new alarm
    override fun createAlarm(alarm: Alarm) {
        // You can add code here to simulate the creation of a new alarm
        viewModelScope.launch(Dispatchers.IO) {
            val alarmId = mockAlarmDao.insert(alarm)
            _alarmCreated.value = true
        }
    }
}
