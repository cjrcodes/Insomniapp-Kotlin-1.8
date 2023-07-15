package com.cjrcodes.insomniappkotlin18.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cjrcodes.insomniappkotlin18.data.model.Alarm

@Dao
interface AlarmDao {
    @Query("SELECT * FROM alarms")
    fun getAll(): List<Alarm>

    @Query("SELECT * FROM alarms WHERE id = :id")
    fun getById(id: Int): Alarm?

    @Query("SELECT * FROM alarms WHERE time = :time")
    fun findAlarmByTime(time: String): Alarm?

    @Insert
    fun insert(alarm: Alarm): Long

    @Update
    fun update(alarm: Alarm)

    @Delete
    fun delete(alarm: Alarm)

    @Query("DELETE FROM alarms")
    fun deleteAll();
}