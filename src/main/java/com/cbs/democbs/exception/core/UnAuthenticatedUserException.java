package com.cbs.democbs.exception.core;

import java.io.Serial;
import lombok.Getter;

@Getter
public class UnAuthenticatedUserException extends RuntimeException {

  @Serial private static final long serialVersionUID = 666229595832200377L;
  private final String globalisationMessageCode;
  private final String defaultUserMessage;

  public UnAuthenticatedUserException(
      final String globalisationMessageCode, final String defaultUserMessage) {
    this.globalisationMessageCode = globalisationMessageCode;
    this.defaultUserMessage = defaultUserMessage;
  }

  public UnAuthenticatedUserException() {
    this.globalisationMessageCode = "error.msg.user.unauthenticated";
    this.defaultUserMessage = "Unauthenticated User ";
  }

  public UnAuthenticatedUserException(final String message) {
    this.globalisationMessageCode = "error.msg.user.unauthenticated";
    this.defaultUserMessage = message;
  }
}
