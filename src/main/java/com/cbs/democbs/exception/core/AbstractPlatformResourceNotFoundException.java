package com.cbs.democbs.exception.core;

import java.io.Serial;
import lombok.Getter;

@Getter
public class AbstractPlatformResourceNotFoundException extends RuntimeException {

  @Serial private static final long serialVersionUID = -939191641498540645L;
  private final String globalisationMessageCode;
  private final String defaultUserMessage;

  public AbstractPlatformResourceNotFoundException(
      String globalisationMessageCode, String defaultUserMessage) {
    this.globalisationMessageCode = globalisationMessageCode;
    this.defaultUserMessage = defaultUserMessage;
  }

}
