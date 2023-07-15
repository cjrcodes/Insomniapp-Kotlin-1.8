package com.cjrcodes.insomniappkotlin18.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.cjrcodes.insomniappkotlin18.data.model.Alarm
import com.cjrcodes.insomniappkotlin18.presentation.WearApp
import java.time.format.DateTimeFormatter
import java.util.Random
import java.util.concurrent.ThreadLocalRandom

@Composable
fun AlarmChip(alarm: Alarm) {

    val formatter = DateTimeFormatter.ofPattern("HH:mm");

    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
        Chip(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),

            label = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onPrimary,
                    text = "${alarm.getFormattedTime()} / ${alarm.maxHeartRateThreshold} BPM"
                )
            },
            onClick = {}
        )
    }
}

}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun AlarmChipPreview() {
    val random = ThreadLocalRandom.current()
    val randomMinute = random.nextInt(0, 60)
    val randomSecond = if (randomMinute == 60) "00" else random.nextInt(0, 60)

    val randomMaxHeartRate = random.nextInt(40, 120)

    val alarm = Alarm(
        time = "$randomMinute:$randomSecond",
        maxHeartRateThreshold = randomMaxHeartRate
    )

    AlarmChip(alarm)
}