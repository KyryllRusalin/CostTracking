package com.kyryll.costtracking.data.remote

import com.kyryll.costtracking.data.remote.dto.CurrentPriceResponse
import retrofit2.Response
import retrofit2.http.GET

interface CoinApi {
    @GET("/v1/bpi/currentprice.json")
    suspend fun getBitcoinCurrentPrice(): CurrentPriceResponse
}