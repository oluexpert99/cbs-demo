package com.cbs.democbs.exception.core;

import java.io.Serial;
import lombok.Getter;

@Getter
public class PlatformServiceException extends RuntimeException {

  @Serial private static final long serialVersionUID = -5921900912692310320L;
  private final String globalisationMessageCode;
  private final String defaultUserMessage;

  public PlatformServiceException(
      final String globalisationMessageCode, final String defaultUserMessage) {
    this.globalisationMessageCode = globalisationMessageCode;
    this.defaultUserMessage = defaultUserMessage;
  }

  public PlatformServiceException(final Throwable throwable) {
    this.globalisationMessageCode = "error.msg.platform.service.exception";
    this.defaultUserMessage = throwable.getMessage();
  }
}
