package com.cjrcodes.insomniappkotlin18.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cjrcodes.insomniappkotlin18.data.model.Alarm

@Database(entities = [Alarm::class], version = 1, exportSchema = false)
abstract class AlarmDatabase : RoomDatabase(){
    abstract fun alarmDao(): AlarmDao
}