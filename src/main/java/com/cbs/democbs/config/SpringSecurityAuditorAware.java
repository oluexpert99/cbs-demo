package com.cbs.democbs.config;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;

@Slf4j
public class SpringSecurityAuditorAware implements AuditorAware<String> {
  private static final String CURRENT_AUDITOR = "system";

  /**
   * Returns the current auditor of the application.
   *
   * @return the current auditor
   */
  @NonNull
  @Override
  public Optional<String> getCurrentAuditor() {
  return  Optional.of(CURRENT_AUDITOR);
  }
}
