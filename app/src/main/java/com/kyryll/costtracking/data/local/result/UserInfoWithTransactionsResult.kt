package com.kyryll.costtracking.data.local.result

import androidx.room.Relation
import com.kyryll.costtracking.data.local.entity.TransactionEntity

data class UserInfoWithTransactionsResult(
    val userId: Int = 0,
    val bitcoinRate: String = "",
    val userBalance: String = "",
    val lastSessionTime: String = "",
    @Relation(parentColumn = "userId", entityColumn = "byUserId")
    val transactions: List<TransactionEntity> = listOf()
)
