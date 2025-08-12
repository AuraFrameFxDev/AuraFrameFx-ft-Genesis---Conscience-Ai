package dev.aurakai.auraframefx.securecomm.di;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.aurakai.auraframefx.securecomm.keystore.SecureKeyStore;
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
public final class SecureCommModule_ProvideSecureKeyStoreFactory implements Factory<SecureKeyStore> {
  private final Provider<Context> contextProvider;

  private SecureCommModule_ProvideSecureKeyStoreFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public SecureKeyStore get() {
    return provideSecureKeyStore(contextProvider.get());
  }

  public static SecureCommModule_ProvideSecureKeyStoreFactory create(
      Provider<Context> contextProvider) {
    return new SecureCommModule_ProvideSecureKeyStoreFactory(contextProvider);
  }

  public static SecureKeyStore provideSecureKeyStore(Context context) {
    return Preconditions.checkNotNullFromProvides(SecureCommModule.INSTANCE.provideSecureKeyStore(context));
  }
}
