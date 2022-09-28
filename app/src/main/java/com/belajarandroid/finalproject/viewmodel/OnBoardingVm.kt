package com.belajarandroid.finalproject.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.belajarandroid.finalproject.model.ResponseOnBoarding
import com.belajarandroid.finalproject.model.SuccessLogin
import com.belajarandroid.finalproject.service.ApiClient
import com.belajarandroid.finalproject.service.ApiInterface
import com.belajarandroid.finalproject.utils.LocalStorageHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class OnBoardingVm() : ViewModel() {
    private val loadingState = MutableLiveData<Boolean>()
    private val errorState = MutableLiveData<Boolean>()
    private val responseStatus = MutableLiveData<Int>()
    private val liveDataUserResponse = MutableLiveData<ResponseOnBoarding>()
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

    fun isResponseStatus(): LiveData<Int> {
        return responseStatus
    }

    val dataOnBoarding: LiveData<ResponseOnBoarding> get() = liveDataUserResponse
    fun getOnBoarding() {
        apiInterFace.getOnBoarding()?.enqueue(object : Callback<ResponseOnBoarding> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ResponseOnBoarding>,
                response: Response<ResponseOnBoarding>
            ) {
                loadingState.value = false
                try {
                    responseStatus.value = response.code()
                    if (response.body() == null) {
                        errorState.value = true
                    } else {
                        liveDataUserResponse.value = response.body()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseOnBoarding>, t: Throwable) {
                loadingState.value = false
                errorState.value = true
                t.printStackTrace()
            }
        })
    }
}