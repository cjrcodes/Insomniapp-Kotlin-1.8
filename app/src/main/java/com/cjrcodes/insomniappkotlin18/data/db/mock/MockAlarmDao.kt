package com.cjrcodes.insomniappkotlin18.data.db.mock

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cjrcodes.insomniappkotlin18.data.db.AlarmDao
import com.cjrcodes.insomniappkotlin18.data.model.Alarm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MockAlarmDao : AlarmDao {

    @Query("SELECT * FROM alarms")
    override fun getAll(): Flow<List<Alarm>> {
        return flowOf(
            listOf(
                Alarm(0, 5),
                Alarm(0, 10),
                Alarm(0, 15),
                Alarm(0, 30),
                Alarm(0, 45),
                Alarm(1, 0) // 60 minutes is equivalent to 1 hour
            )
        )
    }


    @Query("SELECT * FROM alarms WHERE id = :id")
    override fun getById(id: Int): Alarm? {
        return Alarm(0, 5)
    }

    @Query("SELECT * FROM alarms WHERE hour AND minute = :time")
    override fun findAlarmByTime(time: String): Alarm? {
        return Alarm(0, 5)
    }

    @Insert
    override suspend fun insert(alarm: Alarm) {
    }

    @Update
    override fun update(alarm: Alarm) {
    }

    @Delete
    override suspend fun delete(alarm: Alarm) {
    }

    @Query("DELETE FROM alarms")
    override fun deleteAll() {
    }


}
