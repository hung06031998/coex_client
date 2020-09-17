package com.example.coex_client.model.map

import com.google.gson.annotations.SerializedName;

data class NearbyCWModel(
    @SerializedName("listCoWorking")
    var listCoWorking: List<CWModel>
)

data class CWModel (

    @SerializedName("id")
    var id: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("about")
    var about: String,
    @SerializedName("phone")
    var phone: String,
    @SerializedName("photo")
    var photo: List<Object>,
    @SerializedName("address")
    var address: String,
    @SerializedName("location")
    var location: List<Double>,
    @SerializedName("starRating")
    var starRating: List<Integer>,
    @SerializedName("createdAt")
    var createdAt: String,
    @SerializedName("modifiedAt")
    var modifiedAt: String,
    @SerializedName("userId")
    var userId: String
)