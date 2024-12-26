package com.kyryll.costtracking.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val transactionId: Int = 0,
    val type: String,
    val amount: String,
    val category: String,
    val date: String,
    val byUserId: Int = 1
)
