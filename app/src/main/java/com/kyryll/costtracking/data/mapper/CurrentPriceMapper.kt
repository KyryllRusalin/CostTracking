package com.kyryll.costtracking.data.mapper

import com.kyryll.costtracking.data.remote.dto.Bpi
import com.kyryll.costtracking.data.remote.dto.CurrentPriceResponse
import com.kyryll.costtracking.data.remote.dto.Usd
import com.kyryll.costtracking.domain.model.BpiModel
import com.kyryll.costtracking.domain.model.CurrentPriceModel
import com.kyryll.costtracking.domain.model.UsdModel

fun CurrentPriceResponse.toCurrentPriceModel(): CurrentPriceModel {
    return CurrentPriceModel(
        bpi = bpi.toModelBpi()
    )
}

fun Bpi.toModelBpi(): BpiModel {
    return BpiModel(
        usd = usd.toModelUsd()
    )
}

fun Usd.toModelUsd(): UsdModel {
    return UsdModel(
        rate = rate
    )
}