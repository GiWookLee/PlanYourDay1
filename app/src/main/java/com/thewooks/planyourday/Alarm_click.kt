package com.thewooks.planyourday

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.Preference
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.alarm_click.*
import kotlinx.android.synthetic.main.plan_your_day_click.*
import java.util.*


class Alarm_click : AppCompatActivity() {

    private var hour: Int = 0
    private var minute: Int = 0
    private lateinit var alarmManager: AlarmManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alarm_click)

        showAlarmScreen()
        time_needSetting.text = getAlarmData()

        alarm_setting.setOnClickListener {
            clickSetting()
            showAlarmScreen()
            time_needSetting.text = getAlarmData()

        }
        alarm_cancel.setOnClickListener {
            clickCancel()
            clearData()
            showAlarmScreen()
        }

    }

    private fun clearData() {
        var mSharedPreferences: SharedPreferences = getSharedPreferences("alarm_data", 0)
        var mEditor: SharedPreferences.Editor = mSharedPreferences.edit()
        mEditor.clear()
        mEditor.apply()
    }


    private fun storeAlarmData(){
        var mSharedPreferences : SharedPreferences = getSharedPreferences("alarm_data", 0)
        var mEditor : SharedPreferences.Editor = mSharedPreferences.edit()
        mEditor.putString("alarm_data", "${timePicker.hour}시 ${timePicker.minute} 분")
        mEditor.apply()
    }

    private fun getAlarmData() : String?{
        var mSharedPreferences : SharedPreferences = getSharedPreferences("alarm_data", 0)
        var getAlarm : String? = mSharedPreferences.getString("alarm_data", "")
        return getAlarm
    }

private fun showAlarmScreen() {
    var mSharedPreferences : SharedPreferences = getSharedPreferences("alarm_data", 0)
    var getAlarm : String? = mSharedPreferences.getString("alarm_data", "")
    if(getAlarm == ""){
        alarm_on_layout.visibility = View.INVISIBLE
        alarm_off_layout.visibility = View.VISIBLE
    }else{
        alarm_on_layout.visibility = View.VISIBLE
        alarm_off_layout.visibility = View.INVISIBLE
    }

}

    private fun clickSetting() {

        var intent: Intent = Intent(this, AlarmNotifier::class.java)
        var pIntent: PendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        hour = timePicker.hour
        minute = timePicker.minute

        var alarmRingTime: Calendar = Calendar.getInstance()
        alarmRingTime.set(Calendar.HOUR_OF_DAY, hour)
        alarmRingTime.set(Calendar.MINUTE, minute)
        alarmRingTime.set(Calendar.SECOND, 0)
        alarmRingTime.set(Calendar.MILLISECOND, 0)

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            alarmRingTime.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pIntent
        )

        storeAlarmData()

        Log.d("Click", "SettingClick")
        clickSettingToast()


    }

    private fun clickCancel() {
        var intent: Intent = Intent(this, AlarmNotifier::class.java)
        var pIntent: PendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pIntent)


        clickCancelToast()
    }

    private fun clickSettingToast() {
        val calendarRealtime = Calendar.getInstance()

        timePicker
        val calendarTimepicker = Calendar.getInstance()
        calendarTimepicker.set(Calendar.HOUR_OF_DAY, timePicker.hour)
        calendarTimepicker.set(Calendar.MINUTE, timePicker.minute)
        calendarTimepicker.set(Calendar.SECOND, 0)
        calendarTimepicker.set(Calendar.MILLISECOND, 0)
        val millis = calendarTimepicker.timeInMillis

        if (calendarRealtime.timeInMillis >= millis) {
            Toast.makeText(
                this,
                "알람이 내일 ${timePicker.hour}시 ${timePicker.minute}분 부터 울리기 시작합니다",
                Toast.LENGTH_SHORT
            ).show()
            Log.d("Click", "내일 setAlarmToast")
        } else {
            Toast.makeText(
                this,
                "알람이 오늘 ${timePicker.hour}시 ${timePicker.minute}분 부터 울리기 시작합니다",
                Toast.LENGTH_SHORT
            ).show()
            Log.d("Click", "오늘 setAlarmToast")
        }

    }

    private fun clickCancelToast() {
        Toast.makeText(this, "알람이 취소되었습니다", Toast.LENGTH_SHORT).show()
        Log.d("Click", "setAlarmToast")
    }


}
