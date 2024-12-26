package com.kyryll.costtracking.domain.use_case

import com.kyryll.costtracking.domain.repository.CoinRepository
import javax.inject.Inject

class UpdateLastSessionTimeUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    suspend operator fun invoke(sessionTime: String) {
        repository.updateLastSessionTime(sessionTime)
    }
}