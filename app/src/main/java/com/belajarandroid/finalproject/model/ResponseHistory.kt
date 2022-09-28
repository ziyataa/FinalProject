package com.belajarandroid.finalproject.model

import com.google.gson.annotations.SerializedName

data class ResponseHistory(

	@field:SerializedName("success")
	val success: SuccessHistory? = null
)

data class SuccessHistory(

	@field:SerializedName("result")
	val result: List<ResultItemHistory>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class ResultItemHistory(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("status_name")
	val statusName: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("id_location")
	val idLocation: Int? = null,

	@field:SerializedName("latitude_location")
	val latitudeLocation: String? = null,

	@field:SerializedName("path")
	val path: String? = null,

	@field:SerializedName("longitude_location")
	val longitudeLocation: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("address_location")
	val addressLocation: String? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null
)
