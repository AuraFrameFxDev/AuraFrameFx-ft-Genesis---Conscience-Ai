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
public final class SystemModificationManagerImpl_Factory implements Factory<SystemModificationManagerImpl> {
  @Override
  public SystemModificationManagerImpl get() {
    return newInstance();
  }

  public static SystemModificationManagerImpl_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SystemModificationManagerImpl newInstance() {
    return new SystemModificationManagerImpl();
  }

  private static final class InstanceHolder {
    static final SystemModificationManagerImpl_Factory INSTANCE = new SystemModificationManagerImpl_Factory();
  }
}
