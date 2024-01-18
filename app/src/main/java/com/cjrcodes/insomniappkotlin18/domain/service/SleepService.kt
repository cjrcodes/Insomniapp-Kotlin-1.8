package com.cjrcodes.insomniappkotlin18.domain.service

import SleepBroadcastReceiver
import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
/*import com.google.android.gms.location.ActivityRecognition
import com.google.android.gms.location.ActivityRecognitionClient
import com.google.android.gms.location.SleepSegmentEvent
import com.google.android.gms.location.SleepSegmentRequest*/

class SleepService(private val context: Context) {

   /* private val activityRecognitionClient: ActivityRecognitionClient =
        ActivityRecognition.getClient(context)

    fun requestSleepUpdates() {
        val sleepSegmentRequest = SleepSegmentRequest.getDefaultSleepSegmentRequest()
        val pendingIntent = getPendingIntent()
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACTIVITY_RECOGNITION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        } else {
            val task = activityRecognitionClient.requestSleepSegmentUpdates(
                pendingIntent,
                sleepSegmentRequest
            )
            task.addOnSuccessListener {
                Log.d("SleepService", "Successfully requested sleep updates")
            }
            task.addOnFailureListener { exception ->
                Log.d("SleepService", "Requesting sleep updates failed", exception)
            }
        }

    }

    private fun getPendingIntent(): PendingIntent {
        val intent = Intent(context, SleepBroadcastReceiver::class.java)
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }*/
}