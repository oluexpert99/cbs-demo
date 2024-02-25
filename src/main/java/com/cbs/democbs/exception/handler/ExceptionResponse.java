package com.cbs.democbs.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.context.request.WebRequest;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionResponse {
  private Date timestamp;
  private String message;
  private List<String> details;
  private String globalisationMessageCode;

  public ExceptionResponse(Date timestamp, String message, List<String> details) {
    this.timestamp = timestamp;
    this.message = message;
    this.details = details;
  }

  public ExceptionResponse(Date timestamp, String message, String details) {
    this.timestamp = timestamp;
    this.message = message;
    this.details = Collections.singletonList(details);
  }

  public ExceptionResponse(
      Date timestamp, String message, String details, String globalisationMessageCode) {
    this.timestamp = timestamp;
    this.message = message;
    this.details = Collections.singletonList(details);
    this.globalisationMessageCode = globalisationMessageCode;
  }

  public static ExceptionResponse getCodeAndNarration(RuntimeException e, WebRequest request) {
    return new ExceptionResponse(
        new Date(), e.getLocalizedMessage(), request.getDescription(false));
  }
}
