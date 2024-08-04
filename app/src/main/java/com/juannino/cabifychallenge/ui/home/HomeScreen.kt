package com.juannino.cabifychallenge.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juannino.cabifychallenge.R
import com.juannino.cabifychallenge.ui.theme.CabifyChallengeTheme

/**
 * @author Juan Sebastian Niño - 2023
 */

@Composable
fun HomeScreen(
    onProductListClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("Home Screen"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            modifier = Modifier
                .width(180.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(10.dp)),
            painter = painterResource(id = R.drawable.ic_cabify_logo),
            contentDescription = "home logo"
        )
        Text(
            text = stringResource(id = R.string.home_screen_title),
            style = MaterialTheme.typography.titleMedium
        )
        ElevatedButton(
            modifier = Modifier.testTag("Go to Products button"),
            onClick = onProductListClick
        ) {
            Text(
                text = stringResource(id = R.string.go_to_products),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CabifyChallengeTheme {
        HomeScreen {
            // On Click
        }
    }
}