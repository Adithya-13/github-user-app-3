package com.example.githubuserapp3.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.githubuserapp3.R
import com.example.githubuserapp3.ui.DetailActivity.Companion.EXTRA_USERNAME
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        backGroundColor()

        searchActivity.setOnClickListener(this)
        favoriteActivity.setOnClickListener(this)
        settingActivity.setOnClickListener(this)
        aboutActivity.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.searchActivity -> intent(SearchActivity::class.java)
            R.id.favoriteActivity -> intent(FavoriteActivity::class.java)
            R.id.aboutActivity -> {
                val about = Intent(this, DetailActivity::class.java)
                about.putExtra(EXTRA_USERNAME, "adithya-13")
                startActivity(about)
                overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
            }
            R.id.settingActivity -> intent(SettingActivity::class.java)
        }
    }

    private fun backGroundColor() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.navigationBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawableResource(R.drawable.splashscreen_background)
    }

    private fun intent(cls: Class<*>) {
        startActivity(Intent(this, cls))
        overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
    }
}