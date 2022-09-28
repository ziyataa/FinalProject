package com.belajarandroid.finalproject.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.belajarandroid.finalproject.model.ResponseHistory
import com.belajarandroid.finalproject.model.ResponseLocation
import com.belajarandroid.finalproject.service.ApiClient
import com.belajarandroid.finalproject.service.ApiInterface
import com.belajarandroid.finalproject.utils.LocalStorageHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class HistoryVm() : ViewModel() {

    private val loadingState = MutableLiveData<Boolean>()
    private val errorState = MutableLiveData<Boolean>()
    private val responseStatus = MutableLiveData<Int>()
    private val liveDataProductResponse = MutableLiveData<ResponseHistory>()

    lateinit var context: Context
    lateinit var localStorageHelper: LocalStorageHelper
    lateinit var apiInterface: ApiInterface

    constructor(_context: Context) : this() {
        this.context = _context
        localStorageHelper = LocalStorageHelper(_context)
        apiInterface = ApiClient.getClient(_context)?.create(ApiInterface::class.java)!!
    }

    fun isResponseStatus(): LiveData<Int> {
        return responseStatus
    }

    fun isErrorState(): LiveData<Boolean> {
        return errorState
    }

    val dataHistory : LiveData<ResponseHistory> get() = liveDataProductResponse
    fun getHistory(auth : String, id : String, periode : String) {
        apiInterface.getHistory(auth, id, periode)?.enqueue(object : Callback<ResponseHistory> {
            override fun onResponse(
                call: Call<ResponseHistory>,
                response: Response<ResponseHistory>
            ) {
                loadingState.value = false
                try {
                    responseStatus.value = response.code()
                    if (response.body() == null) {
                        errorState.value = true
                    } else {
                        liveDataProductResponse.value = response.body()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseHistory>, t: Throwable) {
                loadingState.value = false
                errorState.value = true
                t.printStackTrace()
            }
        })
    }
}