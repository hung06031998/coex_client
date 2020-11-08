package com.example.coex_client.model.map

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ServiceModel : Serializable {
    @SerializedName("id")
    lateinit var id: String
    @SerializedName("wifi")
    var wifi: Boolean = true
    @SerializedName("conversionCall")
    var conversionCall: Boolean = true
    @SerializedName("drink")
    var drink: Boolean = true
    @SerializedName("printer")
    var printer: Boolean = true
    @SerializedName("airCondition")
    var airCondition: Boolean = true
    @SerializedName("other")
    lateinit var other: List<String>
    @SerializedName("roomId")
    lateinit var roomId: String
}