package io.github.yukoba.codescanner.ui.views

import io.github.yukoba.codescanner.ui.views.types.ScannerInstallationStatus

data class MainUiState(
    val scannerInstallationStatus: ScannerInstallationStatus = ScannerInstallationStatus.INSTALLING,
    val scannerInstallationProgress: Float = 0.0f,
    val scannedText: String? = null,
    val scannerIsAvailable: Boolean = false,
)
