package com.rateprovider.presentation.rate.configuration;

import com.rateprovider.presentation.rate.provider.ProviderType;
import com.rateprovider.presentation.rate.provider.RateProvider;
import com.rateprovider.presentation.rate.RateService;
import com.rateprovider.presentation.rate.RateServiceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class ProviderConfiguration {

  private static final ProviderType DEFAULT_PROVIDER = ProviderType.MINFIN;

  @Bean
  public Map<ProviderType, RateProvider> providerByType(List<RateProvider> providers) {
    return providers.stream().collect(Collectors.toMap(RateProvider::getType, e -> e));
  }

  @Bean
  public RateService rateProvider(ProviderSettings settings, Map<ProviderType, RateProvider> providers) {
    ProviderType mainProviderType = ProviderType.getType(settings.name()).orElse(DEFAULT_PROVIDER);
    List<RateProvider> rateProviders =
        providers.values().stream().filter(e -> e.getType() != mainProviderType).collect(Collectors.toList());
    rateProviders.add(0, providers.get(mainProviderType));
    return RateServiceFactory.build(settings.cached(), rateProviders);
  }

  @ConstructorBinding
  @ConfigurationProperties(prefix = "provider")
  record ProviderSettings(String name, boolean cached) {}

}
