package com.belajarandroid.finalproject.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.belajarandroid.finalproject.model.*
import com.belajarandroid.finalproject.service.ApiClient
import com.belajarandroid.finalproject.service.ApiInterface
import com.belajarandroid.finalproject.utils.LocalStorageHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashScreenVm() : ViewModel() {

    private val loadingState = MutableLiveData<Boolean>()
    private val errorState = MutableLiveData<Boolean>()
    private val responseStatus = MutableLiveData<Int>()
    private val liveDataUserResponse = MutableLiveData<ResponseSplashScreen>()
    lateinit var context: Context
    lateinit var localStorageHelper: LocalStorageHelper
    lateinit var apiInterFace: ApiInterface

    constructor(_context: Context) : this() {
        this.context = _context
        localStorageHelper = LocalStorageHelper(_context)
        apiInterFace = ApiClient.getClient(_context)?.create(ApiInterface::class.java)!!
    }

    fun isErrorState(): LiveData<Boolean> {
        return errorState
    }

    fun responseStatus(): LiveData<Int> {
        return responseStatus
    }

    val dataSplash: LiveData<ResponseSplashScreen> get() = liveDataUserResponse
    fun splash() {
        apiInterFace.getSplashScreen()?.enqueue(object : Callback<ResponseSplashScreen?> {
            override fun onResponse(call: Call<ResponseSplashScreen?>, response: Response<ResponseSplashScreen?>) {
                if (response.isSuccessful) {
                    loadingState.value = false
                    errorState.value = false
                    responseStatus.value = response.code()
                    liveDataUserResponse.value = response.body()
                } else {
                    loadingState.value = false
                    errorState.value = true
                    responseStatus.value = response.code()
                }
            }

            override fun onFailure(call: Call<ResponseSplashScreen?>, t: Throwable) {
                loadingState.value = false
                errorState.value = true
                responseStatus.value = 500
            }
        })
    }

}