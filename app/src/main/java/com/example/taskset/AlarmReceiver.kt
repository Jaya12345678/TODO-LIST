// AlarmReceiver.kt
package com.example.taskset

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationManager
import androidx.core.app.NotificationCompat

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "com.example.taskset.ALARM_TRIGGERED") {
            showNotification(context)
        }
    }

    private fun showNotification(context: Context?) {
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, MyApplication.CHANNEL_ID)
            .setContentTitle("Task Reminder")
            .setContentText("It's time to check your task!")
            .setSmallIcon(R.drawable.ic_notification)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(1, notification)
    }
}
