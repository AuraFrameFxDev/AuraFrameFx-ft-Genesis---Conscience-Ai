package dev.aurakai.auraframefx.services

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.platform.app.InstrumentationRegistry
import dev.aurakai.auraframefx.ai.VertexAIConfig
import dev.aurakai.auraframefx.ai.clients.VertexAIClient
import dev.aurakai.auraframefx.data.logging.AuraFxLogger
import dev.aurakai.auraframefx.security.SecurityContext
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.File

/**
 * Test class for AutoProvisioningService.
 * Validates auto-provisioning functionality and state management.
 */
class AutoProvisioningServiceTest {

    private lateinit var autoProvisioningService: AutoProvisioningService
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var vertexAIClient: VertexAIClient
    private lateinit var vertexAIConfig: VertexAIConfig
    private lateinit var securityContext: SecurityContext
    private lateinit var logger: AuraFxLogger
    private lateinit var testDataStoreFile: File

    companion object {
        private val AUTO_PROVISIONING_COMPLETED = booleanPreferencesKey("auto_provisioning_completed")
    }

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        
        // Create a test-specific DataStore
        testDataStoreFile = File(context.filesDir, "test_auto_provisioning_prefs")
        if (testDataStoreFile.exists()) {
            testDataStoreFile.delete()
        }
        
        dataStore = PreferenceDataStoreFactory.create(
            produceFile = { testDataStoreFile }
        )

        // Mock dependencies
        vertexAIClient = mockk()
        vertexAIConfig = VertexAIConfig(
            projectId = "test-project",
            location = "us-central1",
            endpoint = "test-endpoint",
            modelName = "test-model",
            apiVersion = "v1",
            enableSafetyFilters = true,
            maxRetries = 3,
            timeoutMs = 30000,
            maxConcurrentRequests = 10,
            enableCaching = true,
            cacheExpiryMs = 3600000
        )
        securityContext = mockk()
        logger = mockk(relaxed = true)

        autoProvisioningService = AutoProvisioningService(
            context = context,
            dataStore = dataStore,
            vertexAIClient = vertexAIClient,
            vertexAIConfig = vertexAIConfig,
            securityContext = securityContext,
            logger = logger
        )
    }

    @After
    fun tearDown() {
        if (testDataStoreFile.exists()) {
            testDataStoreFile.delete()
        }
    }

    @Test
    fun testPerformAutoProvisioning_FirstTime_Success() = runTest {
        // Verify initial state
        val initiallyCompleted = autoProvisioningService.isAutoProvisioningCompleted()
        assertFalse("Auto-provisioning should not be completed initially", initiallyCompleted)

        // Perform auto-provisioning
        val result = autoProvisioningService.performAutoProvisioning()

        // Verify result
        assertTrue("Auto-provisioning should succeed", result is AutoProvisioningResult.Success)

        // Verify state is persisted
        val completed = autoProvisioningService.isAutoProvisioningCompleted()
        assertTrue("Auto-provisioning should be marked as completed", completed)

        // Verify DataStore state
        val provisioningCompleted = dataStore.data.map { preferences ->
            preferences[AUTO_PROVISIONING_COMPLETED] ?: false
        }.first()
        assertTrue("DataStore should record provisioning as completed", provisioningCompleted)
    }

    @Test
    fun testPerformAutoProvisioning_AlreadyCompleted_SkipsProvisioning() = runTest {
        // First provisioning
        val firstResult = autoProvisioningService.performAutoProvisioning()
        assertTrue("First provisioning should succeed", firstResult is AutoProvisioningResult.Success)

        // Second provisioning attempt
        val secondResult = autoProvisioningService.performAutoProvisioning()
        assertTrue("Second provisioning should be skipped", secondResult is AutoProvisioningResult.AlreadyProvisioned)
    }

    @Test
    fun testResetProvisioningState() = runTest {
        // Perform initial provisioning
        val result = autoProvisioningService.performAutoProvisioning()
        assertTrue("Initial provisioning should succeed", result is AutoProvisioningResult.Success)

        // Verify it's completed
        val completed = autoProvisioningService.isAutoProvisioningCompleted()
        assertTrue("Auto-provisioning should be completed", completed)

        // Reset state
        autoProvisioningService.resetProvisioningState()

        // Verify state is reset
        val resetCompleted = autoProvisioningService.isAutoProvisioningCompleted()
        assertFalse("Auto-provisioning should not be completed after reset", resetCompleted)
    }

    @Test
    fun testIsAutoProvisioningCompleted_InitialState() = runTest {
        val completed = autoProvisioningService.isAutoProvisioningCompleted()
        assertFalse("Auto-provisioning should not be completed initially", completed)
    }
}