package com.explore.eldercare.ui.notifications

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.explore.eldercare.ui.notifications.data.Reminder

class AndroidAlarmScheduler(
    private val context: Context
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @SuppressLint("ScheduleExactAlarm")
    override fun scheduleReminder(item: Reminder) {
        Log.d("alarmScheduler", item.message)
        val intent = Intent(
            context,
            BroadCastReceiver::class.java
        ).apply {
            putExtra(titleExtra, item.message)
            putExtra(messageExtra, item.frequency)
        }
        if (item.frequency == "once") {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                item.id,
                PendingIntent.getBroadcast(
                    context,
                    item.hashCode(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
        } else {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                item.id,
                AlarmManager.INTERVAL_DAY,
                PendingIntent.getBroadcast(
                    context,
                    item.hashCode(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
        }
    }

    override fun deleteReminder(item: Reminder) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                Intent(context, BroadCastReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}