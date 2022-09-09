package com.belajarandroid.finalproject.service

import com.belajarandroid.finalproject.model.*
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {

    @Headers("apikey: fad62f49c504ddc9f0698c2ee4d5b9ef6f4baf2cb562559a152bc8533ec63635c392c6bf234a2bdee429f8892faea420fe93675cf8dfec79842ba252b6292932")
    @GET("splashscreen")
    fun getSplashScreen() : Call<ResponseSplashScreen>?

    @Headers("apikey: fad62f49c504ddc9f0698c2ee4d5b9ef6f4baf2cb562559a152bc8533ec63635c392c6bf234a2bdee429f8892faea420fe93675cf8dfec79842ba252b6292932")
    @POST("registration")
    fun register(@Body body: Register): Call<SuccessLogin?>?

    @Headers("apikey: fad62f49c504ddc9f0698c2ee4d5b9ef6f4baf2cb562559a152bc8533ec63635c392c6bf234a2bdee429f8892faea420fe93675cf8dfec79842ba252b6292932")
    @POST("authentication")
    fun login(@Body body: ResponseLogin): Call<SuccessLogin>?

    @Headers("apikey: fad62f49c504ddc9f0698c2ee4d5b9ef6f4baf2cb562559a152bc8533ec63635c392c6bf234a2bdee429f8892faea420fe93675cf8dfec79842ba252b6292932")
    @POST("forgot-password")
    fun forgotPassword(@Body body: ResponseForgot): Call<ResponseForgotPassword>?
}