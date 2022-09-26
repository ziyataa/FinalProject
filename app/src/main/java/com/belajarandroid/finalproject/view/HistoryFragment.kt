package com.belajarandroid.finalproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.belajarandroid.finalproject.R
import com.belajarandroid.finalproject.adapter.AdapterHistory
import com.belajarandroid.finalproject.databinding.FragmentHistoryBinding
import com.google.android.material.tabs.TabLayoutMediator

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            val sectionPagerAdapter = AdapterHistory(requireContext() as AppCompatActivity)
            vpHistory.adapter = sectionPagerAdapter
            TabLayoutMediator(indicatorHistory, vpHistory) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.day)
                    1 -> tab.text = getString(R.string.week)
                    2 -> tab.text = getString(R.string.month)
                    else -> tab.text = getString(R.string.year)
                }
            }.attach()
        }
    }
}