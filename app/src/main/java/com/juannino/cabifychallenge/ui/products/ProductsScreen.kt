package com.juannino.cabifychallenge.ui.products

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.juannino.cabifychallenge.R
import com.juannino.cabifychallenge.ui.components.EmptyHolder
import com.juannino.cabifychallenge.ui.components.LoadingDialog
import com.juannino.cabifychallenge.ui.components.ProductItemStore
import com.juannino.cabifychallenge.ui.components.ShowErrorDialog
import com.juannino.cabifychallenge.ui.model.ProductItemsStateUi
import com.juannino.cabifychallenge.viewmodels.ProductsViewModel
import com.juannino.domain.models.ProductItem
import com.juannino.domain.models.UpdateProductItem

/**
 * @author Juan Sebastian Niño - 2023
 */

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel = hiltViewModel(),
    onGoToPaymentClick: () -> Unit,
    onErrorDismissAction: () -> Unit
) {
    val productItemsStateUi by
    viewModel.productsStateUi.collectAsStateWithLifecycle()

    ProductsScreen(
        productItemsStateUi = productItemsStateUi,
        onAddItemClick = viewModel::updateItems,
        onGoToPaymentClick = onGoToPaymentClick,
        onErrorDismissAction = onErrorDismissAction,
        onCloseScreenClick = onErrorDismissAction
    )
}

@Composable
private fun ProductsScreen(
    productItemsStateUi: ProductItemsStateUi,
    onAddItemClick: (UpdateProductItem) -> Unit,
    onGoToPaymentClick: () -> Unit,
    onErrorDismissAction: () -> Unit,
    onCloseScreenClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("Products Screen")
    ) {
        when (productItemsStateUi) {
            is ProductItemsStateUi.Loading -> LoadingDialog()
            is ProductItemsStateUi.Success ->
                if (productItemsStateUi.productItems.isNotEmpty()) {
                    FetchProductItems(
                        productItems = productItemsStateUi.productItems,
                        onAddItemsClick = onAddItemClick
                    )
                } else EmptyHolder(onClick = onCloseScreenClick)

            is ProductItemsStateUi.Error ->
                ShowErrorDialog(
                    errorMessage = productItemsStateUi.errorMessage,
                    onDismiss = onErrorDismissAction
                )
        }
        Button(
            modifier = Modifier
                .testTag("Go to checkout Button")
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth(),
            onClick = onGoToPaymentClick
        ) {
            Text(
                text = stringResource(id = R.string.go_to_payment),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun FetchProductItems(
    productItems: List<ProductItem>,
    onAddItemsClick: (UpdateProductItem) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(bottom = 45.dp)){
        items(productItems) { item ->
            ProductItemStore(
                modifier = Modifier.fillMaxWidth() ,
                productItem = item,
                onAddItemsClick = { newQuantity ->
                    val updateProductItem = UpdateProductItem(
                        code = item.code,
                        price = item.price,
                        quantityItems = newQuantity,
                        discounts = item.discount
                    )
                    onAddItemsClick(updateProductItem)
                }
            )
            Spacer(modifier = Modifier.size(6.dp))
        }
    }
}