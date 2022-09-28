package com.belajarandroid.finalproject.model

import com.google.gson.annotations.SerializedName

data class ResponseRegis(

	@field:SerializedName("success")
	val success: SuccessRegis? = null
)

data class SuccessRegis(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
