package io.github.yukoba.codescanner.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import io.github.yukoba.codescanner.R

// Set of Material typography styles to start with
private val yomogiFont = FontFamily(
    Font(
        googleFont = GoogleFont("Yomogi"),
        fontProvider = GoogleFont.Provider(
            providerAuthority = "com.google.android.gms.fonts",
            providerPackage = "com.google.android.gms",
            certificates = R.array.com_google_android_gms_fonts_certs,
        ),
    ),
)

private val typography = Typography()
val Typography = Typography(
    displayLarge = typography.displayLarge.copy(fontFamily = yomogiFont),
    displayMedium = typography.bodyMedium.copy(fontFamily = yomogiFont),
    displaySmall = typography.displaySmall.copy(fontFamily = yomogiFont),
    headlineLarge = typography.headlineLarge.copy(fontFamily = yomogiFont),
    headlineMedium = typography.headlineMedium.copy(fontFamily = yomogiFont),
    headlineSmall = typography.headlineSmall.copy(fontFamily = yomogiFont),
    titleLarge = typography.titleLarge.copy(fontFamily = yomogiFont),
    titleMedium = typography.titleMedium.copy(fontFamily = yomogiFont),
    titleSmall = typography.titleSmall.copy(fontFamily = yomogiFont),
    bodyLarge = typography.bodyLarge.copy(fontFamily = yomogiFont),
    bodyMedium = typography.bodyMedium.copy(fontFamily = yomogiFont),
    bodySmall = typography.bodySmall.copy(fontFamily = yomogiFont),
    labelLarge = typography.labelLarge.copy(fontFamily = yomogiFont),
    labelMedium = typography.labelMedium.copy(fontFamily = yomogiFont),
    labelSmall = typography.labelSmall.copy(fontFamily = yomogiFont),
)