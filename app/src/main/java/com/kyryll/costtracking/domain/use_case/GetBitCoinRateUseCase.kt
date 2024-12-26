package com.kyryll.costtracking.domain.use_case

import com.kyryll.costtracking.data.mapper.toCurrentPriceModel
import com.kyryll.costtracking.domain.model.CurrentPriceModel
import com.kyryll.costtracking.domain.repository.CoinRepository
import com.kyryll.costtracking.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBitCoinRateUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(): Flow<Resource<CurrentPriceModel>> = flow {
        try {
            emit(Resource.Loading())
            val rate = repository.getBitcoinRate()
            emit(Resource.Success(rate.toCurrentPriceModel()))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}