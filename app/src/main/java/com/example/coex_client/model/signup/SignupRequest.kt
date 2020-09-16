package com.example.coex_client.model.signup

import com.google.gson.annotations.SerializedName

data class SignupRequest(
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("password")
    val password: String
)