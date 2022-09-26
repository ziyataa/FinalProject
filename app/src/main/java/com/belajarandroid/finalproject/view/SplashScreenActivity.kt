package com.belajarandroid.finalproject.view

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.WindowInsets
import androidx.lifecycle.ViewModelProvider
import com.belajarandroid.finalproject.R
import com.belajarandroid.finalproject.databinding.ActivitySplashScreenBinding
import com.belajarandroid.finalproject.model.ResponseLogin
import com.belajarandroid.finalproject.service.ApiClient
import com.belajarandroid.finalproject.service.ApiInterface
import com.belajarandroid.finalproject.utils.BaseVMF
import com.belajarandroid.finalproject.utils.LocalStorageHelper
import com.belajarandroid.finalproject.viewmodel.RegisterVm
import com.belajarandroid.finalproject.viewmodel.SplashScreenVm
import com.bumptech.glide.Glide

class SplashScreenActivity : AppCompatActivity() {

    lateinit var localStorageHelper: LocalStorageHelper
    lateinit var viewModel: SplashScreenVm
    var apiInterface: ApiInterface? = null
    lateinit var binding: ActivitySplashScreenBinding

    private var firstInstaller: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        localStorageHelper = LocalStorageHelper(this)

        apiInterface = ApiClient.getClient(this)?.create(ApiInterface::class.java)
        val viewModelFactory = BaseVMF(SplashScreenVm(this))
        viewModel = ViewModelProvider(this, viewModelFactory)[SplashScreenVm::class.java]

        firstInstaller = localStorageHelper.getFirstInstall()
        Log.d("abcd" ,firstInstaller.toString())

        viewModel.splash()
        initLiveData()
        initStatusBar()

        binding.progressBar.progress = 0
        localStorageHelper = LocalStorageHelper(this)
    }

    private fun initLiveData() {
        viewModel.dataSplash.observe(this) {
            Glide.with(this)
                .load(it.success?.result?.image?.path)
                .into(binding.imgSplashScreen)

            object : CountDownTimer(5000, 1000) {
                override fun onFinish() {
                    if (localStorageHelper.checkLogin() == true) {
                        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                    } else {
                        if (firstInstaller == true) {
                            startActivity(
                                Intent(
                                    this@SplashScreenActivity,
                                    LoginActivity::class.java
                                )
                            )
                        } else {
                            startActivity(
                                Intent(
                                    this@SplashScreenActivity,
                                    OnBoardingActivity::class.java
                                )
                            )
                        }
                    }
                    finish()
                }

                override fun onTick(millisUntilFinished: Long) {

                }
            }.start()
        }
    }

    private fun initStatusBar() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.show(WindowInsets.Type.systemBars())
            window?.statusBarColor = Color.TRANSPARENT
        } else
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window?.statusBarColor = Color.TRANSPARENT
    }
}