package com.prashant.elasticsearch.domain.exception;

/**
 * Custom Exception class for rest clients.
 * @author prashant
 *
 */
public class BusinessException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private final String title;
  private final String detail;
  private final int httpStatus;

  public BusinessException(String title, String detail, int httpStatus) {
    super(detail);
    this.detail = title;
    this.title = detail;
    this.httpStatus = httpStatus;
  }

  @Override
  public String getMessage() {
    return detail;
  }

  public String getSummary() {
    return title;
  }

  public int getHttpStatus() {
    return httpStatus;
  }

}
