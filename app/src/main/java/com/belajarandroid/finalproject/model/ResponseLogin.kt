package com.belajarandroid.finalproject.model

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseLogin (
        @SerializedName("username")
        val username: String? = null,
        @SerializedName("password")
        val password: String? = null
) : Parcelable