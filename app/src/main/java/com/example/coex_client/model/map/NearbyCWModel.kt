package com.example.coex_client.model.map

import com.google.gson.annotations.SerializedName;
import java.io.Serializable

data class NearbyCWModel(
    @SerializedName("listCoWorking")
    var listCoWorking: List<CWModel>
)

class CWModel : Serializable{
    @SerializedName("id")
    lateinit var id: String
    @SerializedName("name")
    lateinit var name: String
    @SerializedName("about")
    lateinit var about: String
    @SerializedName("phone")
    lateinit var phone: String
    @SerializedName("photo")
    lateinit var photo: List<Object>
    @SerializedName("address")
    lateinit var address: String
    @SerializedName("location")
    lateinit var location: List<Double>
    @SerializedName("starRating")
    lateinit var starRating: List<Integer>
    @SerializedName("createdAt")
    lateinit var createdAt: String
    @SerializedName("modifiedAt")
    lateinit var modifiedAt: String
    @SerializedName("userId")
    lateinit var userId: String
}
