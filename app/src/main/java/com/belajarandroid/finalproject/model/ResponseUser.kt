package com.belajarandroid.finalproject.model

import com.google.gson.annotations.SerializedName

data class ResponseUser(

    @field:SerializedName("success")
    val success: SuccessLogin? = null
)

data class SuccessLogin(

    @field:SerializedName("data_user")
    val dataUser: DataUser? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null,

    @field:SerializedName("token")
    val token: String? = null
)

data class DataUser(

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
