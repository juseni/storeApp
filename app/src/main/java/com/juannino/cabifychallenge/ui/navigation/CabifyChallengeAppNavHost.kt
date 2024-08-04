package com.juannino.cabifychallenge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.juannino.cabifychallenge.ui.checkout.CheckoutScreen
import com.juannino.cabifychallenge.ui.home.HomeScreen
import com.juannino.cabifychallenge.ui.navigation.screens.Screen
import com.juannino.cabifychallenge.ui.products.ProductsScreen

/**
 * @author Juan Sebastian Niño - 2023
 */

@Composable
fun CabifyChallengeAppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDest.screen.route,
        modifier = modifier
    ) {
        composable(route = HomeDest.screen.route) {
            HomeScreen(
                onProductListClick = {
                    navController.navigateSingleTopTo(Screen.PRODUCTS_ROUTE.route)
                }
            )
        }

        composable(route = ProductsDest.screen.route) {
            ProductsScreen(
                onGoToPaymentClick = {
                    navController.navigate(Screen.CHECKOUT_ROUTE.route)
                },
                onErrorDismissAction = { navController.navigateUp() }
            )
        }

        composable(route = CheckoutDest.screen.route) {
            CheckoutScreen {
                navController.navigateUp()
            }
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
    }