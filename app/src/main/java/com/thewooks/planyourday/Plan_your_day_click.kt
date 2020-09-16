package com.thewooks.planyourday

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.plan_your_day_click.*

class Plan_your_day_click : AppCompatActivity() {

    var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.plan_your_day_click)

        val fragmentAdapter = PageAdapter_Main(supportFragmentManager)
        main_viewpager.adapter = fragmentAdapter
        main_tablayout.setupWithViewPager(main_viewpager)

        changeColor_button.setOnClickListener {
            ChangeColor()
            storeCount()
            Log.d("Frag", "$count")
        }
    }

    private fun storeCount() {
        var mSharedPreferences: SharedPreferences = getSharedPreferences("countData", 0)
        var mEditor: SharedPreferences.Editor = mSharedPreferences.edit()
        mEditor.putInt("countData", count)
        mEditor.apply()
        Log.d("storedata", "storeCountActivated$count")
    }


    @SuppressLint("ResourceAsColor")
    open fun ChangeColor() {
        count++
        Log.d("count", "$count")
        if (count == 1) {
            changeColor_button.setBackgroundResource(R.color.MyOrange)
        } else if (count == 2) {
            changeColor_button.setBackgroundResource(R.color.MyYellow)
        } else if (count == 3) {
            changeColor_button.setBackgroundResource(R.color.MyGreen)
        } else if (count == 4) {
            changeColor_button.setBackgroundResource(R.color.MyBlue)
        } else if (count == 5) {
            changeColor_button.setBackgroundResource(R.color.MyIndigo)
        } else if (count == 6) {
            changeColor_button.setBackgroundResource(R.color.MyPurple)
        } else {
            changeColor_button.setBackgroundResource(R.color.MyRed)
            count = 0
        }
    }


}




