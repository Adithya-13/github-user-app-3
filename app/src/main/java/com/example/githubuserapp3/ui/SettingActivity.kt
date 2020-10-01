package com.example.githubuserapp3.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.githubuserapp3.R
import com.example.githubuserapp3.service.AlarmHelper
import kotlinx.android.synthetic.main.activity_setting.*
import java.util.*

class SettingActivity : AppCompatActivity() {

    companion object {
        const val ALARM_ID_REPEATING = 100
        const val ALARM_CHANNEL_ID = "channel_reminder"
        const val ALARM_CHANNEL_NAME = "Reminder Channel"
        const val ALARM_TITLE = "alarm_tittle"
        const val ALARM_MESSAGE = "alarm_message"
        const val SP_REMINDER = "sp_reminder"
        const val SP_COLOR = "sp_color"
    }

    private val tag = SettingActivity::class.simpleName
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backGroundColor()
        setContentView(R.layout.activity_setting)

        sharedPreferences =
            this.getSharedPreferences(SettingActivity::class.simpleName, Context.MODE_PRIVATE)
        languageOnClick()
        reminderOnClick()
    }

    private fun reminderOnClick() {

        val isGreen = sharedPreferences.getBoolean(SP_COLOR, true)

        reminder.setOnClickListener {
            val isActive = sharedPreferences.getBoolean(SP_REMINDER, true)
            if (isActive) {
                onAlarm()
            } else {
                offAlarm()
            }
        }

        if (isGreen) {
            reminderView.setBackgroundResource(R.drawable.ripple_card_green)
        } else {
            reminderView.setBackgroundResource(R.drawable.ripple_card_red)
        }

    }

    private fun onAlarm() {

        reminderView.setBackgroundResource(R.drawable.ripple_card_green)
        sharedPreferences.edit().apply {
            putBoolean(SP_REMINDER, false)
            putBoolean(SP_COLOR, true)
        }.apply()

        AlarmHelper.alarm(
            this,
            "Dicoding",
            getString(R.string.desc_notification),
            ALARM_ID_REPEATING,
            Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 9)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
            }
        )

        Toast.makeText(this, getString(R.string.successful_reminder), Toast.LENGTH_SHORT).show()

    }

    private fun offAlarm() {

        reminderView.setBackgroundResource(R.drawable.ripple_card_red)
        sharedPreferences.edit().apply {
            putBoolean(SP_REMINDER, true)
            putBoolean(SP_COLOR, false)
        }.apply()

        AlarmHelper.cancelAlarm(this, ALARM_ID_REPEATING)
        Toast.makeText(this, getString(R.string.nonactive_reminder), Toast.LENGTH_SHORT).show()

    }

    private fun languageOnClick() {
        language.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }

    fun backButton(view: View) {
        onBackPressed()
        overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
        Log.d(tag, "backButton: onBackPressed")
    }

    private fun backGroundColor() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.navigationBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawableResource(R.drawable.splashscreen_background)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
    }
}