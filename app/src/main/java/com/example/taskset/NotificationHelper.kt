package com.example.taskset

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build

class NotificationHelper {
    companion object {
        fun scheduleNotification(context: Context, taskTimeMillis: Long) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            try {
                // Check if the app can schedule exact alarms
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        taskTimeMillis,
                        pendingIntent
                    )
                } else {
                    // Set up the alarm to trigger at the specified task time
                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        taskTimeMillis,
                        pendingIntent
                    )
                }
            } catch (e: SecurityException) {

                e.printStackTrace()

                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    taskTimeMillis,
                    pendingIntent
                )
            }


        }
    }
}

