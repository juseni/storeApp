package com.juannino.cabifychallenge.ui.checkout

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.juannino.cabifychallenge.R
import com.juannino.cabifychallenge.ui.components.EmptyHolder
import com.juannino.cabifychallenge.ui.components.LoadingDialog
import com.juannino.cabifychallenge.ui.components.ShowErrorDialog
import com.juannino.cabifychallenge.ui.model.ProductItemsStateUi
import com.juannino.cabifychallenge.ui.theme.CabifyChallengeTheme
import com.juannino.cabifychallenge.viewmodels.CheckoutViewModel
import com.juannino.domain.models.ProductItem
import kotlinx.coroutines.launch

/**
 * @author Juan Sebastian Niño - 2023
 */

@Composable
fun CheckoutScreen(
    viewModel: CheckoutViewModel = hiltViewModel(),
    onCloseScreen: () -> Unit
) {
    val productItemsStateUi by viewModel.productsStateUi.collectAsStateWithLifecycle()
    var showPaymentMessage by remember { mutableStateOf(false) }
    var isPaymentSuccessful by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    SideEffect {
        scope.launch {
            viewModel.paymentSuccessfulState.collect { isSuccess ->
                showPaymentMessage = true
                isPaymentSuccessful = isSuccess
            }
        }
    }

    if (showPaymentMessage) {
        ShowErrorDialog(
            errorMessage = stringResource(id = R.string.payment_success_message.takeIf { isPaymentSuccessful }
                ?: R.string.payment_fail_message),
            showTitle = !isPaymentSuccessful,
            onDismiss = { if (isPaymentSuccessful) onCloseScreen() }
        )
    }

    CheckoutScreen(
        productItemsStateUi = productItemsStateUi,
        onPayClick = viewModel::makePayment,
        onErrorDismissAction = onCloseScreen
    )
}

@Composable
fun CheckoutScreen(
    productItemsStateUi: ProductItemsStateUi,
    onPayClick: () -> Unit,
    onErrorDismissAction: () -> Unit
) {
    var itemsSize by remember { mutableIntStateOf(0) }
    Box(modifier = Modifier
        .fillMaxSize()
        .testTag("Checkout Screen")
    ) {
        when (productItemsStateUi) {
            is ProductItemsStateUi.Loading -> LoadingDialog()
            is ProductItemsStateUi.Success -> {
                itemsSize = productItemsStateUi.productItems.size
                if (itemsSize > 0) {
                    CreateShoppingCartDetail(
                        productItems = productItemsStateUi.productItems
                    )
                } else EmptyHolder(showTryAgainButton = false) { }
            }

            is ProductItemsStateUi.Error ->
                ShowErrorDialog(
                    errorMessage = productItemsStateUi.errorMessage,
                    onDismiss = onErrorDismissAction
                )
        }
        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth(),
            enabled = itemsSize > 0,
            onClick = onPayClick
        ) {
            Text(
                text = stringResource(id = R.string.make_payment_button),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun CreateShoppingCartDetail(
    productItems: List<ProductItem>,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 40.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.item_label),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = stringResource(id = R.string.price_label),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            LazyColumn {
                items(productItems) { item ->
                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        Text(
                            text = stringResource(
                                id = R.string.item_name_with_quantity, item.name, item.itemQuantity
                            ),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        DashedLine(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(1f)
                        )
                        Text(
                            text = stringResource(
                                id = R.string.price_format,
                                item.totalPriceItems
                            ),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                }
            }
            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp
            )
            Text(
                modifier = Modifier.align(Alignment.End),
                text = stringResource(
                    id = R.string.total_price_label,
                    productItems.sumOf { it.totalPriceItems }
                ),
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}

@Composable
fun DashedLine(
    modifier: Modifier,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Canvas(
        modifier = modifier
    ) {
        val dividerHeight = 1.dp.toPx()
        drawRoundRect(
            color = color,
            style = Stroke(
                width = dividerHeight,
                pathEffect = PathEffect.dashPathEffect(
                    intervals = floatArrayOf(10f, 10f),
                    phase = 10F
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CheckoutScreenPreview() {
    val productItemsFake = listOf(
        ProductItem(
            code = "VOUCHER",
            name = "Voucher",
            price = 15.0,
            discount = null,
            itemQuantity = 4,
            totalPriceItems = 30.0
        ),
        ProductItem(
            code = "TSHIRT",
            name = "T-shirt",
            price = 30.0,
            discount = null,
            itemQuantity = 10,
            totalPriceItems = 300.0
        )
    )
    CabifyChallengeTheme {
        CheckoutScreen(
            productItemsStateUi = ProductItemsStateUi.Success(productItemsFake),
            onPayClick = { },
            onErrorDismissAction = { }
        )
    }
}


