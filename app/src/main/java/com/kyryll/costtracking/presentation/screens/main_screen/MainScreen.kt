package com.kyryll.costtracking.presentation.screens.main_screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.kyryll.costtracking.R
import com.kyryll.costtracking.util.TransactionType
import com.kyryll.costtracking.util.extractTime
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    navigateToTransactionScreen: () -> Unit,
    mainViewModel: MainScreenViewModel
) {
    val userUiState = mainViewModel.userInfo.value

    LaunchedEffect(key1 = userUiState.data?.lastSessionTime) {
        Log.d("KTAG", "MainScreen: $userUiState")
        if (userUiState.data != null) {
            val lastSessionTime = userUiState.data.lastSessionTime
            val shouldFetch = lastSessionTime.isEmpty() || ChronoUnit.HOURS.between(
                LocalDateTime.parse(lastSessionTime),
                LocalDateTime.now()
            ) >= 1

            if (shouldFetch) {
                mainViewModel.getBitcoinRate()
            }
        }
    }

    var isOpenedPopUp by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (userUiState.isLoading) {
            CircularProgressIndicator()
        } else if (userUiState.error.isNotEmpty()) {
            Text(
                text = userUiState.error,
                color = Color.Red
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.End)
                .padding(20.dp),
            text = stringResource(id = R.string.bitcoin_rate) + " " + userUiState.data?.bitcoinRate,
            fontSize = 20.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier,
                text = userUiState.data?.userBalance + " Bitcoins",
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.width(20.dp))
            IconButton(
                modifier = Modifier,
                onClick = { isOpenedPopUp = !isOpenedPopUp }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.wallet),
                    contentDescription = null
                )
            }
        }

        var rechargeAmountValue by remember { mutableStateOf("") }

        AnimatedVisibility(
            visible = isOpenedPopUp
        ) {
            Popup(
                popupPositionProvider = object : PopupPositionProvider {
                    override fun calculatePosition(
                        anchorBounds: IntRect,
                        windowSize: IntSize,
                        layoutDirection: LayoutDirection,
                        popupContentSize: IntSize
                    ): IntOffset {
                        return IntOffset(
                            x = windowSize.width,
                            y = popupContentSize.height - 100
                        )
                    }

                },
                properties = PopupProperties(
                    focusable = true
                ),
                onDismissRequest = {
                    isOpenedPopUp = false
                }
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        text = stringResource(id = R.string.recharge_wallet),
                        fontSize = 20.sp
                    )
                    TextField(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 12.dp),
                        value = rechargeAmountValue,
                        onValueChange = { newValue ->
                            rechargeAmountValue = newValue
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        shape = RectangleShape
                    )
                    Button(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            mainViewModel.recharge(
                                TransactionType.RECHARGE.value,
                                rechargeAmountValue,
                                "",
                                LocalDateTime.now().toString()
                            )
                            isOpenedPopUp = false
                        },
                        enabled = rechargeAmountValue.isNotEmpty()
                    ) {
                        Text(
                            text = stringResource(id = R.string.recharge),
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { navigateToTransactionScreen() }
        ) {
            Text(
                text = stringResource(id = R.string.add_transaction),
                fontSize = 20.sp
            )
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp))

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.all_transactions),
            fontSize = 30.sp
        )
        HorizontalDivider()

        if ((userUiState.data?.transactions != null) && (userUiState.data.transactions.isNotEmpty())) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val groupedTransactions = userUiState.data.transactions
                    .groupBy { it.date.substringBefore("T") }
                    .toSortedMap(compareByDescending { it })

                groupedTransactions.forEach { (date, transactions) ->
                    item {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            text = date,
                            textAlign = TextAlign.Center
                        )
                    }

                    items(transactions.sortedByDescending { it.date }) { transaction ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp)
                                        .size(30.dp),
                                    painter = if (transaction.type == TransactionType.RECHARGE.value) painterResource(
                                        id = R.drawable.icon_recharge
                                    ) else painterResource(id = R.drawable.icon_expense),
                                    contentDescription = null
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp),
                                    text = transaction.date.extractTime()
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(start = 70.dp),
                                    text = transaction.amount
                                )
                            }

                            if (transaction.category.isNotEmpty())
                                Text(
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp),
                                    text = transaction.category
                                )
                        }
                        HorizontalDivider()
                    }
                }
            }
        } else {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.no_transactions),
                fontSize = 20.sp
            )
        }

    }
}