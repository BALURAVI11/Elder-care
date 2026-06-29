package com.explore.eldercare.ui.notifications.data

import kotlinx.coroutines.flow.Flow

class ReminderRepository(private val reminderDao: ReminderDao) {

    suspend fun addReminder(reminder : Reminder){
        reminderDao.addAReminder(reminder)
    }

    fun deleteReminder(reminder: Reminder){
        reminderDao.deleteAReminder(reminder)
    }

    fun getAllReminder() = reminderDao.getAllReminders()

    fun getAReminderById(id : Long) : Flow<Reminder>{
        return reminderDao.getAReminderById(id)
    }

    fun updateAReminder(reminder: Reminder){
        reminderDao.deleteAReminder(reminder)
    }
}