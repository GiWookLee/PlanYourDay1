package com.thewooks.planyourday

import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.Xml
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout

data class OneLineData(
    var checkBox: Boolean,
    var button: String?,
    var timetext: String,
    var timecolor: Int
)
