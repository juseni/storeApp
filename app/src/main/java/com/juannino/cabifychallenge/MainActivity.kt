package com.juannino.cabifychallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.juannino.cabifychallenge.ui.CabifyChallengeApp
import com.juannino.cabifychallenge.ui.theme.CabifyChallengeTheme
import com.juannino.cabifychallenge.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Juan Sebastian Niño - 2023
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            CabifyChallengeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CabifyChallengeApp(homeViewModel = homeViewModel)
                }
            }
        }
    }
}