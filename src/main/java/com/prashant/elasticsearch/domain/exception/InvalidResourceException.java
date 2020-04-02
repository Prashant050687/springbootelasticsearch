package com.prashant.elasticsearch.domain.exception;

import java.util.List;

public class InvalidResourceException extends RuntimeException {
  /**
   * 
   */
  private static final long serialVersionUID = 9170259170212695261L;
  private final List<String> errors;

  public InvalidResourceException(List<String> validationErrors) {
    this.errors = validationErrors;
  }

  @Override
  public String getMessage() {
    return "List of errors: " + errors.toString();
  }
}
