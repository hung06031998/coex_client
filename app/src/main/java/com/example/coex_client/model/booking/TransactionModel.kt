package com.example.coex_client.model.booking

import com.google.gson.annotations.SerializedName

class TransactionModel {
    @SerializedName("id")
    private lateinit var id: String
    @SerializedName("price")
    private var price: Long = 0L
    @SerializedName("createAt")
    private lateinit var createAt: String
    @SerializedName("modifyAt")
    private lateinit var modifyAt: String
    @SerializedName("checkIn")
    private var checkIn: Boolean = false
    @SerializedName("checkOut")
    private var checkOut: Boolean = false
    @SerializedName("earnPoint")
    private var earnPoint: Int = 0
    @SerializedName("bookingRefernce")
    private lateinit var bookingRefernce: String
    @SerializedName("payment")
    private var payment: Boolean = false
    @SerializedName("bookingId")
    private lateinit var bookingId: String
}