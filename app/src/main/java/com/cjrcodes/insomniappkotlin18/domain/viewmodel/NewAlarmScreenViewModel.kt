package com.cjrcodes.insomniappkotlin18.domain.viewmodel

import androidx.lifecycle.ViewModel
import com.cjrcodes.insomniappkotlin18.data.db.AlarmDao

class NewAlarmScreenViewModel(private val myDao: AlarmDao) : ViewModel() {
    //val myData = AlarmDao.getAll().asLiveData()

    // other functions to interact with the DAO...
}