package dev.aurakai.auraframefx.initializers

import android.content.Context
import androidx.startup.Initializer
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.services.AutoProvisioningService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * App Initializer for Genesis-OS that handles auto-provisioning during startup.
 * This initializer sets up auto-provisioning of AI services and other critical components.
 */
class AppInitializerInitializer : Initializer<Unit> {

    /**
     * Performs application-specific initialization logic during app startup.
     * Initiates auto-provisioning of AI services and security contexts.
     *
     * This method is called on the main thread when the application launches.
     */
    override fun create(context: Context) {
        Timber.i("Genesis-OS App Initializer starting...")
        
        try {
            // Get the auto-provisioning service through Hilt entry point
            val entryPoint = EntryPointAccessors.fromApplication(
                context,
                AutoProvisioningEntryPoint::class.java
            )
            val autoProvisioningService = entryPoint.autoProvisioningService()
            
            // Perform auto-provisioning in a background coroutine
            // Using SupervisorJob to ensure failures don't crash the app
            val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
            scope.launch {
                try {
                    val result = autoProvisioningService.performAutoProvisioning()
                    when (result) {
                        is dev.aurakai.auraframefx.services.AutoProvisioningResult.Success -> {
                            Timber.i("Auto-provisioning completed successfully during app startup")
                        }
                        is dev.aurakai.auraframefx.services.AutoProvisioningResult.AlreadyProvisioned -> {
                            Timber.i("Auto-provisioning already completed, skipping")
                        }
                        is dev.aurakai.auraframefx.services.AutoProvisioningResult.Failed -> {
                            Timber.e("Auto-provisioning failed: ${result.reason}")
                        }
                    }
                } catch (e: Exception) {
                    Timber.e(e, "Exception during auto-provisioning in app startup")
                }
            }
            
            Timber.i("Genesis-OS App Initializer completed")
        } catch (e: Exception) {
            Timber.e(e, "Failed to initialize auto-provisioning")
        }
    }

    /**
     * Returns a list of initializer classes that this initializer depends on.
     *
     * @return An empty list, indicating that this initializer has no dependencies.
     */
    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }

    /**
     * Hilt Entry Point for accessing auto-provisioning service during app startup.
     */
    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface AutoProvisioningEntryPoint {
        fun autoProvisioningService(): AutoProvisioningService
    }
}
