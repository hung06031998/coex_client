package com.example.coex_client.model.forgot

import com.google.gson.annotations.SerializedName

data class ResetRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("otp")
    val otp: String,
    @SerializedName("newPass")
    val newPass: String,
    @SerializedName("confPass")
    val confPass: String
)