package com.cjrcodes.insomniappkotlin18.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarms")
data class Alarm(
    @ColumnInfo(name ="time")val time: Int,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
) {
    /*private fun parseTime(time: String): LocalTime {
        val formatter = DateTimeFormatter.ofPattern("h : m")
        return LocalTime.parse(time, formatter)
    }

    fun getFormattedTime(): String {
        var minutes = time.substring(0, time.indexOf(":"))
        var seconds = time.substring(time.indexOf(":") + 1)
        if (minutes.toInt() < 10) {
            minutes = "0$minutes"
        }
        if (seconds.toInt() < 10) {
            seconds = "0$seconds"
        }

        return "$minutes:$seconds";
    }*/


}
