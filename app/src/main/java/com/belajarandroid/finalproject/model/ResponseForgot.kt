package com.belajarandroid.finalproject.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseForgot(
    @SerializedName("idcardnumber")
    var idcardnumber: String? = null,
    @SerializedName("password")
    var password: String? = null,
    @SerializedName("confirm_password")
    var confirm_password: String? = null
) : Parcelable
