package com.prashant.elasticsearch.domain.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -2996794270148354390L;

  private final Object resourceId;

  private final String resourceType;

  /**
   * Instantiates a new instance of {@link ResourceNotFoundException}.
   *
   * @param resourceType type of the resource which cannot be found
   * @param resourceId   an identifier of the resource which cannot be found
   */
  public ResourceNotFoundException(String resourceType, Object resourceId) {
    this.resourceType = resourceType;
    this.resourceId = resourceId;
  }

  /**
   * Instantiates a new instance of {@link ResourceNotFoundException}.
   *
   * @param cause        an original cause of the error
   * @param resourceType type of the resource which cannot be found
   * @param resourceId   an identifier of the resource which cannot be found
   */
  public ResourceNotFoundException(Throwable cause, String resourceType, Object resourceId) {
    super(cause);
    this.resourceType = resourceType;
    this.resourceId = resourceId;
  }

  /**
   * Instantiates a new instance of {@link ResourceNotFoundException}.
   *
   * @param message      custom message
   * @param cause        an original cause of the error
   * @param resourceType type of the resource which cannot be found
   * @param resourceId   an identifier of the resource which cannot be found
   */
  public ResourceNotFoundException(String message, Throwable cause, String resourceType, Object resourceId) {
    super(message, cause);
    this.resourceType = resourceType;
    this.resourceId = resourceId;
  }
}
