package io.github.yukoba.codescanner

import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.gms.common.Feature
import com.google.android.gms.common.api.Api
import com.google.android.gms.common.api.OptionalModuleApi
import com.google.android.gms.common.api.internal.ApiKey
import com.google.android.gms.common.moduleinstall.InstallStatusListener
import com.google.android.gms.common.moduleinstall.ModuleAvailabilityResponse
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallClient
import com.google.android.gms.common.moduleinstall.ModuleInstallIntentResponse
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.android.gms.common.moduleinstall.ModuleInstallResponse
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.barcode.common.internal.BarcodeSource
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements
import org.robolectric.annotation.Resetter

@RunWith(AndroidJUnit4::class)
@Config(
    shadows = [ShadowGmsBarcodeScanning::class, ShadowModuleInstall::class],
    instrumentedPackages = [
        "com.google.mlkit.vision.codescanner",
        "com.google.android.gms.common.moduleinstall",
    ],
)
class CodeScannerTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun `should show scan result when scan succeeded`() {
        // Arrange
        ShadowGmsBarcodeScanning.scannerReturns("scanned value 42")

        // Act
        composeTestRule.onNodeWithText(context.getString(R.string.scan)).performClick()

        // Assert
        composeTestRule.onNodeWithText("scanned value 42").assertIsDisplayed()
    }
}

@Implements(GmsBarcodeScanning::class)
@Suppress("UtilityClassWithPublicConstructor")
class ShadowGmsBarcodeScanning {
    companion object {
        private var scanResult: Barcode? = null

        fun scannerReturns(rawValue: String) {
            scanResult = Barcode(RawValueOnlyBarcodeSource(rawValue))
        }

        @JvmStatic
        @Implementation
        @Suppress("UnusedParameter")
        fun getClient(
            context: Context,
            options: GmsBarcodeScannerOptions,
        ): GmsBarcodeScanner =
            object : GmsBarcodeScanner {
                override fun startScan(): Task<Barcode> = Tasks.forResult(checkNotNull(scanResult))

                override fun getOptionalFeatures(): Array<Feature> = emptyArray()
            }

        @JvmStatic
        @Resetter
        fun reset() {
            scanResult = null
        }
    }
}

@Implements(ModuleInstall::class)
@Suppress("UtilityClassWithPublicConstructor")
class ShadowModuleInstall {
    companion object {
        @JvmStatic
        @Implementation
        @Suppress("UnusedParameter")
        fun getClient(context: Context): ModuleInstallClient =
            object : ModuleInstallClient {
                override fun installModules(request: ModuleInstallRequest): Task<ModuleInstallResponse> =
                    Tasks.forResult(ModuleInstallResponse(0))

                override fun areModulesAvailable(vararg apis: OptionalModuleApi): Task<ModuleAvailabilityResponse> =
                    throw UnsupportedOperationException()

                override fun deferredInstall(vararg apis: OptionalModuleApi): Task<Void> =
                    throw UnsupportedOperationException()

                override fun getInstallModulesIntent(
                    vararg apis: OptionalModuleApi,
                ): Task<ModuleInstallIntentResponse> = throw UnsupportedOperationException()

                override fun releaseModules(vararg apis: OptionalModuleApi): Task<Void> =
                    throw UnsupportedOperationException()

                override fun unregisterListener(listener: InstallStatusListener): Task<Boolean> =
                    throw UnsupportedOperationException()

                override fun getApiKey(): ApiKey<Api.ApiOptions.NoOptions> = throw UnsupportedOperationException()
            }
    }
}

private class RawValueOnlyBarcodeSource(
    private val value: String,
) : BarcodeSource {
    override fun getRawValue(): String = value

    override fun getDisplayValue(): String = value

    override fun getRawBytes(): ByteArray = value.toByteArray()

    override fun getFormat(): Int = Barcode.FORMAT_UNKNOWN

    override fun getValueType(): Int = Barcode.TYPE_UNKNOWN

    override fun getBoundingBox(): Rect? = null

    override fun getCornerPoints(): Array<Point>? = null

    override fun getCalendarEvent(): Barcode.CalendarEvent? = null

    override fun getContactInfo(): Barcode.ContactInfo? = null

    override fun getDriverLicense(): Barcode.DriverLicense? = null

    override fun getEmail(): Barcode.Email? = null

    override fun getGeoPoint(): Barcode.GeoPoint? = null

    override fun getPhone(): Barcode.Phone? = null

    override fun getSms(): Barcode.Sms? = null

    override fun getUrl(): Barcode.UrlBookmark? = null

    override fun getWifi(): Barcode.WiFi? = null
}
