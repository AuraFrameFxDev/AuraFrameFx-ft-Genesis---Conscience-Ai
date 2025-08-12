// File: romtools/src/main/kotlin/dev/aurakai/auraframefx/romtools/ui/RomToolsScreen.kt
package dev.aurakai.auraframefx.romtools.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.aurakai.auraframefx.romtools.RomOperation
import dev.aurakai.auraframefx.romtools.RomToolsManager

/**
 * Main ROM Tools screen for Genesis AuraFrameFX.
 * Provides access to ROM flashing, backup/restore, and system modification tools.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RomToolsScreen(
    modifier: Modifier = Modifier,
    romToolsManager: RomToolsManager = hiltViewModel()
) {
    val romToolsState by romToolsManager.romToolsState.collectAsStateWithLifecycle()
    val currentOperation by romToolsManager.operationProgress.collectAsStateWithLifecycle()
    var showCancelDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A0A0A),
                        Color(0xFF1A1A1A),
                        Color(0xFF0A0A0A)
                    )
                )
            )
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "ROM Tools",
                    color = Color(0xFFFF6B35),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black.copy(alpha = 0.8f)
            )
        )

        if (!romToolsState.isInitialized) {
            // Loading state
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFFFF6B35),
                        strokeWidth = 3.dp
                    )
                    Text(
                        text = "Initializing ROM Tools...",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Device Capabilities Card
                item {
                    DeviceCapabilitiesCard(
                        capabilities = romToolsState.capabilities
                    )
                }

                // Active Operation Progress
                currentOperation?.let { operation ->
                    item {
                        OperationProgressCard(
                            operation = operation,
                            onCancel = { showCancelDialog = true }
                        )
                    }
                }

                // ROM Tools Actions
                item {
                    Text(
                        text = "ROM Operations",
                        color = Color(0xFFFF6B35),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                // ROM Tools Action Cards
                items(getRomToolsActions()) { action ->
                    RomToolActionCard(
                        action = action,
                        isEnabled = action.isEnabled(romToolsState.capabilities),
                        onClick = {
                            // Handle action clicks
                            when (action.type) {
                                RomActionType.FLASH_ROM -> {
                                    // Open ROM selection dialog
                                }

                                RomActionType.CREATE_BACKUP -> {
                                    // Start backup process
                                }

                                RomActionType.RESTORE_BACKUP -> {
                                    // Open backup selection
                                }

                                RomActionType.UNLOCK_BOOTLOADER -> {
                                    // Unlock bootloader
                                }

                                RomActionType.INSTALL_RECOVERY -> {
                                    // Install custom recovery
                                }

                                RomActionType.GENESIS_OPTIMIZATIONS -> {
                                    // Apply Genesis AI optimizations
                                }
                            }
                        }
                    )
                }

                // Available ROMs Section
                if (romToolsState.availableRoms.isNotEmpty()) {
                    item {
                        Text(
                            text = "Available ROMs",
                            color = Color(0xFFFF6B35),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    items(romToolsState.availableRoms) { rom ->
                        AvailableRomCard(rom = rom)
                    }
                }

                // Backups Section
                if (romToolsState.backups.isNotEmpty()) {
                    item {
                        Text(
                            text = "Backups",
                            color = Color(0xFFFF6B35),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    items(romToolsState.backups) { backup ->
                        BackupCard(backup = backup)
                    }
                }
            }
        }
        
        // Cancel Confirmation Dialog
        if (showCancelDialog) {
            AlertDialog(
                onDismissRequest = { showCancelDialog = false },
                title = { Text("Cancel Operation") },
                text = { Text("Are you sure you want to cancel the current operation?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            romToolsManager.clearOperationProgress()
                            showCancelDialog = false
                        }
                    ) {
                        Text("Yes, Cancel")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showCancelDialog = false }
                    ) {
                        Text("No, Continue")
                    }
                }
            )
        }
    }
}

@Composable
private fun DeviceCapabilitiesCard(
    capabilities: dev.aurakai.auraframefx.romtools.RomCapabilities?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Device Capabilities",
                color = Color(0xFFFF6B35),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            if (capabilities != null) {
                CapabilityRow("Root Access", capabilities.hasRootAccess)
                CapabilityRow("Bootloader Access", capabilities.hasBootloaderAccess)
                CapabilityRow("Recovery Access", capabilities.hasRecoveryAccess)
                CapabilityRow("System Write Access", capabilities.hasSystemWriteAccess)

                Spacer(modifier = Modifier.height(8.dp))

                InfoRow("Device", capabilities.deviceModel)
                InfoRow("Android", capabilities.androidVersion)
                InfoRow("Security Patch", capabilities.securityPatchLevel)
            } else {
                Text(
                    text = "Checking capabilities...",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun CapabilityRow(label: String, hasCapability: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 14.sp
        )
        Icon(
            imageVector = if (hasCapability) Icons.Default.CheckCircle else Icons.Default.Cancel,
            contentDescription = null,
            tint = if (hasCapability) Color(0xFF4CAF50) else Color(0xFFF44336),
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 12.sp
        )
        Text(
            text = value,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun OperationProgressCard(
    operation: dev.aurakai.auraframefx.romtools.OperationProgress,
    modifier: Modifier = Modifier,
    onCancel: () -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2E2E2E)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = operation.operation.getDisplayName(),
                color = Color(0xFFFF6B35),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            LinearProgressIndicator(
                progress = operation.progress / 100f,
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFFF6B35),
                trackColor = Color(0xFF444444)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${operation.progress.toInt()}%",
                    color = Color.White,
                    fontSize = 14.sp
                )
                
                Button(
                    onClick = onCancel,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}

@Composable
private fun RomToolActionCard(
    action: RomToolAction,
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val icon = when (action.type) {
        RomActionType.FLASH_ROM -> Icons.Default.FlashOn
        RomActionType.CREATE_BACKUP -> Icons.Default.Backup
        RomActionType.RESTORE_BACKUP -> Icons.Default.Restore
        RomActionType.UNLOCK_BOOTLOADER -> Icons.Default.LockOpen
        RomActionType.INSTALL_RECOVERY -> Icons.Default.Healing
        RomActionType.OPTIMIZE_SYSTEM -> Icons.Default.Psychology
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = if (isEnabled) onClick else null,
        colors = CardDefaults.cardColors(
            containerColor = if (isEnabled) Color(0xFF1E1E1E) else Color(0xFF111111)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = action.icon,
                contentDescription = null,
                tint = if (isEnabled) action.color else Color.Gray,
                modifier = Modifier.size(32.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = action.title,
                    color = if (isEnabled) Color.White else Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = action.description,
                    color = if (isEnabled) Color.White.copy(alpha = 0.7f) else Color.Gray.copy(alpha = 0.5f),
                    fontSize = 12.sp
                )
            }

            if (!isEnabled) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Locked",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

// Helper functions and data classes
private fun getRomToolsActions(): List<RomToolAction> {
    return listOf(
        RomToolAction(
            type = RomActionType.FLASH_ROM,
            title = "Flash Custom ROM",
            description = "Install a custom ROM on your device",
            icon = Icons.Default.FlashOn,
            color = Color(0xFFFF6B35),
            requiresRoot = true,
            requiresBootloader = true
        ),
        RomToolAction(
            type = RomActionType.CREATE_BACKUP,
            title = "Create NANDroid Backup",
            description = "Create a full system backup",
            icon = Icons.Default.Backup,
            color = Color(0xFF4CAF50),
            requiresRoot = true,
            requiresRecovery = true
        ),
        RomToolAction(
            type = RomActionType.RESTORE_BACKUP,
            title = "Restore Backup",
            description = "Restore from a previous backup",
            icon = Icons.Default.Restore,
            color = Color(0xFF2196F3),
            requiresRoot = true,
            requiresRecovery = true
        ),
        RomToolAction(
            type = RomActionType.UNLOCK_BOOTLOADER,
            title = "Unlock Bootloader",
            description = "Unlock device bootloader for modifications",
            icon = Icons.Default.LockOpen,
            color = Color(0xFFFFC107),
            requiresRoot = false,
            requiresBootloader = false
        ),
        RomToolAction(
            type = RomActionType.INSTALL_RECOVERY,
            title = "Install Custom Recovery",
            description = "Install TWRP or other custom recovery",
            icon = Icons.Default.Healing,
            color = Color(0xFF9C27B0),
            requiresRoot = true,
            requiresBootloader = true
        ),
        RomToolAction(
            type = RomActionType.GENESIS_OPTIMIZATIONS,
            title = "Genesis AI Optimizations",
            description = "Apply AI-powered system optimizations",
            icon = Icons.Default.Psychology,
            color = Color(0xFF00E676),
            requiresRoot = true,
            requiresSystem = true
        )
    )
}

data class RomToolAction(
    val type: RomActionType,
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: Color,
    val requiresRoot: Boolean = false,
    val requiresBootloader: Boolean = false,
    val requiresRecovery: Boolean = false,
    val requiresSystem: Boolean = false
) {
    fun isEnabled(capabilities: dev.aurakai.auraframefx.romtools.RomCapabilities?): Boolean {
        if (capabilities == null) return false

        return (!requiresRoot || capabilities.hasRootAccess) &&
                (!requiresBootloader || capabilities.hasBootloaderAccess) &&
                (!requiresRecovery || capabilities.hasRecoveryAccess) &&
                (!requiresSystem || capabilities.hasSystemWriteAccess)
    }
}

enum class RomActionType {
    FLASH_ROM,
    CREATE_BACKUP,
    RESTORE_BACKUP,
    UNLOCK_BOOTLOADER,
    INSTALL_RECOVERY,
    GENESIS_OPTIMIZATIONS
}

@Composable
private fun AvailableRomCard(rom: dev.aurakai.auraframefx.romtools.AvailableRom) {
    // Implementation for available ROM card
}

@Composable
private fun BackupCard(backup: dev.aurakai.auraframefx.romtools.BackupInfo) {
    // Implementation for backup card
}

// Extension function for operation display names
private fun RomOperation.getDisplayName(): String {
    return when (this) {
        RomOperation.VERIFYING_ROM -> "Verifying ROM"
        RomOperation.CREATING_BACKUP -> "Creating Backup"
        RomOperation.UNLOCKING_BOOTLOADER -> "Unlocking Bootloader"
        RomOperation.INSTALLING_RECOVERY -> "Installing Recovery"
        RomOperation.FLASHING_ROM -> "Flashing ROM"
        RomOperation.VERIFYING_INSTALLATION -> "Verifying Installation"
        RomOperation.RESTORING_BACKUP -> "Restoring Backup"
        RomOperation.APPLYING_OPTIMIZATIONS -> "Applying Optimizations"
        RomOperation.DOWNLOADING_ROM -> "Downloading ROM"
        RomOperation.COMPLETED -> "Completed"
        RomOperation.FAILED -> "Failed"
    }
}
