package com.juannino.cabifychallenge.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.RemoveCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juannino.cabifychallenge.R
import com.juannino.cabifychallenge.ui.theme.CabifyChallengeTheme
import com.juannino.cabifychallenge.ui.utils.MUG_ITEM_CODE
import com.juannino.cabifychallenge.ui.utils.T_SHIRT_ITEM_CODE
import com.juannino.cabifychallenge.ui.utils.VOUCHER_ITEM_CODE
import com.juannino.domain.models.ProductItem
import com.juannino.domain.models.ProductItemDiscounts

/**
 * @author Juan Sebastian Niño - 2023
 */

@Composable
fun ProductItemStore(
    modifier: Modifier,
    productItem: ProductItem,
    onAddItemsClick: (Int) -> Unit
) {
    Box(modifier = modifier) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                GetDiscountInfoSection(
                    modifier = Modifier.fillMaxWidth(),
                    discounts = productItem.discount
                )
                Spacer(modifier = Modifier.size(4.dp))
                ImageAndPriceSection(
                    modifier = Modifier.fillMaxWidth(),
                    code = productItem.code,
                    price = productItem.price,
                    name = productItem.name,
                    discountIsApplied = productItem.hasDiscountActive
                )
                Spacer(modifier = Modifier.size(8.dp))
                ModifyItemsSection(
                    modifier = Modifier.fillMaxWidth(),
                    totalItemsPrice = productItem.totalPriceItems,
                    currentItemQuantity = productItem.itemQuantity,
                    onAddItemsClick = onAddItemsClick
                )
            }
        }
    }
}

@Composable
private fun GetDiscountInfoSection(modifier: Modifier, discounts: ProductItemDiscounts?) {
    if (discounts != null) {
        Box(modifier = modifier) {
            Text(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(4.dp),
                text = discounts.messageDiscount,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
private fun ImageAndPriceSection(
    modifier: Modifier,
    code: String,
    price: Double,
    name: String,
    discountIsApplied: Boolean
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop,
            painter = painterResource(
                // Hardcoded images
                id = when (code) {
                    VOUCHER_ITEM_CODE -> R.drawable.ic_voucher
                    T_SHIRT_ITEM_CODE -> R.drawable.ic_tshirt
                    MUG_ITEM_CODE -> R.drawable.ic_mug
                    else -> R.drawable.landscape_placeholder_com
                }
            ),
            contentDescription = "product image"
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(id = R.string.price_format, price),
                style = MaterialTheme.typography.labelLarge
            )
            if (discountIsApplied) {
                Text(
                    text = stringResource(id = R.string.discount_applied_label),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun ModifyItemsSection(
    modifier: Modifier,
    totalItemsPrice: Double,
    currentItemQuantity: Int,
    onAddItemsClick: (Int) -> Unit
) {

    var itemQuantity by remember { mutableIntStateOf(currentItemQuantity) }

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier.testTag("Remove Item Button"),
                    onClick = { itemQuantity-- },
                    enabled = itemQuantity > 0
                ) {
                    Icon(
                        imageVector = Icons.Rounded.RemoveCircleOutline,
                        contentDescription = "remove button"
                    )
                }
                Text(
                    text = itemQuantity.toString(),
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(
                    modifier = Modifier.testTag("Add item Button"),
                    onClick = { itemQuantity++ }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.AddCircleOutline,
                        contentDescription = "add button"
                    )
                }
            }
            Button(
                onClick = { onAddItemsClick(itemQuantity) }
            ) {
                Text(
                    text = stringResource(id = R.string.add_items_button),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.size(15.dp))
        }
        Text(
            modifier = Modifier.align(Alignment.BottomEnd),
            text = stringResource(id = R.string.total_price_label, totalItemsPrice),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductItemStorePreview() {
    val productItemFake = ProductItem(
        code = "VOUCHER",
        name = "Voucher",
        price = 15.0,
        discount = ProductItemDiscounts(
            "two_for_one_prom",
            "test message discount"
        )
    )
    CabifyChallengeTheme {
        ProductItemStore(
            modifier = Modifier.fillMaxWidth(),
            productItem = productItemFake
        ) {
            // On Click
        }
    }
}