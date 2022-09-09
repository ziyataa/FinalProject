package com.belajarandroid.finalproject.model

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ResponseUser(

	@field:SerializedName("success")
	val success: SuccessLogin? = null
)

@SuppressLint("ParcelCreator")
@Parcelize
@Keep
data class SuccessLogin(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("token")
	val token: String? = null
) : Parcelable
