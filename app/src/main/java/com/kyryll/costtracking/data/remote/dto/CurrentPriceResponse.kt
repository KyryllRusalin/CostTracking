package com.kyryll.costtracking.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CurrentPriceResponse(
    val time: Time,
    val disclaimer: String,
    val chartName: String,
    val bpi: Bpi
)

data class Time(
    val updated: String,
    val updatedISO: String,
    @SerializedName("updateduk")
    val updatedUk: String
)

data class Bpi(
    @SerializedName("USD")
    val usd: Usd
)

data class Usd(
    val rate: String
)

