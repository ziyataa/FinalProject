package com.belajarandroid.finalproject.model

import com.google.gson.annotations.SerializedName

data class ResponseForgotPassword(

	@field:SerializedName("success")
	val success: Success? = null
)

data class Success(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
