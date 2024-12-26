package com.kyryll.costtracking.presentation.navigation

import android.os.Build
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kyryll.costtracking.presentation.screens.main_screen.MainScreen
import com.kyryll.costtracking.presentation.screens.main_screen.MainScreenViewModel
import com.kyryll.costtracking.presentation.screens.transaction_screen.TransactionScreen
import com.kyryll.costtracking.presentation.screens.transaction_screen.TransactionScreenViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val mainViewModel: MainScreenViewModel = viewModel<MainScreenViewModel>()
    val transactionViewModel: TransactionScreenViewModel = viewModel<TransactionScreenViewModel>()

    NavHost(
        navController = navController,
        startDestination = MainScreenRoute
    ) {
        composable<MainScreenRoute> {
            MainScreen(
                mainViewModel = mainViewModel,
                navigateToTransactionScreen = {
                    navController.navigate(TransactionScreenRoute)
                }
            )
        }
        composable<TransactionScreenRoute> {
            TransactionScreen(
                transactionViewModel = transactionViewModel,
                navigateToMainScreen = {
                    navController.navigate(MainScreenRoute) {
                        popUpTo(0)
                    }
                },
                onUpdateTransactions = {
                    mainViewModel.getUserInfo()
                }
            )
        }
    }
}