package com.belajarandroid.finalproject.view.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.belajarandroid.finalproject.R
import com.belajarandroid.finalproject.adapter.AdapterLocationHistory
import com.belajarandroid.finalproject.databinding.FragmentWeekBinding
import com.belajarandroid.finalproject.databinding.FragmentYearBinding
import com.belajarandroid.finalproject.model.ResultItemHistory
import com.belajarandroid.finalproject.utils.BaseVMF
import com.belajarandroid.finalproject.utils.Constant
import com.belajarandroid.finalproject.utils.LocalStorageHelper
import com.belajarandroid.finalproject.viewmodel.HistoryVm
import kotlinx.android.synthetic.main.fragment_week.*
import kotlinx.android.synthetic.main.fragment_year.*

class WeekFragment : Fragment() {

    lateinit var localStorageHelper: LocalStorageHelper
    lateinit var viewModel: HistoryVm

    private var _binding: FragmentWeekBinding? = null
    private val binding get() = _binding!!

    var pagerList = arrayListOf<ResultItemHistory>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWeekBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        localStorageHelper = LocalStorageHelper(requireContext())

        val viewModelFactory = BaseVMF(HistoryVm(this.requireContext()))
        viewModel = ViewModelProvider(this, viewModelFactory)[HistoryVm::class.java]

        var auth = localStorageHelper.getUserToken()
        var userId = localStorageHelper.getUserId()
        viewModel.getHistory(auth, userId.toString(), Constant.WEEK)

        initLiveData()
    }

    private fun initLiveData() {
        viewModel.isResponseStatus().observe(requireActivity()) {
            if (it != 200) {
                Toast.makeText(requireContext(), "Berhasil mengambil data", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel?.isErrorState()?.observe(requireActivity()) {
            if (it == null) return@observe
            if (it) {
                Toast.makeText(requireContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.dataHistory.observe(requireActivity()) {
            it.success?.result?.let { it1 -> pagerList.addAll(it1) }
            rvWeekHistory.adapter = AdapterLocationHistory(pagerList)
            rvWeekHistory.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)
        }
    }
}