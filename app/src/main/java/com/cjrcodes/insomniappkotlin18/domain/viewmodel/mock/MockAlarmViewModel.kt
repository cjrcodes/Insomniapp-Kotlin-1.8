package com.cjrcodes.insomniappkotlin18.domain.viewmodel.mock

import androidx.lifecycle.viewModelScope
import com.cjrcodes.insomniappkotlin18.data.db.mock.MockAlarmDao
import com.cjrcodes.insomniappkotlin18.data.model.Alarm
import com.cjrcodes.insomniappkotlin18.domain.viewmodel.AlarmViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MockAlarmViewModel(private val mockAlarmDao: MockAlarmDao) : AlarmViewModel(mockAlarmDao){

    private val _alarmCreated = MutableStateFlow(false)
    override val alarmUpdated: StateFlow<Boolean> get() = _alarmCreated

    override val alarms: StateFlow<List<Alarm>> = MutableStateFlow(
        listOf(
            Alarm(0, 5),
            Alarm(0, 10),
            Alarm(0, 15),
            Alarm(0, 30),
            Alarm(0, 45),
            Alarm(1, 0) // 60 minutes is equivalent to 1 hour
        )
    )


    // Override the createNewAlarm method to simulate the creation of a new alarm
    fun updateUserAlarm(alarm: Alarm) {
        // You can add code here to simulate the creation of a new alarm
        viewModelScope.launch(Dispatchers.IO) {
            val alarmId = mockAlarmDao.insert(alarm)
            _alarmCreated.value = true
        }
    }
}
