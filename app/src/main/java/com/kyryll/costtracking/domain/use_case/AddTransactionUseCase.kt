package com.kyryll.costtracking.domain.use_case

import com.kyryll.costtracking.data.mapper.toTransactionEntity
import com.kyryll.costtracking.domain.model.TransactionModel
import com.kyryll.costtracking.domain.repository.CoinRepository
import com.kyryll.costtracking.util.TransactionType
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    suspend operator fun invoke(transaction: TransactionModel) {
        var currentBalance = repository.getUserFullInfo()?.userBalance
        if (transaction.type == TransactionType.RECHARGE.value) {
            currentBalance = (currentBalance?.toInt()?.plus(transaction.amount.toInt())).toString()
        } else {
            currentBalance = (currentBalance?.toInt()?.minus(transaction.amount.toInt())).toString()
        }

        if (currentBalance.toInt() >= 0) {
            repository.addTransaction(transaction.toTransactionEntity())
            repository.updateUserBalance(currentBalance)
        }
    }
}