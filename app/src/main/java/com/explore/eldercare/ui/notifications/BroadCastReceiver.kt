package com.explore.eldercare.ui.notifications

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.explore.eldercare.R

const val channelID = "ChannelReminders"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"

class BroadCastReceiver : BroadcastReceiver() {

    override fun onReceive(context : Context, intent : Intent) {

        //TODO add floating notifications

        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(intent.getCharSequenceExtra(messageExtra).toString()))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(121,notification)
    }
}