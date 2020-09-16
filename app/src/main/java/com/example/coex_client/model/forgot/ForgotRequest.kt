package com.example.coex_client.model.forgot

import com.google.gson.annotations.SerializedName

data class ForgotRequest(
    @SerializedName("email")
    val email : String
)