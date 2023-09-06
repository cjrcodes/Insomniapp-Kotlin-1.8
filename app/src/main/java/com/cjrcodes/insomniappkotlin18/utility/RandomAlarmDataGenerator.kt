package com.cjrcodes.insomniappkotlin18.utility

import com.cjrcodes.insomniappkotlin18.data.model.Alarm
import java.util.concurrent.ThreadLocalRandom

class RandomAlarmDataGenerator {
    fun generateRandomData(size: Int): List<Alarm> {

        val alarms = mutableListOf<Alarm>()

        val random = ThreadLocalRandom.current()

        for (i in 0 until size) {
            val randomMinute = random.nextInt(0, 60)
          /*  val randomSecond = if (randomMinute == 60) 0 else random.nextInt(0, 60)
            val randomTime = "$randomMinute:$randomSecond"
            val randomMaxHeartRate = random.nextInt(40, 120)*/

            alarms.add(Alarm(randomMinute))
        }
        return alarms

    }


}