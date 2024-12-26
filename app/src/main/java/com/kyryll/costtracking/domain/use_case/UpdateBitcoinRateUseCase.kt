package com.kyryll.costtracking.domain.use_case

import com.kyryll.costtracking.domain.repository.CoinRepository
import javax.inject.Inject

class UpdateBitcoinRateUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    suspend operator fun invoke(rate: String) {
        repository.updateBitcoinRate(rate)
    }
}