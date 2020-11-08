package com.example.coex_client.model.map

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class RoomModel : Serializable {
    @SerializedName("id")
    lateinit var id: String
    @SerializedName("name")
    lateinit var name: String
    @SerializedName("about")
    lateinit var about: String
    @SerializedName("price")
    var price: Int = 0
    @SerializedName("photo")
    lateinit var photo: List<Object>
    @SerializedName("maxPerson")
    var maxPerson: Int = 0
    @SerializedName("createdAt")
    lateinit var createdAt: String
    @SerializedName("modifiedAt")
    lateinit var modifiedAt: String
    @SerializedName("coWorkingId")
    lateinit var coWorkingId: String
    @SerializedName("service")
    lateinit var service: ServiceModel
}