package com.explore.eldercare.ui.notifications.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [Reminder :: class],
    version = 1,
    exportSchema = false
)

abstract class ReminderDatabase : RoomDatabase() {
    abstract fun reminderDao() : ReminderDao
}