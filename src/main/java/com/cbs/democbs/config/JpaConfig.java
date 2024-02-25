package com.cbs.democbs.config;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * This class holds JPA configurations for this application.
 *
 * @version 1.0
 * @since 1.0
 */
@Configuration
@EnableCaching
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaConfig {

  /**
   * AuditorAware bean used for auditing.
   *
   * @return Application implementation of AuditorAware.
   */
  @Bean
  public AuditorAware<String> auditorAware() {
    return new SpringSecurityAuditorAware();
  }


  @Bean
  public JdbcTemplate jdbcTemplate(HikariDataSource hikariDataSource) {
    return new JdbcTemplate(hikariDataSource);
  }
}
