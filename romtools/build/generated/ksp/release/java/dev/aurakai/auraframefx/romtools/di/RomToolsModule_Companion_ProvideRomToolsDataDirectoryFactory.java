package dev.aurakai.auraframefx.romtools.di;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
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
public final class RomToolsModule_Companion_ProvideRomToolsDataDirectoryFactory implements Factory<String> {
  private final Provider<Context> contextProvider;

  private RomToolsModule_Companion_ProvideRomToolsDataDirectoryFactory(
      Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public String get() {
    return provideRomToolsDataDirectory(contextProvider.get());
  }

  public static RomToolsModule_Companion_ProvideRomToolsDataDirectoryFactory create(
      Provider<Context> contextProvider) {
    return new RomToolsModule_Companion_ProvideRomToolsDataDirectoryFactory(contextProvider);
  }

  public static String provideRomToolsDataDirectory(Context context) {
    return Preconditions.checkNotNullFromProvides(RomToolsModule.Companion.provideRomToolsDataDirectory(context));
  }
}
