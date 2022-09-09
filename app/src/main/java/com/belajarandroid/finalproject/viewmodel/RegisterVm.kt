package com.belajarandroid.finalproject.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.belajarandroid.finalproject.model.Register
import com.belajarandroid.finalproject.model.SuccessLogin
import com.belajarandroid.finalproject.service.ApiClient
import com.belajarandroid.finalproject.service.ApiInterface
import com.belajarandroid.finalproject.utils.LocalStorageHelper
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterVm() : ViewModel() {

    private val loadingState = MutableLiveData<Boolean>()
    private val errorState = MutableLiveData<Boolean>()
    private val responseStatus = MutableLiveData<Int>()
    private val liveDataUserResponse = MutableLiveData<SuccessLogin>()
    lateinit var context: Context
    lateinit var localStorageHelper: LocalStorageHelper
    lateinit var apiInterFace: ApiInterface

    constructor(_context: Context) : this() {
        this.context = _context
        localStorageHelper = LocalStorageHelper(_context)
        apiInterFace = ApiClient.getClient(_context)?.create(ApiInterface::class.java)!!
    }

    fun isLoadingState(): LiveData<Boolean> {
        return loadingState
    }

    fun isErrorState(): LiveData<Boolean> {
        return errorState
    }

    fun responseStatus(): LiveData<Int> {
        return responseStatus
    }

    val dataRegister : LiveData<SuccessLogin> get() = liveDataUserResponse

    fun register(userData : Register) {
        apiInterFace?.register(userData)?.enqueue(object : Callback<SuccessLogin?> {
            override fun onResponse(call: Call<SuccessLogin?>, response: Response<SuccessLogin?>) {
                loadingState.value = false
                try {
                    responseStatus.value = response.code()
                    if (response.body() == null) {
                        errorState.value = true
                    } else {
                        liveDataUserResponse.value = response.body()
                    }
                } catch (e: Exception) {
                    errorState.value = true
                }
            }

            override fun onFailure(call: Call<SuccessLogin?>, t: Throwable) {
                loadingState.value = false
                errorState.value = true
                t.printStackTrace()
            }
        })
    }
}