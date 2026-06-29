package com.explore.eldercare.ui.notifications

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.explore.eldercare.R
import com.explore.eldercare.databinding.ActivityNotificationBinding
import com.explore.eldercare.ui.notifications.data.Reminder
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.TextInputEditText

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private lateinit var viewModel: NotificationsViewModel
    private lateinit var adapter: RemindersAdapter
    private lateinit var scheduler: AndroidAlarmScheduler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        createNotificationChannel()
        scheduler = AndroidAlarmScheduler(this)

        binding.fabCreateReminder.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.set_reminder_dialog, null)
            val btnCreate = view.findViewById<AppCompatButton>(R.id.btn_create)
            val message = view.findViewById<TextInputEditText>(R.id.message)
            val timePicker = view.findViewById<TimePicker>(R.id.timePicker)
            val datePicker = view.findViewById<DatePicker>(R.id.datePicker)
            val toggleGroup = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleButton)
            var frequency = "daily"
            dialog.setCancelable(true)
            dialog.setContentView(view)
            dialog.show()
            btnCreate.setOnClickListener {
                if (toggleGroup.checkedButtonId == R.id.btn_once) frequency = "once"
                val time = getTime(timePicker, datePicker)
                val reminder = Reminder(
                    id = time,
                    message = message.text.toString(),
                    frequency = frequency,
                    time = time.toString()
                )
                if (checkNotificationPermission(applicationContext)) {
                    scheduleNotification(reminder)
                }
                message.text?.clear()
                dialog.dismiss()
            }
        }
        adapter = RemindersAdapter()
        viewModel = NotificationsViewModel(scheduler = scheduler)
        val list = viewModel.getAllReminders


        adapter.setReminders(list)

        adapter.overdueListener {
            println("delete pressed")
            Log.d("deletepressed", it.toString())
            viewModel.deleteReminder(it)
        }
        binding.rvReminder.adapter = adapter

    }

    @SuppressLint("ScheduleExactAlarm")
    private fun scheduleNotification(reminder: Reminder) {
        Log.d("newReminder", reminder.toString())
        viewModel.addReminder(reminder)
        adapter.notifyDataSetChanged()
        Toast.makeText(applicationContext, "Reminder is set 😊", Toast.LENGTH_SHORT).show()
    }

    private fun getTime(timePicker: TimePicker, datePicker: DatePicker): Long {
        val minute = timePicker.minute
        val hour = timePicker.hour
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)

        return calendar.timeInMillis
    }

    private fun checkNotificationPermission(context: Context): Boolean {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val isEnabled = notificationManager.areNotificationsEnabled()

        if (!isEnabled) {

            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            startActivity(intent)

            return false
        }

        return true
    }

    private fun createNotificationChannel() {

        val name = "Reminder Channel"
        val description = "A channel for all the notification of reminders"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = description
        channel.enableVibration(true)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }
}