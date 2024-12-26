package com.kyryll.costtracking.presentation.screens.transaction_screen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyryll.costtracking.R
import com.kyryll.costtracking.util.TransactionType
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionScreen(
    navigateToMainScreen: () -> Unit,
    transactionViewModel: TransactionScreenViewModel,
    onUpdateTransactions: () -> Unit
) {
    IconButton(onClick = { navigateToMainScreen() }) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 30.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.transaction),
            fontSize = 30.sp
        )

        var transactionAmountValue by remember {
            mutableStateOf("")
        }

        TextField(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .align(Alignment.CenterHorizontally),
            value = transactionAmountValue,
            onValueChange = { newValue ->
                transactionAmountValue = newValue
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        val options = listOf("Groceries", "Taxi", "Electronics", "Restaurant", "Other")
        var exposedMenuExpanded by remember {
            mutableStateOf(false)
        }
        var selectedCategoryValue by remember {
            mutableStateOf(options[0])
        }

        ExposedDropdownMenuBox(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            expanded = exposedMenuExpanded,
            onExpandedChange = {
                exposedMenuExpanded = !exposedMenuExpanded
            }
        ) {
            TextField(
                modifier = Modifier
                    .padding(10.dp)
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                value = selectedCategoryValue,
                onValueChange = {},
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = exposedMenuExpanded)
                },
                readOnly = true
            )

            ExposedDropdownMenu(
                expanded = exposedMenuExpanded,
                onDismissRequest = { exposedMenuExpanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(text = option)
                        },
                        onClick = {
                            selectedCategoryValue = option
                            exposedMenuExpanded = false
                        }
                    )
                }
            }
        }

        Button(
            modifier = Modifier
                .padding(top = 20.dp)
                .align(Alignment.CenterHorizontally),
            onClick = {
                transactionViewModel.addNewTransaction(
                    TransactionType.EXPENSE.value,
                    transactionAmountValue,
                    selectedCategoryValue,
                    LocalDateTime.now().toString()
                ) {
                    if (transactionViewModel.approvedTransaction)
                        onUpdateTransactions()
                }

                navigateToMainScreen()
            },
            enabled = transactionAmountValue.isNotEmpty()
        ) {
            Text(
                text = stringResource(id = R.string.add),
                fontSize = 20.sp
            )
        }
    }
}