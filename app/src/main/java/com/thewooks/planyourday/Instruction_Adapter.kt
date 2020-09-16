package com.thewooks.planyourday

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class Instruction_Adapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FragmentTab_Ins_1()
            }
            1 -> {
                FragmentTab_Ins_2()
            }
            2 -> {
                FragmentTab_Ins_3()
            }
            else -> {
                FragmentTab_Ins_4()
            }
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> ""
            1 -> ""
            2 -> ""
            else -> {
                return ""
            }
        }
    }
}