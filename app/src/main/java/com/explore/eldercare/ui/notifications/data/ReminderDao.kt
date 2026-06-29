package com.explore.eldercare.ui.notifications.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ReminderDao {

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    abstract suspend fun addAReminder(reminderEntity : Reminder)

    @Query(" Select * from `reminder-table` ")
    abstract fun getAllReminders() : Flow<List<Reminder>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateReminder(reminderEntity: Reminder)

    @Delete
    abstract fun deleteAReminder(reminderEntity: Reminder)

    @Query("Select * from `reminder-table` where id =:id ")
    abstract fun getAReminderById(id: Long) : Flow<Reminder>

}