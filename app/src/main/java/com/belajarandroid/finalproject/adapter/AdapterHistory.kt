package com.belajarandroid.finalproject.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.belajarandroid.finalproject.view.history.DayFragment
import com.belajarandroid.finalproject.view.history.MonthFragment
import com.belajarandroid.finalproject.view.history.WeekFragment
import com.belajarandroid.finalproject.view.history.YearFragment

class AdapterHistory (activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> return DayFragment()
            1 -> return WeekFragment()
            2 -> return MonthFragment()
            else -> return YearFragment()
        }
    }
}