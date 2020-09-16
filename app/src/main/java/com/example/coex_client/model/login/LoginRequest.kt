package com.example.coex_client.model.login

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email")
    val email :String,

    @SerializedName("password")
    val password: String,

    @SerializedName("firebaseToken")
    val firebaseToken : String
)