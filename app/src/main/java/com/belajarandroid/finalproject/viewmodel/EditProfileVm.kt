package com.belajarandroid.finalproject.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.belajarandroid.finalproject.model.EditProfile
import com.belajarandroid.finalproject.model.ResponseEditProfile
import com.belajarandroid.finalproject.service.ApiClient
import com.belajarandroid.finalproject.service.ApiInterface
import com.belajarandroid.finalproject.utils.LocalStorageHelper
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class EditProfileVm() : ViewModel(){

    private val loadingState = MutableLiveData<Boolean>()
    private val errorState = MutableLiveData<Boolean>()
    private val responseStatus = MutableLiveData<Int>()
    private val liveDataProductResponse = MutableLiveData<ResponseEditProfile>()

    lateinit var context: Context
    lateinit var localStorageHelper: LocalStorageHelper
    lateinit var apiInterface: ApiInterface

    constructor(_context: Context) : this() {
        this.context = _context
        localStorageHelper = LocalStorageHelper(_context)
        apiInterface = ApiClient.getClient(_context)?.create(ApiInterface::class.java)!!
    }

    fun isLoadingState(): LiveData<Boolean> {
        return loadingState
    }

    fun isErrorState(): LiveData<Boolean> {
        return errorState
    }

    fun isResponseStatus(): LiveData<Int> {
        return responseStatus
    }

    val editUser : LiveData<ResponseEditProfile> get() = liveDataProductResponse
    fun editProfile(auth : String, id : String, data : EditProfile) {
        apiInterface.updateProfile(auth, id, data)?.enqueue(object: Callback<ResponseEditProfile?> {
            override fun onResponse(call: Call<ResponseEditProfile?>, response: Response<ResponseEditProfile?>) {
                loadingState.value = false
                try {
                    responseStatus.value = response.code()
                    if (response.body() == null) {
                        errorState.value = true
                    } else {
                        liveDataProductResponse.value = response.body()
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseEditProfile?>, t: Throwable) {
                loadingState.value = false
                errorState.value = true
                t.printStackTrace()
            }

        })
    }
}