package com.cbs.democbs.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * This class holds OpenApi 3 configurations for this application.
 *
 * @version 1.0
 * @since 1.0
 */
@Configuration
@RequiredArgsConstructor
public class OpenApi30Config {

  private final OpenApiProperties properties;

  /**
   * Configures the OpenApi 3.0 bean.
   *
   * @return the OpenApi 3.0 bean
   */
  @Bean
  public OpenAPI customOpenAPI() {
    final String apiTitle = String.format("%s API", StringUtils.capitalize(properties.getName()));

    var info =
        new Info()
            .title(apiTitle)
            .version(properties.getVersion())
            .description(properties.getDescription());




    return new OpenAPI()
        .info(info);
  }
}
