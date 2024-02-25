package com.cbs.democbs.exception.core;

import java.io.Serial;
import lombok.Getter;

@Getter
public class AbstractPlatformDomainRuleException extends RuntimeException {

  @Serial private static final long serialVersionUID = -6359775773306040512L;
  private final String globalisationMessageCode;
  private final String defaultUserMessage;

  public AbstractPlatformDomainRuleException(
      String globalisationMessageCode, String defaultUserMessage) {
    this.globalisationMessageCode = globalisationMessageCode;
    this.defaultUserMessage = defaultUserMessage;
  }
}
