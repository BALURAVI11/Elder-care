package com.explore.eldercare.ui.notifications

import android.app.Application

class ReminderApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}
