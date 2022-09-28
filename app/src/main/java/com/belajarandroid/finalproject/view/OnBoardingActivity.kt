package com.belajarandroid.finalproject.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.belajarandroid.finalproject.R
import com.belajarandroid.finalproject.adapter.ViewPagerAdapter
import com.belajarandroid.finalproject.databinding.ActivityMainBinding
import com.belajarandroid.finalproject.databinding.ActivityOnBoardingBinding
import com.belajarandroid.finalproject.model.ResponseOnBoarding
import com.belajarandroid.finalproject.model.ResultItem
import com.belajarandroid.finalproject.service.ApiClient
import com.belajarandroid.finalproject.service.ApiInterface
import com.belajarandroid.finalproject.utils.BaseVMF
import com.belajarandroid.finalproject.utils.LocalStorageHelper
import com.belajarandroid.finalproject.viewmodel.LoginVm
import com.belajarandroid.finalproject.viewmodel.OnBoardingVm
import com.belajarandroid.finalproject.viewmodel.SplashScreenVm
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_on_boarding.*

class OnBoardingActivity : AppCompatActivity() {

    lateinit var binding: ActivityOnBoardingBinding

    lateinit var localStorageHelper: LocalStorageHelper

    var apiInterface: ApiInterface? = null
    lateinit var viewModel: OnBoardingVm

    var pagerList = arrayListOf<ResultItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        localStorageHelper = LocalStorageHelper(this)
        apiInterface = ApiClient.getClient(this)?.create(ApiInterface::class.java)
        val viewModelFactory = BaseVMF(OnBoardingVm(this))
        viewModel = ViewModelProvider(this, viewModelFactory)[OnBoardingVm::class.java]

        viewModel.getOnBoarding()

        binding?.apply {
            view_pager.adapter = ViewPagerAdapter(pagerList)
            TabLayoutMediator(indicator, view_pager) { tab, position -> }.attach()

            btnLoginOnBoarding.setOnClickListener {
                localStorageHelper.putFirstInstall(true)
                startActivity(Intent(this@OnBoardingActivity, LoginActivity::class.java))
            }

            btnSignUpOnBoarding.setOnClickListener {
                localStorageHelper.putFirstInstall(true)
                startActivity(Intent(this@OnBoardingActivity, RegisterActivity::class.java))
            }
        }

        initLiveData()

        initStatusBar()
    }

    private fun initLiveData() {
        viewModel?.isResponseStatus()?.observe(this) {
            if (it != 200) {
                Toast.makeText(this, "Berhasil mengambil data", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel?.isErrorState()?.observe(this) {
            if (it == null) return@observe
            if (it) {
                Toast.makeText(this, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.dataOnBoarding.observe(this) {
            it.success?.result?.let { it1 -> pagerList.addAll(it1) }
            view_pager.adapter?.notifyDataSetChanged()
        }
    }

    private fun initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.window?.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            this.window?.decorView?.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }
    }
}