package com.belajarandroid.finalproject.model

import com.google.gson.annotations.SerializedName

data class ResponseLocation(

	@field:SerializedName("success")
	val success: SuccessLocation? = null
)

data class ResultItemLocation(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("path")
	val path: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("created_date")
	val createdDate: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null,

	var isSelected : Boolean = false
)

data class SuccessLocation(

	@field:SerializedName("result")
	val result: List<ResultItemLocation>,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
