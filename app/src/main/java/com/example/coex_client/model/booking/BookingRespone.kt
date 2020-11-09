package com.example.coex_client.model.booking

import com.google.gson.annotations.SerializedName

class BookingRespone {
    @SerializedName("id")
    private lateinit var id: String
    @SerializedName("description")
    private lateinit var description: String
    @SerializedName("startTime")
    private lateinit var startTime: String
    @SerializedName("endTime")
    private lateinit var endTime: String
    @SerializedName("numPerson")
    private var numPerson: Int = 0
    @SerializedName("status")
    private lateinit var status: String
    @SerializedName("createAt")
    private lateinit var createAt: String
    @SerializedName("modifyAt")
    private lateinit var modifyAt: String
    @SerializedName("duration")
    private var duration: Float = 0F
    @SerializedName("roomId")
    private lateinit var roomId: String
    @SerializedName("userId")
    private lateinit var userId: String
    @SerializedName("transaction")
    private lateinit var transaction: TransactionModel
}