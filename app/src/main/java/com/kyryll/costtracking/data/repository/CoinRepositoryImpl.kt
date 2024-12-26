package com.kyryll.costtracking.data.repository

import com.kyryll.costtracking.data.local.CoinDatabase
import com.kyryll.costtracking.data.local.entity.TransactionEntity
import com.kyryll.costtracking.data.local.entity.UserEntity
import com.kyryll.costtracking.data.local.result.UserInfoWithTransactionsResult
import com.kyryll.costtracking.data.remote.CoinApi
import com.kyryll.costtracking.data.remote.dto.CurrentPriceResponse
import com.kyryll.costtracking.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    val api: CoinApi,
    val db: CoinDatabase
) : CoinRepository {
    private val dao = db.dao

    override suspend fun getBitcoinRate(): CurrentPriceResponse {
        return api.getBitcoinCurrentPrice()
    }

    override suspend fun addTransaction(transaction: TransactionEntity) {
        dao.upsertTransaction(transaction)
    }

    override suspend fun addUser(user: UserEntity) {
        dao.upsertUser(user)
    }

    override suspend fun updateBitcoinRate(rate: String) {
        dao.updateBitcoinRate(rate)
    }

    override suspend fun updateUserBalance(balance: String) {
        dao.updateUserBalance(balance)
    }

    override suspend fun updateLastSessionTime(sessionTime: String) {
        dao.updateLastSessionTime(sessionTime)
    }

    override suspend fun getUserFullInfo(): UserInfoWithTransactionsResult? {
        return dao.getFullUserInfo()
    }
}