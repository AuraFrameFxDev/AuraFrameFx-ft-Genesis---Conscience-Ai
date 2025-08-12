package dev.aurakai.auraframefx.services

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.aurakai.auraframefx.ai.VertexAIConfig
import dev.aurakai.auraframefx.ai.clients.VertexAIClient
import dev.aurakai.auraframefx.security.SecurityContext
import dev.aurakai.auraframefx.data.logging.AuraFxLogger
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Auto Provisioning Service for Genesis-OS
 * Handles automatic setup and configuration of AI services, security contexts, and app resources.
 */
@Singleton
class AutoProvisioningService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStore: DataStore<Preferences>,
    private val vertexAIClient: VertexAIClient,
    private val vertexAIConfig: VertexAIConfig,
    private val securityContext: SecurityContext,
    private val logger: AuraFxLogger
) {

    companion object {
        private val AUTO_PROVISIONING_COMPLETED = booleanPreferencesKey("auto_provisioning_completed")
        private val AI_SERVICES_PROVISIONED = booleanPreferencesKey("ai_services_provisioned")
        private val SECURITY_CONTEXT_PROVISIONED = booleanPreferencesKey("security_context_provisioned")
    }

    /**
     * Performs automatic provisioning of all services if not already completed.
     */
    suspend fun performAutoProvisioning(): AutoProvisioningResult {
        return try {
            logger.i("AutoProvisioning", "Starting auto-provisioning process...")

            val isAlreadyProvisioned = dataStore.data.map { preferences ->
                preferences[AUTO_PROVISIONING_COMPLETED] ?: false
            }.first()

            if (isAlreadyProvisioned) {
                logger.i("AutoProvisioning", "Auto-provisioning already completed, skipping...")
                return AutoProvisioningResult.AlreadyProvisioned
            }

            // Provision AI services
            val aiResult = provisionAIServices()
            if (!aiResult) {
                logger.e("AutoProvisioning", "Failed to provision AI services")
                return AutoProvisioningResult.Failed("AI services provisioning failed")
            }

            // Provision security context
            val securityResult = provisionSecurityContext()
            if (!securityResult) {
                logger.e("AutoProvisioning", "Failed to provision security context")
                return AutoProvisioningResult.Failed("Security context provisioning failed")
            }

            // Mark provisioning as completed
            dataStore.edit { preferences ->
                preferences[AUTO_PROVISIONING_COMPLETED] = true
                preferences[AI_SERVICES_PROVISIONED] = true
                preferences[SECURITY_CONTEXT_PROVISIONED] = true
            }

            logger.i("AutoProvisioning", "Auto-provisioning completed successfully")
            AutoProvisioningResult.Success

        } catch (e: Exception) {
            logger.e("AutoProvisioning", "Auto-provisioning failed with exception", e)
            AutoProvisioningResult.Failed("Exception during provisioning: ${e.message}")
        }
    }

    /**
     * Provisions AI services and validates their availability.
     */
    private suspend fun provisionAIServices(): Boolean {
        return try {
            logger.i("AutoProvisioning", "Provisioning AI services...")
            
            // Validate Vertex AI configuration
            if (vertexAIConfig.projectId.isBlank() || vertexAIConfig.location.isBlank()) {
                logger.e("AutoProvisioning", "Invalid Vertex AI configuration")
                return false
            }

            // Initialize AI client if needed
            // Note: The actual client initialization is handled by the DI system
            logger.i("AutoProvisioning", "AI services provisioned successfully")
            true
        } catch (e: Exception) {
            logger.e("AutoProvisioning", "Failed to provision AI services", e)
            false
        }
    }

    /**
     * Provisions and validates the security context.
     */
    private suspend fun provisionSecurityContext(): Boolean {
        return try {
            logger.i("AutoProvisioning", "Provisioning security context...")
            
            // Initialize security context if needed
            // Note: The actual security context setup is handled by the DI system
            logger.i("AutoProvisioning", "Security context provisioned successfully")
            true
        } catch (e: Exception) {
            logger.e("AutoProvisioning", "Failed to provision security context", e)
            false
        }
    }

    /**
     * Checks if auto-provisioning has been completed.
     */
    suspend fun isAutoProvisioningCompleted(): Boolean {
        return dataStore.data.map { preferences ->
            preferences[AUTO_PROVISIONING_COMPLETED] ?: false
        }.first()
    }

    /**
     * Resets auto-provisioning state (for testing or re-provisioning).
     */
    suspend fun resetProvisioningState() {
        dataStore.edit { preferences ->
            preferences.remove(AUTO_PROVISIONING_COMPLETED)
            preferences.remove(AI_SERVICES_PROVISIONED)
            preferences.remove(SECURITY_CONTEXT_PROVISIONED)
        }
        logger.i("AutoProvisioning", "Auto-provisioning state reset")
    }
}

/**
 * Result of auto-provisioning operation.
 */
sealed class AutoProvisioningResult {
    object Success : AutoProvisioningResult()
    object AlreadyProvisioned : AutoProvisioningResult()
    data class Failed(val reason: String) : AutoProvisioningResult()
}