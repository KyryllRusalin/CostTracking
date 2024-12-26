package com.kyryll.costtracking.domain.use_case

import com.kyryll.costtracking.data.local.entity.UserEntity
import com.kyryll.costtracking.data.mapper.toUserFullInfoModel
import com.kyryll.costtracking.domain.model.UserFullInfoModel
import com.kyryll.costtracking.domain.repository.CoinRepository
import com.kyryll.costtracking.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetUserFullInfoUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(): Flow<Resource<UserFullInfoModel>> = flow {
        try {
            emit(Resource.Loading())
            var userInfo = repository.getUserFullInfo()
            if (userInfo == null) {
                repository.addUser(
                    UserEntity(
                        bitcoinRate = "0",
                        userBalance = "0",
                        lastSessionTime = ""
                    )
                )
                userInfo = repository.getUserFullInfo()
                emit(Resource.Success(userInfo!!.toUserFullInfoModel()))
            } else {
                emit(Resource.Success(userInfo.toUserFullInfoModel()))
            }
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}