package com.example.coex_client.model.map

import com.google.gson.annotations.SerializedName;

data class NearbyCWModel(
    @SerializedName("listCoWorking")
    var listCoWorking: List<CWModel>
)
