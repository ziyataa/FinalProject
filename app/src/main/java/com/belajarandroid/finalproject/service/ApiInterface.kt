package com.belajarandroid.finalproject.service

import com.belajarandroid.finalproject.model.*
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("attendance/splashscreen")
    fun getSplashScreen(): Call<ResponseSplashScreen>?

    @GET("attendance/onboarding")
    fun getOnBoarding(): Call<ResponseOnBoarding>?

    @POST("attendance/registration")
    fun register(@Body body: Register): Call<ResponseRegis?>?

    @POST("attendance/authentication")
    fun login(@Body body: ResponseLogin): Call<ResponseUser>?

    @PUT("attendance/forgot-password/{id}")
    fun forgotPassword(
        @Path("id") id: String?,
        @Body body: ResponseForgot
    ): Call<ResponseForgotPassword>?

    @GET("attendance/account")
    fun getProfile(
        @Header("Authorization") auth: String?,
        @Query("id_user") id: String?
    ): Call<ResponseProfile>?

    @GET("attendance/location")
    fun getLocation(@Header("Authorization") auth: String?): Call<ResponseLocation>?

    @GET("attendance/history")
    fun getHistory(
        @Header("Authorization") auth: String?,
        @Query("id_user") id: String?,
        @Query("periode") periode: String?
    ): Call<ResponseHistory>?

    @PUT("attendance/update-account/{id}")
    fun updateProfile(
        @Header("Authorization") auth: String?,
        @Path("id") id: String?,
        @Body body: EditProfile
    ): Call<ResponseEditProfile>?

    @POST("attendance/check_in")
    fun checkin(
        @Header("Authorization") auth: String?,
        @Body body: ResponseCheckIn
    ): Call<ResponseCheck>?
}