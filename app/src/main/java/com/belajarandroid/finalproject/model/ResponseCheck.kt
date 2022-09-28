package com.belajarandroid.finalproject.model

import com.google.gson.annotations.SerializedName

data class ResponseCheck(

	@field:SerializedName("success")
	val success: Success? = null
)

data class Success(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class ResponseCheckIn(

	@SerializedName("latitude")
	val latitude: String? = null,
	@SerializedName("longitude")
	val longitude: String? = null,
	@SerializedName("address")
	val address: String? = null,
	@SerializedName("id_location")
	val id_location: String? = null,
	@SerializedName("id_user")
	val id_user: String? = null
)