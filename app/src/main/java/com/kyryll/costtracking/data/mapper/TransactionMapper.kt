package com.kyryll.costtracking.data.mapper

import com.kyryll.costtracking.data.local.entity.TransactionEntity
import com.kyryll.costtracking.domain.model.TransactionModel

fun TransactionModel.toTransactionEntity(): TransactionEntity {
    return TransactionEntity(
        type = type,
        amount = amount,
        category = category,
        date = date
    )
}

fun TransactionEntity.toTransactionModel(): TransactionModel {
    return TransactionModel(
        type = type,
        amount = amount,
        category = category,
        date = date
    )
}