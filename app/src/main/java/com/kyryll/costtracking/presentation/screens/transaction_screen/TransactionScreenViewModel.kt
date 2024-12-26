package com.kyryll.costtracking.presentation.screens.transaction_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyryll.costtracking.domain.model.TransactionModel
import com.kyryll.costtracking.domain.use_case.AddTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionScreenViewModel @Inject constructor(
    private val addTransactionUseCase: AddTransactionUseCase
) : ViewModel() {
    var approvedTransaction by mutableStateOf(true)

    fun addNewTransaction(
        type: String,
        amount: String,
        category: String,
        date: String,
        onUpdate: () -> Unit
    ) {
        val localTransactionModel = TransactionModel(type, amount, category, date)

        viewModelScope.launch(Dispatchers.IO) {
            addTransactionUseCase(localTransactionModel)
            onUpdate()
        }
    }
}