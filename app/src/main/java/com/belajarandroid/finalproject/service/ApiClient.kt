package com.belajarandroid.finalproject.service

import android.content.Context
import android.content.Intent
import com.belajarandroid.finalproject.utils.LocalStorageHelper
import com.belajarandroid.finalproject.view.LoginActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {
        private fun getRequestHeader(context: Context): OkHttpClient? {
            return OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    var localStorageHelper = LocalStorageHelper(context)
                    println("localstorage " + localStorageHelper.getUserToken())
                    val newRequest: Request = chain.request().newBuilder()
                        .addHeader("apikey", "TuIBt77u7tZHi8n7WqUC")
                        .build()
                    chain.proceed(newRequest)
                }
                .addInterceptor { chain ->
                    var localStorageHelper = LocalStorageHelper(context)
                    val response = chain.proceed(chain.request())
                    if (response.code() == 401) {
                        localStorageHelper.logout()
                        var intent = Intent(context, LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                        return@addInterceptor response
                    }
                    return@addInterceptor response
                }
                .build()
        }

        private var retrofit: Retrofit? = null
        fun getClient(context: Context): Retrofit? {
            retrofit = null
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
//                    .baseUrl("http://8.215.65.108:5000/")
                    .baseUrl("http://server.portlan.id/training_android/public/api/")
                    .client(getRequestHeader(context))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }
}