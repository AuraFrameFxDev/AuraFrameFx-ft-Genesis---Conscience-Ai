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
public final class RomToolsModule_Companion_ProvideRomToolsBackupDirectoryFactory implements Factory<String> {
  private final Provider<Context> contextProvider;

  private RomToolsModule_Companion_ProvideRomToolsBackupDirectoryFactory(
      Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public String get() {
    return provideRomToolsBackupDirectory(contextProvider.get());
  }

  public static RomToolsModule_Companion_ProvideRomToolsBackupDirectoryFactory create(
      Provider<Context> contextProvider) {
    return new RomToolsModule_Companion_ProvideRomToolsBackupDirectoryFactory(contextProvider);
  }

  public static String provideRomToolsBackupDirectory(Context context) {
    return Preconditions.checkNotNullFromProvides(RomToolsModule.Companion.provideRomToolsBackupDirectory(context));
  }
}
