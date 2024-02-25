package com.cbs.democbs.common;

import org.junit.jupiter.api.AfterEach;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresDatabaseContainerInitializer
    implements ApplicationContextInitializer<ConfigurableApplicationContext> {
  private static final PostgreSQLContainer<?> sqlContainer =
      new PostgreSQLContainer<>("postgres:15-alpine")
          .withDatabaseName("integration-tests-db")
          .withReuse(true)
          .withUsername("sa")
          .withPassword("sa");

  static {
    sqlContainer.start();
  }

  public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
    TestPropertyValues.of(
            "spring.datasource.url=" + sqlContainer.getJdbcUrl(),
            "spring.datasource.driver-class-name=org.postgresql.Driver",
            "spring.jpa.hibernate.ddl-auto=create-drop",
            "spring.datasource.username=" + sqlContainer.getUsername(),
            "spring.datasource.password=" + sqlContainer.getPassword())
        .applyTo(configurableApplicationContext.getEnvironment());
  }

  @AfterEach
  void tearDown() {
    System.out.println("Stopping container...");
    sqlContainer.stop();
    // You can perform cleanup operations here after each test method
  }
}
