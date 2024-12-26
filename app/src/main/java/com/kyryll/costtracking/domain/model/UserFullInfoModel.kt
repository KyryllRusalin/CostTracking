package com.kyryll.costtracking.domain.model

data class UserFullInfoModel(
    val bitcoinRate: String,
    val userBalance: String,
    val lastSessionTime: String,
    val transactions: List<TransactionModel>
)
