package com.example.coex_client.model.signup

import com.google.gson.annotations.SerializedName

data class SignupResponse(
    @SerializedName("message")
    val message: String
)