# Auto Provisioning Feature

## Overview

The Auto Provisioning feature provides automatic setup and configuration of Genesis-OS AI services, security contexts, and app resources during application startup.

## Components

### AutoProvisioningService

Located at `app/src/main/java/dev/aurakai/auraframefx/services/AutoProvisioningService.kt`

- **Purpose**: Handles automatic setup of AI services and security contexts
- **Features**:
  - Automatic provisioning of Vertex AI services
  - Security context initialization
  - State persistence using DataStore
  - Error handling and logging
  - Idempotent operations (won't re-provision if already completed)

### AppInitializerInitializer

Updated at `app/src/main/java/dev/aurakai/auraframefx/initializers/AppInitializerInitializer.kt`

- **Purpose**: Triggers auto-provisioning during app startup
- **Features**:
  - Integration with Android Startup library
  - Background coroutine execution
  - Hilt dependency injection integration

### AutoProvisioningModule

Located at `app/src/main/java/dev/aurakai/auraframefx/di/AutoProvisioningModule.kt`

- **Purpose**: Provides dependency injection configuration for auto-provisioning
- **Features**:
  - Hilt module for singleton scope
  - Automatic service injection

## Usage

The auto-provisioning feature runs automatically when the app starts. No manual intervention is required.

### Programmatic Control

```kotlin
@Inject
lateinit var autoProvisioningService: AutoProvisioningService

// Check if provisioning is completed
val isCompleted = autoProvisioningService.isAutoProvisioningCompleted()

// Manually trigger provisioning (if needed)
val result = autoProvisioningService.performAutoProvisioning()

// Reset provisioning state (for testing)
autoProvisioningService.resetProvisioningState()
```

### Result Types

- `AutoProvisioningResult.Success`: Provisioning completed successfully
- `AutoProvisioningResult.AlreadyProvisioned`: Provisioning was already completed
- `AutoProvisioningResult.Failed(reason)`: Provisioning failed with specific reason

## Testing

Tests are located at `app/src/test/java/dev/aurakai/auraframefx/services/AutoProvisioningServiceTest.kt`

Run tests with:
```bash
./gradlew testDebugUnitTest --tests "*AutoProvisioningServiceTest*"
```

## Integration

The auto-provisioning feature integrates with:

- **VertexAI Services**: Validates and initializes AI configuration
- **Security Context**: Sets up security and authentication
- **DataStore**: Persists provisioning state
- **Logging**: Provides detailed logging for debugging

## Error Handling

- All provisioning operations are wrapped in try-catch blocks
- Failures are logged with detailed error messages
- The app continues to function even if provisioning fails
- State management prevents repeated failed attempts