package com.cjrcodes.insomniappkotlin18.domain.viewmodel.mock

import com.cjrcodes.insomniappkotlin18.data.db.mock.MockAlarmDao
import com.cjrcodes.insomniappkotlin18.domain.viewmodel.NewAlarmViewModel

class MockNewAlarmViewModel : NewAlarmViewModel(MockAlarmDao()){
}