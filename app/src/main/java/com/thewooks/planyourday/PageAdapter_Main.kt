package com.thewooks.planyourday

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class PageAdapter_Main(fm : FragmentManager) :FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {FragmentTab_1()}
            else -> {FragmentTab_2()}
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "오늘의 목표"
            else -> {return "월간 목표"}
        }
    }
}




//class PageAdapter (fm : FragmentManager) : FragmentStatePagerAdapter(fm){
//    private var fragment1s :ArrayList<FragmentTab_1> = ArrayList()
//
//    override fun getItem(position: Int): Fragment {
//        return fragment1s[position]
//    }
//
//    override fun getCount(): Int {
//        return fragment1s.size
//    }
//
//    fun addItems(fragment1:FragmentTab_1){
//        fragment1s.add(fragment1)
//    }
//
//}