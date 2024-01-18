package com.cjrcodes.insomniappkotlin18.domain.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cjrcodes.insomniappkotlin18.data.db.AlarmDao
import com.cjrcodes.insomniappkotlin18.data.model.Alarm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class AlarmViewModel @Inject constructor(
    private val alarmDao: AlarmDao
) : ViewModel() {

    /*open val alarms: Flow<List<Alarm>> = alarmDao.getAll()
        .onStart { emit(emptyList()) }
        .catch { emit(emptyList()) }
        .distinctUntilChanged()*/

    private val _alarms = MutableStateFlow<List<Alarm>>(emptyList())
    open val alarms: StateFlow<List<Alarm>> = _alarms

    // User-editable alarm
    private val _userAlarm = MutableStateFlow<Alarm?>(null)
    val userAlarm: StateFlow<Alarm?> = _userAlarm

    init {
        viewModelScope.launch {
            _alarms.value = listOf(5, 10, 15, 30, 45, 60).map { Alarm(0, it) }
        }
    }

    open fun addAlarm(alarm: Alarm) : Boolean {
        val currentAlarms = _alarms.value.toMutableList()
        val addSuccessful = currentAlarms.add(alarm)
        if (addSuccessful) {
            _alarms.value = currentAlarms
        }
        return addSuccessful
    }

    // Function to update the user-editable alarm
    open fun updateAlarm(alarm: Alarm) {
        val currentAlarms = _alarms.value.toMutableList()
        val index = currentAlarms.indexOfFirst { it.id == alarm.id }
        if (index != -1) {
            currentAlarms[index] = alarm
            _alarms.value = currentAlarms
        }
    }

    open fun deleteAlarm(alarm: Alarm) {
        val currentAlarms = _alarms.value.toMutableList()
        currentAlarms.remove(alarm)
        _alarms.value = currentAlarms
    }

    private lateinit var lifecycleOwner: LifecycleOwner
    /*  private val _alarmCreated = mutableStateOf(false)
      var alarmCreated: Boolean
          get() = _alarmCreated.value
          private set(value) {
              _alarmCreated.value = value
          }*/

    private val _alarmUpdated = MutableStateFlow(false)
    open val alarmUpdated: StateFlow<Boolean> get() = _alarmUpdated

    fun setAlarmCreated(value: Boolean) {
        _alarmUpdated.value = value
    }

    fun setLifecycleOwner(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    val myData = alarmDao.getAll().asLiveData()

}

