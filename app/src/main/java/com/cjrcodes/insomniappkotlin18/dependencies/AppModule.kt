package com.cjrcodes.insomniappkotlin18.dependencies

import android.content.Context
import androidx.room.Room
import com.cjrcodes.insomniappkotlin18.data.db.AlarmDao
import com.cjrcodes.insomniappkotlin18.data.db.AlarmDatabase
import com.cjrcodes.insomniappkotlin18.domain.viewmodel.AlarmViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun provideAlarmDatabase(@ApplicationContext context: Context): AlarmDatabase {
        return Room.databaseBuilder(
            context,
            AlarmDatabase::class.java,
            "alarm-database"
        ).build()
    }
    @Provides
    fun provideNewAlarmViewModel( alarmDao: AlarmDao
    ): AlarmViewModel {
        return AlarmViewModel(alarmDao)
    }

    @Provides
    fun provideAlarmDao(database: AlarmDatabase): AlarmDao {
        return database.alarmDao()
    }

}
