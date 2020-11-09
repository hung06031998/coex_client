package com.example.coex_client.model.booking

import com.google.gson.annotations.SerializedName

class BookingRequest(
    description: String,
    startTime: String,
    numPerson: Int,
    duration: Float,
    roomId: String
) {
    @SerializedName("description")
    private lateinit var description: String
    @SerializedName("startTime")
    private lateinit var startTime: String
    @SerializedName("numPerson")
    private var numPerson: Int = 0
    @SerializedName("duration")
    private var duration: Float = 0F
    @SerializedName("roomId")
    private lateinit var roomId: String
}