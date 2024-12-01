package io.github.yukoba.codescanner.ui.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.yukoba.codescanner.ui.components.ScannerInstallingScreen
import io.github.yukoba.codescanner.ui.components.ScannerScreen
import io.github.yukoba.codescanner.ui.views.types.ScannerInstallationStatus

@Composable
fun MainView(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState.scannerInstallationStatus) {
        ScannerInstallationStatus.INSTALLING -> ScannerInstallingScreen(
            progress = uiState.scannerInstallationProgress,
            modifier = modifier,
        )

        ScannerInstallationStatus.SUCCEED -> ScannerScreen(
            scannedText = uiState.scannedText,
            onCopyToClipboardButtonClicked = viewModel::onCopyToClipboardButtonClicked,
            onScanButtonClicked = viewModel::onScanButtonClicked,
            modifier = modifier,
        )

        ScannerInstallationStatus.FAILED -> TODO()
    }
}