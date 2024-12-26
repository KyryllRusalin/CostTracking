package com.kyryll.costtracking.data.mapper

import com.kyryll.costtracking.data.local.result.UserInfoWithTransactionsResult
import com.kyryll.costtracking.domain.model.UserFullInfoModel

fun UserInfoWithTransactionsResult.toUserFullInfoModel(): UserFullInfoModel {
    return UserFullInfoModel(
        bitcoinRate = bitcoinRate,
        userBalance = userBalance,
        lastSessionTime = lastSessionTime,
        transactions = transactions.map { it.toTransactionModel() }
    )
}