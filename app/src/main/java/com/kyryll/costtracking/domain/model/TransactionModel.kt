package com.kyryll.costtracking.domain.model

data class TransactionModel(
    val type: String,
    val amount: String,
    val category: String,
    val date: String
)
