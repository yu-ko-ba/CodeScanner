package io.github.yukoba.codescanner.usecase

import android.content.Context
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

class CodeScanUseCase(
    context: Context,
    private val onScanSucceed: (String?) -> Unit = {},
    private val onScanFailed: (Exception) -> Unit = {},
    private val onScanCanceled: () -> Unit = {},
) {
    private val scanner: GmsBarcodeScanner

    init {
        val options = GmsBarcodeScannerOptions.Builder()
            .enableAutoZoom()
            .build()
        scanner = GmsBarcodeScanning.getClient(context, options)
    }

    fun scan() {
        scanner.startScan()
            .addOnSuccessListener { scannedData ->
                onScanSucceed(scannedData.rawValue)
            }
            .addOnFailureListener { e ->
                onScanFailed(e)
            }
            .addOnCanceledListener {
                onScanCanceled()
            }
    }
}