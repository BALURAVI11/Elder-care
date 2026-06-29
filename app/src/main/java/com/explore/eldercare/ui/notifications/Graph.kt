package com.explore.eldercare.ui.notifications

import android.content.Context
import androidx.room.Room
import com.explore.eldercare.ui.notifications.data.ReminderDatabase
import com.explore.eldercare.ui.notifications.data.ReminderRepository

object Graph{

    lateinit var database : ReminderDatabase

    val reminderRepository by lazy {
        ReminderRepository(database.reminderDao())
    }

    fun provide(context : Context){
        database = Room.databaseBuilder(context,ReminderDatabase::class.java,"reminders.db").build()
    }

}