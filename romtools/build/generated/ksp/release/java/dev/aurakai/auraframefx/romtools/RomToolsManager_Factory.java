package dev.aurakai.auraframefx.romtools;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.aurakai.auraframefx.romtools.bootloader.BootloaderManager;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class RomToolsManager_Factory implements Factory<RomToolsManager> {
  private final Provider<Context> contextProvider;

  private final Provider<BootloaderManager> bootloaderManagerProvider;

  private final Provider<RecoveryManager> recoveryManagerProvider;

  private final Provider<SystemModificationManager> systemModificationManagerProvider;

  private final Provider<FlashManager> flashManagerProvider;

  private final Provider<RomVerificationManager> verificationManagerProvider;

  private final Provider<BackupManager> backupManagerProvider;

  private RomToolsManager_Factory(Provider<Context> contextProvider,
      Provider<BootloaderManager> bootloaderManagerProvider,
      Provider<RecoveryManager> recoveryManagerProvider,
      Provider<SystemModificationManager> systemModificationManagerProvider,
      Provider<FlashManager> flashManagerProvider,
      Provider<RomVerificationManager> verificationManagerProvider,
      Provider<BackupManager> backupManagerProvider) {
    this.contextProvider = contextProvider;
    this.bootloaderManagerProvider = bootloaderManagerProvider;
    this.recoveryManagerProvider = recoveryManagerProvider;
    this.systemModificationManagerProvider = systemModificationManagerProvider;
    this.flashManagerProvider = flashManagerProvider;
    this.verificationManagerProvider = verificationManagerProvider;
    this.backupManagerProvider = backupManagerProvider;
  }

  @Override
  public RomToolsManager get() {
    return newInstance(contextProvider.get(), bootloaderManagerProvider.get(), recoveryManagerProvider.get(), systemModificationManagerProvider.get(), flashManagerProvider.get(), verificationManagerProvider.get(), backupManagerProvider.get());
  }

  public static RomToolsManager_Factory create(Provider<Context> contextProvider,
      Provider<BootloaderManager> bootloaderManagerProvider,
      Provider<RecoveryManager> recoveryManagerProvider,
      Provider<SystemModificationManager> systemModificationManagerProvider,
      Provider<FlashManager> flashManagerProvider,
      Provider<RomVerificationManager> verificationManagerProvider,
      Provider<BackupManager> backupManagerProvider) {
    return new RomToolsManager_Factory(contextProvider, bootloaderManagerProvider, recoveryManagerProvider, systemModificationManagerProvider, flashManagerProvider, verificationManagerProvider, backupManagerProvider);
  }

  public static RomToolsManager newInstance(Context context, BootloaderManager bootloaderManager,
      RecoveryManager recoveryManager, SystemModificationManager systemModificationManager,
      FlashManager flashManager, RomVerificationManager verificationManager,
      BackupManager backupManager) {
    return new RomToolsManager(context, bootloaderManager, recoveryManager, systemModificationManager, flashManager, verificationManager, backupManager);
  }
}
