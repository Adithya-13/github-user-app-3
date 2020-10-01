package com.example.githubuserapp3.service

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.githubuserapp3.ui.MainActivity
import com.example.githubuserapp3.ui.SettingActivity.Companion.ALARM_ID_REPEATING
import com.example.githubuserapp3.ui.SettingActivity.Companion.ALARM_MESSAGE
import com.example.githubuserapp3.ui.SettingActivity.Companion.ALARM_TITLE

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val title = intent?.getStringExtra(ALARM_TITLE)
        val message = intent?.getStringExtra(ALARM_MESSAGE)

        val notificationIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)

        context?.let {
            AlarmHelper.showNotification(
                it,
                title ?: "Title",
                message ?: "Message",
                ALARM_ID_REPEATING,
                pendingIntent
            )
        }
    }
}