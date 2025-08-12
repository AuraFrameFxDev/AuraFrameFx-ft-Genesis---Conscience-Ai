package dev.aurakai.auraframefx.romtools.bootloader;

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
public final class BootloaderManagerImpl_Factory implements Factory<BootloaderManagerImpl> {
  @Override
  public BootloaderManagerImpl get() {
    return newInstance();
  }

  public static BootloaderManagerImpl_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static BootloaderManagerImpl newInstance() {
    return new BootloaderManagerImpl();
  }

  private static final class InstanceHolder {
    static final BootloaderManagerImpl_Factory INSTANCE = new BootloaderManagerImpl_Factory();
  }
}
