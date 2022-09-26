package com.belajarandroid.finalproject.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.LocaleList
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.belajarandroid.finalproject.R
import com.belajarandroid.finalproject.adapter.AdapterLocation
import com.belajarandroid.finalproject.adapter.ViewPagerAdapter
import com.belajarandroid.finalproject.databinding.FragmentHomeBinding
import com.belajarandroid.finalproject.databinding.FragmentProfileBinding
import com.belajarandroid.finalproject.model.ResultItemLocation
import com.belajarandroid.finalproject.utils.BaseVMF
import com.belajarandroid.finalproject.utils.LocalStorageHelper
import com.belajarandroid.finalproject.viewmodel.CheckInVm
import com.belajarandroid.finalproject.viewmodel.LocationVm
import com.belajarandroid.finalproject.viewmodel.ProfileVm
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_on_boarding.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat

class HomeFragment : Fragment() {

    lateinit var localStorageHelper: LocalStorageHelper
    lateinit var viewModel: LocationVm
    lateinit var viewModelCheckIn: CheckInVm

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    var itemList = arrayListOf<ResultItemLocation>()

    private val adapterLocation: AdapterLocation by lazy {
        AdapterLocation(itemList,
            onItemSelectedListener = { setSelected(it) })
    }

    private fun setSelected(data: ResultItemLocation) {
        itemList.onEach {
            it.isSelected = data.id == it.id
        }
//        data.isSelected = true

        Toast.makeText(requireContext(), "data masuk", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        localStorageHelper = LocalStorageHelper(requireContext())

        val viewModelFactory = BaseVMF(LocationVm(this.requireContext()))
        viewModel = ViewModelProvider(this, viewModelFactory)[LocationVm::class.java]

//        val viewModelFactoryCheckIn = BaseVMF(CheckInVm(this.requireContext()))
//        viewModelCheckIn = ViewModelProvider(this, viewModelFactory)[CheckInVm::class.java]

        var auth = localStorageHelper.getUserToken()
        viewModel.getLocation(auth)

//        rvLocation.adapter = adapterLocation
//        rvLocation.layoutManager = LinearLayoutManager(
//            requireContext(),
//            LinearLayoutManager.VERTICAL, false
//        )

        initLiveData()
        with(binding.rvLocation) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterLocation
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun initLiveData() {
        viewModel.dataLocation.observe(viewLifecycleOwner) {
            if (it != null) {
                val dateFormat = SimpleDateFormat("YYYY-MM-DD HH:MM:SS")
                val date = dateFormat.parse(it.success?.result?.get(0)?.createdDate.toString())
                val dateFormatFinal = SimpleDateFormat("dd - MMMM - yyyy")
                val dateFinal = date?.let { it1 -> dateFormatFinal.format(it1) }

                val dateFormater = SimpleDateFormat("YYYY-MM-DD HH:MM:SS")
                val dater = dateFormater.parse(it.success?.result?.get(0)?.createdDate.toString())
                val daterFormatFinal = SimpleDateFormat("HH:mm")
                val daterFinal = dater?.let { it1 -> daterFormatFinal.format(it1) }

                it.success?.result?.let { it1 -> itemList.addAll(it1) }

                binding.apply {
                    txtDateHome.text = dateFinal
                    txtHourHome.text = daterFinal
                }
            }
        }
    }
}