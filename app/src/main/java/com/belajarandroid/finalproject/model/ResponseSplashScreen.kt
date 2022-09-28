package com.belajarandroid.finalproject.model

import com.google.gson.annotations.SerializedName

data class ResponseSplashScreen(

	@field:SerializedName("success")
	val success: SuccessSplash? = null
)

data class Result(

	@field:SerializedName("image")
	val image: Image? = null
)

data class Image(

	@field:SerializedName("path")
	val path: String? = null,

	@field:SerializedName("name")
	val name: String? = null
)

data class SuccessSplash(

	@field:SerializedName("result")
	val result: Result? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
