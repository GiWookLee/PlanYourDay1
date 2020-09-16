package com.thewooks.planyourday

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.instructions_click.*
import kotlinx.android.synthetic.main.plan_your_day_click.*
import java.util.ArrayList
import android.view.View as View

class Instructions_click :AppCompatActivity() {

    private val adapter by lazy { Instruction_Adapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.instructions_click)

        viewpager_howToUse.adapter = Instructions_click@adapter

    }
}
