package dev.aurakai.auraframefx.securecomm.protocol;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.aurakai.auraframefx.securecomm.crypto.CryptoManager;
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
public final class SecureChannel_Factory implements Factory<SecureChannel> {
  private final Provider<CryptoManager> cryptoManagerProvider;

  private SecureChannel_Factory(Provider<CryptoManager> cryptoManagerProvider) {
    this.cryptoManagerProvider = cryptoManagerProvider;
  }

  @Override
  public SecureChannel get() {
    return newInstance(cryptoManagerProvider.get());
  }

  public static SecureChannel_Factory create(Provider<CryptoManager> cryptoManagerProvider) {
    return new SecureChannel_Factory(cryptoManagerProvider);
  }

  public static SecureChannel newInstance(CryptoManager cryptoManager) {
    return new SecureChannel(cryptoManager);
  }
}
