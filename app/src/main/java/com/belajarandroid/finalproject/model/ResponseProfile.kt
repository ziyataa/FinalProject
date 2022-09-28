package com.belajarandroid.finalproject.model

import com.google.gson.annotations.SerializedName

data class ResponseProfile(

	@field:SerializedName("success")
	val success: SuccessProfile? = null
)

data class ResultProfile(

	@field:SerializedName("idcardnumber")
	val idcardnumber: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("path")
	val path: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("fullname")
	val fullname: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("position")
	val position: String? = null
)

data class SuccessProfile(

	@field:SerializedName("result")
	val result: ResultProfile? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
