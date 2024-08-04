package com.juannino.cabifychallenge.ui.navigation.screens

import androidx.annotation.StringRes
import com.juannino.cabifychallenge.R

/**
 * @author Juan Sebastian Niño - 2023
 */

enum class Screen(val route: String, @StringRes val title: Int) {
    HOME_ROUTE("home", R.string.home_title),
    PRODUCTS_ROUTE("products", R.string.products_title),
    CHECKOUT_ROUTE("checkout", R.string.checkout_title),
}
