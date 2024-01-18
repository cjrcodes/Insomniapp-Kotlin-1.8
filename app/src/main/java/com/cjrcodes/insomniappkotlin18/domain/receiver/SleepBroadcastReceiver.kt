import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.cjrcodes.insomniappkotlin18.domain.collection.SleepStatus
//import com.google.android.gms.location.SleepSegmentEvent

class SleepBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        /*if (SleepSegmentEvent.hasEvents(intent)) {
            val sleepSegmentEvents = SleepSegmentEvent.extractEvents(intent)
            for (event in sleepSegmentEvents) {
                when (event.status) {
                    SleepStatus.STATUS_SLEEP -> Log.d("SleepBroadcastReceiver", "User is asleep")
                    SleepStatus.STATUS_AWAKE -> Log.d("SleepBroadcastReceiver", "User is awake")
                    else -> Log.d("SleepBroadcastReceiver", "Unknown sleep status")
                }
            }
        }*/
    }
}
// Compare this snippet from app/src/main/java/com/cjrcodes/insomniappkotlin18/domain/collection/Alarm.kt: