package com.juannino.cabifychallenge.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.juannino.cabifychallenge.R
import com.juannino.cabifychallenge.ui.theme.CabifyChallengeTheme

/**
 * @author Juan Sebastian Niño - 2023
 */

@Composable
fun ShowErrorDialog(
    errorMessage: String,
    showTitle: Boolean = true,
    onDismiss: () -> Unit
) {
    val configuration = LocalConfiguration.current
    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.widthIn(max = configuration.screenWidthDp.dp - 80.dp),
        title = {
            if (showTitle) {
                Text(
                    text = stringResource(id = R.string.error_message_title),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        },
        text = {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.labelMedium
            )
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            Text(
                text = stringResource(R.string.dismiss_dialog_button_text),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { onDismiss() },
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ShowErrorDialogPreview() {
    CabifyChallengeTheme {
        ShowErrorDialog("error getting images") {
            // On DismissCLick
        }
    }
}