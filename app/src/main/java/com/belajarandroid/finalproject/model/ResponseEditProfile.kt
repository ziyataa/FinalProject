package com.belajarandroid.finalproject.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ResponseEditProfile(

	@field:SerializedName("success")
	val success: SuccessEditProfile? = null
)

data class SuccessEditProfile(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

@Parcelize
data class EditProfile(
	@SerializedName("fullname")
	var fullname: String? = null,
	@SerializedName("address")
	var address: String? = null,
	@SerializedName("position")
	var position: String? = null
) : Parcelable
