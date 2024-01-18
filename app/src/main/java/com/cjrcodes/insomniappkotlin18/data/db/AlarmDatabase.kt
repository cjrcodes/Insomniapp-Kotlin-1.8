package com.cjrcodes.insomniappkotlin18.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cjrcodes.insomniappkotlin18.data.model.Alarm

@Database(entities = [Alarm::class], version = 2)
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao


    companion object {
        @Volatile
        private var INSTANCE: AlarmDatabase? = null


        fun getDatabase(context: Context): AlarmDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AlarmDatabase::class.java,
                    "AlarmDatabase"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}
