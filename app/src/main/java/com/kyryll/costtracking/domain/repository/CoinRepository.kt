package com.kyryll.costtracking.domain.repository

import com.kyryll.costtracking.data.local.entity.TransactionEntity
import com.kyryll.costtracking.data.local.entity.UserEntity
import com.kyryll.costtracking.data.local.result.UserInfoWithTransactionsResult
import com.kyryll.costtracking.data.remote.dto.CurrentPriceResponse

interface CoinRepository {
    suspend fun getBitcoinRate(): CurrentPriceResponse

    suspend fun addTransaction(transaction: TransactionEntity)

    suspend fun addUser(user: UserEntity)

    suspend fun updateBitcoinRate(rate: String)

    suspend fun updateUserBalance(balance: String)

    suspend fun updateLastSessionTime(sessionTime: String)

    suspend fun getUserFullInfo(): UserInfoWithTransactionsResult?
}