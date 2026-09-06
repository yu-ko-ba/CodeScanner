package io.github.yukoba.codescanner.usecase

import android.content.Context
import com.google.android.gms.common.moduleinstall.InstallStatusListener
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.android.gms.tflite.java.TfLite

class InstallScannerUseCase(
    private val context: Context,
) {
    operator fun invoke(
        onInstallProgressChanged: (Float) -> Unit = {},
        onInstallSucceed: () -> Unit = {},
        onInstallFailed: () -> Unit = {},
    ) {
        val installClient = ModuleInstall.getClient(context)
        val optionalModuleApi = TfLite.getClient(context)
        val installProgressListener = InstallStatusListener {
            val progressInfo = it.progressInfo ?: return@InstallStatusListener
            val totalBytesToDownload = progressInfo.totalBytesToDownload
            if (totalBytesToDownload <= 0L) return@InstallStatusListener
            onInstallProgressChanged(progressInfo.bytesDownloaded.toFloat() / totalBytesToDownload)
        }
        val installRequest = ModuleInstallRequest.newBuilder()
            .addApi(optionalModuleApi)
            .setListener(installProgressListener)
            .build()

        installClient
            .installModules(installRequest)
            .addOnSuccessListener {
                onInstallSucceed()
            }
            .addOnFailureListener {
                onInstallFailed()
            }
    }
}