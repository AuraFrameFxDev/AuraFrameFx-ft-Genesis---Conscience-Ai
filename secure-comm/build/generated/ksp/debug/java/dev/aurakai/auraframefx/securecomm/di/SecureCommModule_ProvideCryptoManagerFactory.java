package dev.aurakai.auraframefx.securecomm.di;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.aurakai.auraframefx.securecomm.crypto.CryptoManager;
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
public final class SecureCommModule_ProvideCryptoManagerFactory implements Factory<CryptoManager> {
  private final Provider<Context> contextProvider;

  private final Provider<SecureKeyStore> secureKeyStoreProvider;

  private SecureCommModule_ProvideCryptoManagerFactory(Provider<Context> contextProvider,
      Provider<SecureKeyStore> secureKeyStoreProvider) {
    this.contextProvider = contextProvider;
    this.secureKeyStoreProvider = secureKeyStoreProvider;
  }

  @Override
  public CryptoManager get() {
    return provideCryptoManager(contextProvider.get(), secureKeyStoreProvider.get());
  }

  public static SecureCommModule_ProvideCryptoManagerFactory create(
      Provider<Context> contextProvider, Provider<SecureKeyStore> secureKeyStoreProvider) {
    return new SecureCommModule_ProvideCryptoManagerFactory(contextProvider, secureKeyStoreProvider);
  }

  public static CryptoManager provideCryptoManager(Context context, SecureKeyStore secureKeyStore) {
    return Preconditions.checkNotNullFromProvides(SecureCommModule.INSTANCE.provideCryptoManager(context, secureKeyStore));
  }
}
