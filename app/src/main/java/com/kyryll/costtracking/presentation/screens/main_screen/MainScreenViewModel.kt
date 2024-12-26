package com.kyryll.costtracking.presentation.screens.main_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyryll.costtracking.domain.model.TransactionModel
import com.kyryll.costtracking.domain.use_case.AddTransactionUseCase
import com.kyryll.costtracking.domain.use_case.GetBitCoinRateUseCase
import com.kyryll.costtracking.domain.use_case.GetUserFullInfoUseCase
import com.kyryll.costtracking.domain.use_case.UpdateBitcoinRateUseCase
import com.kyryll.costtracking.domain.use_case.UpdateLastSessionTimeUseCase
import com.kyryll.costtracking.util.Resource
import com.kyryll.costtracking.util.substringBeforeDot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getBitCoinRateUseCase: GetBitCoinRateUseCase,
    private val addTransactionUseCase: AddTransactionUseCase,
    private val getUserFullInfoUseCase: GetUserFullInfoUseCase,
    private val updateBitcoinRateUseCase: UpdateBitcoinRateUseCase,
    private val updateLastSessionTimeUseCase: UpdateLastSessionTimeUseCase,
) : ViewModel() {
    private val _userInfo = mutableStateOf(MainScreenState())
    val userInfo: State<MainScreenState> = _userInfo

    init {
        getUserInfo()
        updateLastSessionTime()
    }

    fun getUserInfo() {
        getUserFullInfoUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _userInfo.value = MainScreenState(
                        data = result.data
                    )
                }

                is Resource.Error -> {
                    _userInfo.value = MainScreenState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }

                is Resource.Loading -> {
                    _userInfo.value = MainScreenState(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getBitcoinRate() {
        getBitCoinRateUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    updateBitcoinRateUseCase(
                        result.data?.bpi?.usd?.rate?.substringBeforeDot() ?: "0"
                    )
                    getUserInfo()
                }

                is Resource.Error -> {

                }

                is Resource.Loading -> {

                }
            }
        }.launchIn(viewModelScope)
    }

    fun recharge(type: String, amount: String, category: String, date: String) {
        val localTransactionModel = TransactionModel(type, amount, category, date)

        viewModelScope.launch(Dispatchers.IO) {
            addTransactionUseCase(localTransactionModel)
            getUserInfo()
        }
    }

    fun updateLastSessionTime() {
        val localSessionTime = LocalDateTime.now().toString()

        viewModelScope.launch(Dispatchers.IO) {
            updateLastSessionTimeUseCase(localSessionTime)
        }
    }
}