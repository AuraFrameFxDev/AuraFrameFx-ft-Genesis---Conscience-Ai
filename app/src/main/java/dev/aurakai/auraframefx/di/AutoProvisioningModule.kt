package dev.aurakai.auraframefx.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.aurakai.auraframefx.services.AutoProvisioningService
import javax.inject.Singleton

/**
 * Hilt Module for providing Auto Provisioning related dependencies.
 * Implements auto-provisioning of AI services, security contexts, and app resources.
 */
@Module
@InstallIn(SingletonComponent::class)
object AutoProvisioningModule {

    /**
     * The AutoProvisioningService is provided by Hilt's automatic injection
     * since it's annotated with @Inject constructor and @Singleton.
     * This module exists for explicit configuration if needed in the future.
     */
}