package com.cjrcodes.insomniappkotlin18.data.db.mock

import com.cjrcodes.insomniappkotlin18.data.db.AlarmDao
import com.cjrcodes.insomniappkotlin18.data.model.Alarm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MockAlarmDao: AlarmDao {

    private val mockData: MutableList<Alarm> = mutableListOf(
        Alarm(12, 40),
        Alarm(13, 40),
        Alarm(5, 70),
        Alarm(60, 120),
        Alarm(20),
       /* Alarm("6:00", 120),
        Alarm("7:30", 90),
        Alarm("8:00", 69),
        Alarm("9:15", 110),
        Alarm("10:00", 75),
        Alarm("60:00", 40)*/
    )
    override fun getAll(): Flow<List<Alarm>> {
        return flowOf(mockData)
    }

    override fun getById(id: Int): Alarm? {
        TODO("Not yet implemented")
    }

    override fun findAlarmByTime(time: String): Alarm? {
        TODO("Not yet implemented")
    }

    override fun insert(alarm: Alarm): Long {
        TODO("Not yet implemented")
    }

    override fun update(alarm: Alarm) {
        TODO("Not yet implemented")
    }

    override fun delete(alarm: Alarm) {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }

}
