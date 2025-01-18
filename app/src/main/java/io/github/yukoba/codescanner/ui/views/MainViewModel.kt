package io.github.yukoba.codescanner.ui.views

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.github.yukoba.codescanner.ui.views.types.ScannerInstallationStatus
import io.github.yukoba.codescanner.usecase.CodeScanUseCase
import io.github.yukoba.codescanner.usecase.CopyToClipboardUseCase
import io.github.yukoba.codescanner.usecase.InstallScannerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val copyToClipboardUseCase = CopyToClipboardUseCase(
        getApplication<Application>().applicationContext,
    )
    private val codeScanUseCase = CodeScanUseCase(
        context = getApplication<Application>().applicationContext,
    )

    init {
        val installScannerUseCase = InstallScannerUseCase(
            context = getApplication<Application>().applicationContext,
        )

        installScannerUseCase(
            onInstallProgressChanged = { progress ->
                _uiState.update { currentState ->
                    currentState.copy(scannerInstallationProgress = progress)
                }
            },
            onInstallSucceed = {
                _uiState.update { currentState ->
                    currentState.copy(scannerInstallationStatus = ScannerInstallationStatus.SUCCEED)
                }
            },
        )
    }

    fun onCopyToClipboardButtonClicked() {
        // This button is disabled while scannedText is null.
        copyToClipboardUseCase(_uiState.value.scannedText!!)
    }

    fun onScanButtonClicked() {
        codeScanUseCase(
            onScanSucceed = {
                _uiState.update { currentState ->
                    currentState.copy(scannedText = it)
                }
            }
        )
    }
}