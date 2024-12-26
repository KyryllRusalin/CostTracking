package com.kyryll.costtracking.domain.model

data class CurrentPriceModel(
    val bpi: BpiModel = BpiModel()
)

data class BpiModel(
    val usd: UsdModel = UsdModel()
)

data class UsdModel(
    val rate: String = ""
)