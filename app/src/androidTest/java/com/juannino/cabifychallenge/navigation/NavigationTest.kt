package com.juannino.cabifychallenge.navigation

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.juannino.cabifychallenge.MainActivity
import com.juannino.cabifychallenge.ui.navigation.CabifyChallengeAppNavHost
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author Juan Sebastian Niño - 2023
 */

@ExperimentalCoroutinesApi
@HiltAndroidTest
class NavigationTest {
    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupShareAppNavHost() {
        hiltTestRule.inject()
        composeTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            CabifyChallengeAppNavHost(navController = navController)
        }
    }

    @Test
    fun cabifyChallengeAppNavHost_verifyHomeStartDestination() {
        composeTestRule
            .onNodeWithTag("Home Screen")
            .assertIsDisplayed()
    }

    @Test
    fun cabifyChallengeAppNavHost_verifyProductsDestinationFromHome() {
        composeTestRule
            .onNodeWithTag("Home Screen")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("Go to Products button")
            .assertIsDisplayed()
            .performClick()

        composeTestRule
            .onNodeWithTag("Products Screen")
            .assertIsDisplayed()
    }

    @Test
    fun cabifyChallengeAppNavHost_verifyCheckoutDestinationFromProducts() {
        composeTestRule
            .onNodeWithTag("Go to Products button")
            .assertIsDisplayed()
            .performClick()

        composeTestRule
            .onNodeWithTag("Products Screen")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("Go to checkout Button")
            .assertIsDisplayed()
            .performClick()

        composeTestRule
            .onNodeWithTag("Checkout Screen")
            .assertIsDisplayed()
    }
}