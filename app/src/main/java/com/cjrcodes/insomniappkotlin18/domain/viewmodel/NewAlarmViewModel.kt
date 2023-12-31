package com.cjrcodes.insomniappkotlin18.domain.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cjrcodes.insomniappkotlin18.data.db.AlarmDao
import com.cjrcodes.insomniappkotlin18.data.db.mock.MockAlarmDao
import com.cjrcodes.insomniappkotlin18.data.model.Alarm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class NewAlarmViewModel @Inject constructor(
    private val alarmDao: AlarmDao
) : ViewModel() {

    open val alarms: Flow<List<Alarm>> = alarmDao.getAll()
        .onStart { emit(emptyList()) }
        .catch { emit(emptyList()) }
        .distinctUntilChanged()

    private lateinit var lifecycleOwner: LifecycleOwner
    /*  private val _alarmCreated = mutableStateOf(false)
      var alarmCreated: Boolean
          get() = _alarmCreated.value
          private set(value) {
              _alarmCreated.value = value
          }*/

    private val _alarmCreated = MutableStateFlow(false)
    open val alarmCreated: StateFlow<Boolean> get() = _alarmCreated

    fun setLifecycleOwner(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    val myData = alarmDao.getAll().asLiveData()


    open fun createAlarm(alarm: Alarm) {
        viewModelScope.launch(Dispatchers.IO) {
            val alarmId = alarmDao.insert(alarm)
            _alarmCreated.value = true
        }
    }

    fun updateAlarm(alarm: Alarm){
        viewModelScope.launch(Dispatchers.IO) {
            alarmDao.update(alarm)
        }
    }


}

class NewAlarmModelPreview(val dynamicValue: StateFlow<String> = MutableStateFlow("no data")) :
    NewAlarmViewModel(
        MockAlarmDao()
    ) {

}
