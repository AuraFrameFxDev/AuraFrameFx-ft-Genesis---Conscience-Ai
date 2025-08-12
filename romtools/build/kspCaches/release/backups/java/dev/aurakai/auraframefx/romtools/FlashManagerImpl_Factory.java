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
public final class FlashManagerImpl_Factory implements Factory<FlashManagerImpl> {
  @Override
  public FlashManagerImpl get() {
    return newInstance();
  }

  public static FlashManagerImpl_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FlashManagerImpl newInstance() {
    return new FlashManagerImpl();
  }

  private static final class InstanceHolder {
    static final FlashManagerImpl_Factory INSTANCE = new FlashManagerImpl_Factory();
  }
}
