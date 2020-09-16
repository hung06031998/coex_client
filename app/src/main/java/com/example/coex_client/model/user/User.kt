package com.example.coex_client.model.user

import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("id")
    val id: String,
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("birth")
    val birth: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("point")
    val point: String
)