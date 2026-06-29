package com.explore.eldercare.ui.notifications.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminder-table")
data class Reminder(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 0L,
    @ColumnInfo(name = "reminder-message")
    val message : String = "",
    @ColumnInfo(name = "reminder-time")
    val time : String = "",
    @ColumnInfo(name = "reminder-frequency")
    val frequency : String = ""
)