package com.juannino.cabifychallenge.ui.navigation

import com.juannino.cabifychallenge.ui.navigation.screens.Screen

/**
 * @author Juan Sebastian Niño - 2023
 */

interface Destinations {
    val screen: Screen
}

object HomeDest: Destinations {
    override val screen: Screen
        get() = Screen.HOME_ROUTE
}

object ProductsDest: Destinations {
    override val screen: Screen
        get() = Screen.PRODUCTS_ROUTE
}

object CheckoutDest: Destinations {
    override val screen: Screen
        get() = Screen.CHECKOUT_ROUTE
}

// Screens to be displayed
val allScreens = listOf(HomeDest, ProductsDest, CheckoutDest)