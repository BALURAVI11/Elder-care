package com.explore.eldercare.ui.notifications

import com.explore.eldercare.ui.notifications.data.Reminder

interface AlarmScheduler {
    fun scheduleReminder(item : Reminder)
    fun deleteReminder(item : Reminder)
}