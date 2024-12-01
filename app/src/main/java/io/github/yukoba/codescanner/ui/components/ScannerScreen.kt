package io.github.yukoba.codescanner.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.yukoba.codescanner.R

@Composable
fun ScannerScreen(
    scannedText: String?,
    onCopyToClipboardButtonClicked: () -> Unit = {},
    onScanButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ScannedText(
            text = scannedText ?: "",
            modifier = Modifier.weight(1f),
        )

        Button(
            content = {
                IconText(
                    painter = painterResource(R.drawable.baseline_content_copy_24),
                    text = stringResource(R.string.copy_to_clipboard),
                )
            },
            onClick = onCopyToClipboardButtonClicked,
            enabled = scannedText != null,
        )

        Button(
            content = { Text(stringResource(R.string.scan)) },
            onClick = onScanButtonClicked,
        )
    }
}