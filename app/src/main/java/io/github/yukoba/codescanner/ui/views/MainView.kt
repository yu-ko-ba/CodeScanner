package io.github.yukoba.codescanner.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.yukoba.codescanner.R
import io.github.yukoba.codescanner.ui.components.IconText
import io.github.yukoba.codescanner.ui.components.ScannedText

@Composable
fun MainView(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ScannedText(
            text = uiState.scannedText ?: "",
            modifier = Modifier.weight(1f),
        )

        Button(
            content = {
                IconText(
                    painter = painterResource(R.drawable.baseline_content_copy_24),
                    text = stringResource(R.string.copy_to_clipboard),
                )
            },
            onClick = {},
            enabled = uiState.scannedText != null,
        )

        Button(
            onClick = {},
        ) {
            Text(stringResource(R.string.scan))
        }
    }
}