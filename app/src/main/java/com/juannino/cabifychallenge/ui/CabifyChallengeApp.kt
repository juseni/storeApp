package com.juannino.cabifychallenge.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.juannino.cabifychallenge.ui.components.AppTopBar
import com.juannino.cabifychallenge.ui.navigation.CabifyChallengeAppNavHost
import com.juannino.cabifychallenge.ui.navigation.CheckoutDest
import com.juannino.cabifychallenge.ui.navigation.HomeDest
import com.juannino.cabifychallenge.ui.navigation.allScreens
import com.juannino.cabifychallenge.ui.navigation.navigateSingleTopTo
import com.juannino.cabifychallenge.viewmodels.HomeViewModel

/**
 * @author Juan Sebastian Niño - 2023
 */

@Composable
fun CabifyChallengeApp(homeViewModel: HomeViewModel) {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination

    val currentScreen =
        allScreens.find {
            it.screen.route == currentDestination?.route
        } ?: HomeDest

    Scaffold(
        topBar = {
            AppTopBar(
                viewModel = homeViewModel,
                screen = currentScreen.screen,
                onBackClick = { navController.navigateUp() },
                onFavoriteClick = {
                    navController.navigateSingleTopTo(CheckoutDest.screen.route)
                }
            )
        }
    ) { innerPadding ->
        CabifyChallengeAppNavHost(
            navController = navController,
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )
    }
}