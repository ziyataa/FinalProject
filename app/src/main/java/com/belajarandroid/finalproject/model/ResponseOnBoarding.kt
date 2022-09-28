package com.belajarandroid.finalproject.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ResponseOnBoarding(

	@field:SerializedName("success")
	val success: SuccessOnBoarding? = null
) :Parcelable

@Parcelize
data class ResultItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("path")
	val path: String? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable

@Parcelize
data class SuccessOnBoarding(

	@field:SerializedName("result")
	val result: List<ResultItem>,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
) :Parcelable
