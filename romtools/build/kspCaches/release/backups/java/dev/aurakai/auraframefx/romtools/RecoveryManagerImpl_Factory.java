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
public final class RecoveryManagerImpl_Factory implements Factory<RecoveryManagerImpl> {
  @Override
  public RecoveryManagerImpl get() {
    return newInstance();
  }

  public static RecoveryManagerImpl_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static RecoveryManagerImpl newInstance() {
    return new RecoveryManagerImpl();
  }

  private static final class InstanceHolder {
    static final RecoveryManagerImpl_Factory INSTANCE = new RecoveryManagerImpl_Factory();
  }
}
