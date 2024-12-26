package com.kyryll.costtracking.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey
    val userId: Int = 1,
    val bitcoinRate: String,
    val userBalance: String,
    val lastSessionTime: String
)