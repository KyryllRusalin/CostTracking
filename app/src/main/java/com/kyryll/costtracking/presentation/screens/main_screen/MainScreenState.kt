package com.kyryll.costtracking.presentation.screens.main_screen

import com.kyryll.costtracking.domain.model.UserFullInfoModel

data class MainScreenState(
    val isLoading: Boolean = false,
    val data: UserFullInfoModel? = null,
    val error: String = ""
)
