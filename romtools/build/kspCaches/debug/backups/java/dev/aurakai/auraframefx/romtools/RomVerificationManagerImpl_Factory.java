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
public final class RomVerificationManagerImpl_Factory implements Factory<RomVerificationManagerImpl> {
  @Override
  public RomVerificationManagerImpl get() {
    return newInstance();
  }

  public static RomVerificationManagerImpl_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static RomVerificationManagerImpl newInstance() {
    return new RomVerificationManagerImpl();
  }

  private static final class InstanceHolder {
    static final RomVerificationManagerImpl_Factory INSTANCE = new RomVerificationManagerImpl_Factory();
  }
}
