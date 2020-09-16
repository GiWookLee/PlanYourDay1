package com.thewooks.planyourday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clickMain()
        clickAlarm()
        clickInstruction()
        clickCredits()
        clickSettings()

    }

    private fun clickMain() {
        planYourDay_button.setOnClickListener {
            val intent = Intent(this, Plan_your_day_click::class.java)
            startActivity(intent)
        }
    }

    private fun clickAlarm() {
        alarm_button.setOnClickListener {
            val intent = Intent(this, Alarm_click::class.java)
            startActivity(intent)
        }
    }

    private fun clickInstruction() {
        howToUse_button.setOnClickListener {
            val intent = Intent(this, Instructions_click::class.java)
            startActivity(intent)
        }
    }

    private fun clickCredits() {
        credits_button.setOnClickListener {
            val intent = Intent(this, CreditsClick::class.java)
            startActivity(intent)
        }
    }

    private fun clickSettings() {
        settings_button.setOnClickListener {
            val intent = Intent(this, SettingsClick::class.java)
            startActivity(intent)
        }
    }
}
