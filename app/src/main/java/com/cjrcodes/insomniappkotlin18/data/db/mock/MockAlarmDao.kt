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
                Alarm(5),
                Alarm(10),
                Alarm(15),
                Alarm(30),
                Alarm(45),
                Alarm(60)
            )
        )
    }


    @Query("SELECT * FROM alarms WHERE id = :id")
    override fun getById(id: Int): Alarm? {
        return Alarm(5)
    }

    @Query("SELECT * FROM alarms WHERE time = :time")
    override fun findAlarmByTime(time: String): Alarm? {
        return Alarm(5)
    }

    @Insert
    override fun insert(alarm: Alarm): Long {
        return 5
    }

    @Update
    override fun update(alarm: Alarm) {
    }

    @Delete
    override fun delete(alarm: Alarm) {
    }

    @Query("DELETE FROM alarms")
    override fun deleteAll() {
    }


}
