package com.prashant.elasticsearch.domain.exception;

import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.prashant.elasticsearch.dto.ErrorDetail;

/**
 * Allows to handle all expected and unexpected errors occurred while processing the request.
 *
 * @author Vadym Lotar
 * @see ControllerAdvice
 * @since 0.1.0.RELEASE
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

  private static final String APPLICATION_PROBLEM_JSON = "application/problem+json";

  /**
   * Handles a case when requested resource cannot be found
   *
   * @param e
   *           any exception of type {@link Exception}
   * @return {@link ResponseEntity} containing standard body in case of errors
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public HttpEntity<ErrorDetail> handleResourceNotFoundException(ResourceNotFoundException e,
    final HttpServletRequest request) {

    LOGGER.error("Resource {} of type {} cannot be found", e.getResourceId(),
      e.getResourceType());

    ErrorDetail problem = new ErrorDetail("Resource not found",
      "Requested resource cannot be found");
    problem.setStatus(HttpStatus.NOT_FOUND.value());

    return new ResponseEntity<>(problem, overrideContentType(), HttpStatus.NOT_FOUND);
  }

  /**
   * Handles a case when message from request body cannot be de-serialized
   *
   * @param e
   *           any exception of type {@link HttpMessageNotReadableException}
   * @return {@link ResponseEntity} containing standard body in case of errors
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public HttpEntity<ErrorDetail> handleHttpMessageNotReadableException(
    HttpMessageNotReadableException e, final HttpServletRequest request) {

    LOGGER.error("Cannot de-serialize message in request body: {}", e.getMessage());

    final ErrorDetail problem = new ErrorDetail("Message cannot be converted",
      String.format("Invalid request body: %s", e.getMessage()));
    problem.setStatus(HttpStatus.BAD_REQUEST.value());

    return new ResponseEntity<>(problem, overrideContentType(), HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles a case when validation of the request body fails
   *
   * @param e
   *           any exception of type {@link MethodArgumentNotValidException}
   * @return {@link ResponseEntity} containing standard body in case of errors
   */
  @ExceptionHandler(AccessDeniedException.class)
  public HttpEntity<ErrorDetail> handleAccessDeniedException(
    AccessDeniedException e, final HttpServletRequest request) {

    LOGGER.error("Access Denied: {}", e.getMessage());

    final ErrorDetail problem = new ErrorDetail(
      "Access Denied - You do not have permission to access the operation",
      e.getMessage());
    problem.setStatus(HttpStatus.FORBIDDEN.value());

    return new ResponseEntity<>(problem, overrideContentType(), HttpStatus.FORBIDDEN);
  }

  /**
   * Handles a case when there are business Exceptions
   
   */
  @ExceptionHandler(BusinessException.class)
  public HttpEntity<ErrorDetail> handleBusinessException(
    AccessDeniedException e, final HttpServletRequest request) {

    LOGGER.error("Business Exception: {}", e.getMessage());

    final ErrorDetail problem = new ErrorDetail(
      "Business Exception",
      e.getMessage());
    problem.setStatus(HttpStatus.BAD_REQUEST.value());

    return new ResponseEntity<>(problem, overrideContentType(), HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles a case when validation of the request body fails
   *
   * @param e
   *           any exception of type {@link MethodArgumentTypeMismatchException}
   * @return {@link ResponseEntity} containing standard body in case of errors
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public HttpEntity<ErrorDetail> handleMethodArgumentTypeMismatchException(
    MethodArgumentTypeMismatchException e, final HttpServletRequest request) {

    LOGGER.error("Request body is invalid: {}", e.getMessage());

    final ErrorDetail problem = new ErrorDetail("Field type mismatch", null);
    problem.setStatus(HttpStatus.BAD_REQUEST.value());
    problem.setErrors(Collections.singletonList(new ErrorDetail("Wrong field value format",
      String.format("Incorrect value '%s' for field '%s'. Expected value type '%s'",
        e.getValue(), e.getName(), e.getParameter().getParameterType().getTypeName()))));

    return new ResponseEntity<>(problem, overrideContentType(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(javax.validation.ConstraintViolationException.class)
  public HttpEntity<ErrorDetail> handleConstraintViolationsFromJavax(
    javax.validation.ConstraintViolationException e, final HttpServletRequest request) {

    LOGGER.error("Constraint Violation: {}", e.getMessage());

    return validationError(e.getMessage(), e.getConstraintViolations().stream()
      .map(violation -> new ErrorDetail("Invalid Parameter", violation.getMessage()))
      .collect(Collectors.toList()), request);
  }

  public HttpEntity<ErrorDetail> validationError(final String exceptionMessage,
    final List<ErrorDetail> details,
    final HttpServletRequest request) {

    LOGGER.debug("Request body is invalid: {}", exceptionMessage);

    final ErrorDetail problem = new ErrorDetail("Field type mismatch", null);
    problem.setStatus(HttpStatus.BAD_REQUEST.value());
    problem.setErrors(details);

    return new ResponseEntity<>(problem, overrideContentType(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EmptyResultDataAccessException.class)
  public HttpEntity<ErrorDetail> handleEmptyResultDataAccessException(
    EmptyResultDataAccessException e, final HttpServletRequest request) {

    LOGGER.error("EmptyResultDataAccessException: {}", e.getMessage());

    ErrorDetail problem = new ErrorDetail("Resource not found",
      "Requested resource cannot be found");
    problem.setStatus(HttpStatus.NOT_FOUND.value());

    return new ResponseEntity<>(problem, overrideContentType(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
  public HttpEntity<ErrorDetail> handleObjectOptimisticLockingFailureException(
    ObjectOptimisticLockingFailureException e, final HttpServletRequest request) {

    LOGGER.error("ObjectOptimisticLockingFailureException: {}", e.getMessage());

    ErrorDetail problem = new ErrorDetail("Optimistic Locking Failure",
      "Update on an outdated version is not allowed.");
    problem.setStatus(HttpStatus.LOCKED.value());

    return new ResponseEntity<>(problem, overrideContentType(), HttpStatus.LOCKED);
  }

  /**
   * Handles {@link InvalidResourceException}
   *
   * @param e any exception of type {@link InvalidResourceException}
   * @return {@link ResponseEntity} containing standard body in case of errors
   */
  @ExceptionHandler(InvalidResourceException.class)
  public HttpEntity<ErrorDetail> handleInvalidAssetResourceException(InvalidResourceException e,
    final HttpServletRequest request) {

    LOGGER.debug("Invalid resource provided: {}", e.getMessage());

    final ErrorDetail problem = new ErrorDetail("Invalid resource provided", e.getMessage());
    problem.setStatus(HttpStatus.BAD_REQUEST.value());

    return new ResponseEntity<>(problem, overrideContentType(), HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles all unexpected situations
   *
   * @param e
   *           any exception of type {@link Exception}
   * @return {@link ResponseEntity} containing standard body in case of errors
   */
  @ExceptionHandler(Exception.class)
  public HttpEntity<ErrorDetail> handleException(Exception e, final HttpServletRequest request) {

    LOGGER.error("An unexpected error occurred", e);

    ErrorDetail problem = new ErrorDetail("Internal Error",
      "An unexpected error has occurred");
    problem.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

    return new ResponseEntity<>(problem, overrideContentType(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private HttpHeaders overrideContentType() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Content-Type", APPLICATION_PROBLEM_JSON);
    return httpHeaders;
  }

}
