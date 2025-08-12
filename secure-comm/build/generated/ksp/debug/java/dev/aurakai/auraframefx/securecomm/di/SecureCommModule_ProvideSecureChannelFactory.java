package dev.aurakai.auraframefx.securecomm.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.aurakai.auraframefx.securecomm.crypto.CryptoManager;
import dev.aurakai.auraframefx.securecomm.protocol.SecureChannel;
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
public final class SecureCommModule_ProvideSecureChannelFactory implements Factory<SecureChannel> {
  private final Provider<CryptoManager> cryptoManagerProvider;

  private SecureCommModule_ProvideSecureChannelFactory(
      Provider<CryptoManager> cryptoManagerProvider) {
    this.cryptoManagerProvider = cryptoManagerProvider;
  }

  @Override
  public SecureChannel get() {
    return provideSecureChannel(cryptoManagerProvider.get());
  }

  public static SecureCommModule_ProvideSecureChannelFactory create(
      Provider<CryptoManager> cryptoManagerProvider) {
    return new SecureCommModule_ProvideSecureChannelFactory(cryptoManagerProvider);
  }

  public static SecureChannel provideSecureChannel(CryptoManager cryptoManager) {
    return Preconditions.checkNotNullFromProvides(SecureCommModule.INSTANCE.provideSecureChannel(cryptoManager));
  }
}
