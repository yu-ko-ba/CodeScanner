package io.github.yukoba.codescanner.usecase

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import io.github.yukoba.codescanner.R

class CopyToClipboardUseCase(private val context: Context) {
    private val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    operator fun invoke(text: String) {
        clipboard.setPrimaryClip(
            ClipData.newPlainText(
                context.getString(R.string.clip_text_label),
                text,
            )
        )
    }
}