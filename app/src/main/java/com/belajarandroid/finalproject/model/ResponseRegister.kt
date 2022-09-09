package com.belajarandroid.finalproject.model

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Register(
    @SerializedName("username")
    var username: String? = null,
    @SerializedName("password")
    var password: String? = null,
    @SerializedName("fullname")
    var fullname: String? = null,
    @SerializedName("idcardnumber")
    var idcardnumber: String? = null,
    @SerializedName("address")
    var address: String? = null,
) : Parcelable