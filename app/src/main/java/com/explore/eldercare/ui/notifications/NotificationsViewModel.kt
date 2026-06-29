package com.explore.eldercare.ui.notifications

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.explore.eldercare.ui.notifications.data.Reminder
import com.explore.eldercare.ui.notifications.data.ReminderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NotificationsViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository,
    val scheduler: AndroidAlarmScheduler
) : ViewModel() {

    lateinit var  getAllReminders : Flow<List<Reminder>>

    init {
        viewModelScope.launch {
            getAllReminders = reminderRepository.getAllReminder()
        }
    }

    fun addReminder(reminder: Reminder){
        viewModelScope.launch(Dispatchers.IO) {
            reminderRepository.addReminder(reminder)
            scheduler.scheduleReminder(reminder)
        }
    }

    fun updateReminder(reminder: Reminder){
        viewModelScope.launch(Dispatchers.IO) {
            reminderRepository.updateAReminder(reminder)
        }
    }

    fun getReminderById(id: Long) = reminderRepository.getAReminderById(id)

    fun deleteReminder(reminder: Reminder){
        viewModelScope.launch(Dispatchers.IO) {
            scheduler.deleteReminder(reminder)
            reminderRepository.deleteReminder(reminder)
        }
    }

}