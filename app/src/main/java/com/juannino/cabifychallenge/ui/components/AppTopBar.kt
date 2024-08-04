package com.juannino.cabifychallenge.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.juannino.cabifychallenge.ui.navigation.screens.Screen
import com.juannino.cabifychallenge.viewmodels.HomeViewModel

/**
 * @author Juan Sebastian Niño - 2023
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    viewModel: HomeViewModel,
    screen: Screen,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    var itemsInBasket by remember { mutableIntStateOf(Int.MIN_VALUE) }
    LaunchedEffect(Unit) {
        viewModel.itemsQuantityState.collect { itemsQuantity ->
            itemsInBasket = itemsQuantity
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .testTag("Top App Bar")
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(text = stringResource(id = screen.title))
            },
            navigationIcon = {
                if (screen != Screen.HOME_ROUTE) {
                    IconButton(
                        modifier = Modifier.testTag("Fav Button"),
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "back_button"
                        )
                    }
                }
            },
            actions = {
                IconButton(modifier = Modifier.fillMaxHeight(), onClick = onFavoriteClick) {
                    Box {
                        Icon(
                            imageVector = Icons.Filled.ShoppingCart,
                            contentDescription = "checkout_button"
                        )
                        if (itemsInBasket != Int.MIN_VALUE && itemsInBasket != 0) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(bottom = 10.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = CircleShape
                                    ),
                                style = MaterialTheme.typography.headlineSmall,
                                text = itemsInBasket.toString(),
                                color = Color.White
                            )
                        }
                    }
                }
            }
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            thickness = 2.dp
        )
    }
}