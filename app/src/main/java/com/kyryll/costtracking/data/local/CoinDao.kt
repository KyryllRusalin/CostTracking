package com.kyryll.costtracking.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.kyryll.costtracking.data.local.entity.TransactionEntity
import com.kyryll.costtracking.data.local.entity.UserEntity
import com.kyryll.costtracking.data.local.result.UserInfoWithTransactionsResult

@Dao
interface CoinDao {
    @Upsert
    suspend fun upsertTransaction(transaction: TransactionEntity)

    @Upsert
    suspend fun upsertUser(user: UserEntity)

    @Query("UPDATE UserEntity SET bitcoinRate = :bitcoinRate WHERE userId = 1")
    suspend fun updateBitcoinRate(bitcoinRate: String)

    @Query("UPDATE UserEntity SET userBalance = :userBalance WHERE userId = 1")
    suspend fun updateUserBalance(userBalance: String)

    @Query("UPDATE UserEntity SET lastSessionTime = :sessionTime WHERE userId = 1")
    suspend fun updateLastSessionTime(sessionTime: String)

    @Transaction
    @Query("SELECT * FROM UserEntity")
    suspend fun getFullUserInfo(): UserInfoWithTransactionsResult?
}