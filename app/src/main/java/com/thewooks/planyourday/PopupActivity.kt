package com.thewooks.planyourday

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.alarm_click.*
import kotlinx.android.synthetic.main.one_line_item.*
import kotlinx.android.synthetic.main.popup_button.*

open class PopupActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_button)

        button_confirm.setOnClickListener {
            var text = txtText.text.toString()
            var mSharedPreferences : SharedPreferences = getSharedPreferences("textButton", 0)
            var mEditor : SharedPreferences.Editor = mSharedPreferences.edit()
            mEditor.putString("textButton", text)
            mEditor.apply()
            finish()
            Log.d("textButtonShared", "${getSharedPreferences("textButton",0)}")
        }

        button_cancel.setOnClickListener {
            finish()
        }
    }
}

