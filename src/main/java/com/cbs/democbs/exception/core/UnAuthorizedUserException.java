package com.cbs.democbs.exception.core;

import java.io.Serial;
import lombok.Getter;

@Getter
public class UnAuthorizedUserException extends RuntimeException {

  @Serial private static final long serialVersionUID = 7720607075311050594L;
  private final String globalisationMessageCode;
  private final String defaultUserMessage;

  public UnAuthorizedUserException(
      final String globalisationMessageCode, final String defaultUserMessage) {
    this.globalisationMessageCode = globalisationMessageCode;
    this.defaultUserMessage = defaultUserMessage;
  }

  public UnAuthorizedUserException() {
    this.globalisationMessageCode = "error.msg.user.unauthorized";
    this.defaultUserMessage = "User is not allowed to access resource ";
  }

  public UnAuthorizedUserException(final String message) {
    this.globalisationMessageCode = "error.msg.user.unauthorized";
    this.defaultUserMessage = "message";
  }
}
