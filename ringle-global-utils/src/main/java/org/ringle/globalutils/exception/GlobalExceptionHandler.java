package org.ringle.globalutils.exception;

import java.net.BindException;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Handles BindException, typically occurring from @ModelAttribute binding failures.
	 */
	@ExceptionHandler(BindException.class)
	protected ResponseEntity<ErrorResponse> handleParamViolationException(BindException ex) {
		BaseErrorCode errorCode = CommonErrorCode.REQUEST_PARAMETER_BIND_FAILED;
		log.warn("Parameter binding failed: {}", ex.getMessage(), ex);

		ErrorResponse error = ErrorResponse.builder()
			.status(errorCode.getHttpStatus().value())
			.message(ex.getMessage())
			.code(errorCode.getCode())
			.build();

		return new ResponseEntity<>(error, errorCode.getHttpStatus());
	}

	/**
	 * Handles custom exceptions that occur during business logic processing.
	 */
	@ExceptionHandler(CommonException.class)
	protected ResponseEntity<ErrorResponse> handleApplicationException(CommonException ex) {
		BaseErrorCode errorCode = ex.getErrorCode();
		log.warn("Application exception occurred: {}, ErrorCode: {}", ex.getMessage(), errorCode.getCode(), ex);

		ErrorResponse error = ErrorResponse.builder()
			.status(errorCode.getHttpStatus().value())
			.message(errorCode.getMessage())
			.code(errorCode.getCode())
			.build();

		return new ResponseEntity<>(error, errorCode.getHttpStatus());
	}

	/**
	 * Handles IllegalArgumentException.
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
		BaseErrorCode errorCode = CommonErrorCode.INVALID_REQUEST;
		log.warn("Illegal argument: {}", ex.getMessage(), ex);

		ErrorResponse error = ErrorResponse.builder()
			.status(errorCode.getHttpStatus().value())
			.message(ex.getMessage() != null ? ex.getMessage() : "")
			.code(errorCode.getCode())
			.build();

		return new ResponseEntity<>(error, errorCode.getHttpStatus());
	}

	/**
	 * Handles MethodArgumentNotValidException from @Valid on @RequestBody.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
		BaseErrorCode errorCode = CommonErrorCode.INVALID_REQUEST;
		String fieldErrors = ex.getBindingResult().getFieldErrors().stream()
			.map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
			.collect(Collectors.joining(", "));

		log.warn("Validation failed: {}", fieldErrors, ex);

		ErrorResponse error = ErrorResponse.builder()
			.status(errorCode.getHttpStatus().value())
			.message(fieldErrors)
			.code(errorCode.getCode())
			.build();

		return new ResponseEntity<>(error, errorCode.getHttpStatus());
	}

	/**
	 * Handles HttpRequestMethodNotSupportedException.
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException ex) {
		BaseErrorCode errorCode = CommonErrorCode.METHOD_NOT_ALLOWED;
		String supportedMethods =
			ex.getSupportedHttpMethods() != null ? String.join(", ", ex.getSupportedHttpMethods().toString()) : "N/A";
		log.warn("HTTP method not supported: {}. Supported methods: {}", ex.getMethod(), supportedMethods, ex);

		String message = String.format("HTTP method '%s' not supported for this endpoint. Supported methods: %s",
			ex.getMethod(), supportedMethods);
		ErrorResponse error = ErrorResponse.builder()
			.status(errorCode.getHttpStatus().value())
			.message(message)
			.code(errorCode.getCode())
			.build();

		return new ResponseEntity<>(error, errorCode.getHttpStatus());
	}

	/**
	 * Handles ConstraintViolationException for method parameter validation (@RequestParam, @PathVariable).
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
		BaseErrorCode errorCode = CommonErrorCode.INVALID_REQUEST;
		String constraintErrors = ex.getConstraintViolations().stream()
			.map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
			.collect(Collectors.joining(", "));

		log.warn("Constraint validation failed: {}", constraintErrors, ex);

		ErrorResponse error = ErrorResponse.builder()
			.status(errorCode.getHttpStatus().value())
			.message("Constraint validation failed: " + constraintErrors)
			.code(errorCode.getCode())
			.build();

		return new ResponseEntity<>(error, errorCode.getHttpStatus());
	}

	/**
	 * Handles HttpMessageNotReadableException for JSON parsing failures.
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<ErrorResponse> handleInvalidJson(HttpMessageNotReadableException ex) {
		BaseErrorCode errorCode = CommonErrorCode.MALFORMED_JSON;
		log.warn("Malformed JSON request: {}", ex.getLocalizedMessage(), ex);

		ErrorResponse error = ErrorResponse.builder()
			.status(errorCode.getHttpStatus().value())
			.message("Malformed JSON request: " + ex.getLocalizedMessage())
			.code(errorCode.getCode())
			.build();

		return new ResponseEntity<>(error, errorCode.getHttpStatus());
	}

	/**
	 * Handles all other uncaught exceptions.
	 */
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
		BaseErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
		log.error("Unexpected error occurred: {}", ex.getMessage(), ex);

		ErrorResponse error = ErrorResponse.builder()
			.status(errorCode.getHttpStatus().value())
			.message("An unexpected error occurred")
			.code(errorCode.getCode())
			.build();

		return new ResponseEntity<>(error, errorCode.getHttpStatus());
	}
}
