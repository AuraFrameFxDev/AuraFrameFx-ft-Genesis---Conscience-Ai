package dev.aurakai.auraframefx.romtools;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
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
public final class BackupManagerImpl_Factory implements Factory<BackupManagerImpl> {
  @Override
  public BackupManagerImpl get() {
    return newInstance();
  }

  public static BackupManagerImpl_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static BackupManagerImpl newInstance() {
    return new BackupManagerImpl();
  }

  private static final class InstanceHolder {
    static final BackupManagerImpl_Factory INSTANCE = new BackupManagerImpl_Factory();
  }
}
