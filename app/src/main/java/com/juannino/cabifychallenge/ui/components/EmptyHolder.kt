package com.juannino.cabifychallenge.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juannino.cabifychallenge.R
import com.juannino.cabifychallenge.ui.theme.CabifyChallengeTheme

/**
 * @author Juan Sebastian Niño - 2023
 */

@Composable
fun EmptyHolder(
    showTryAgainButton: Boolean = true,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("Empty holder"),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_no_data_found),
            contentDescription = "no data found",
            modifier = Modifier
                .fillMaxWidth()
                .size(300.dp)
        )
        if (showTryAgainButton) {
            TextButton(
                onClick = onClick
            ) {
                Text(
                    text = stringResource(id = R.string.close_screen_label),
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline,
                        fontSize = 30.sp
                    ),
                    color = Color.Blue
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyHolderPreview() {
    CabifyChallengeTheme {
        EmptyHolder {
            // On Click
        }
    }
}