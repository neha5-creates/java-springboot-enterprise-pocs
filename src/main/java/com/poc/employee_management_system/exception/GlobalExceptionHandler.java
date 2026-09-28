package com.poc.employee_management_system.exception;
import com.poc.employee_management_system.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log =
            LoggerFactory.getLogger(
                    GlobalExceptionHandler.class
            );
    @ExceptionHandler(
            EmployeeNotFoundException.class
    )
    public ResponseEntity<ApiErrorResponse>
    handleEmployeeNotFound(
            EmployeeNotFoundException exception,
            HttpServletRequest request) {
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "EMPLOYEE_NOT_FOUND",
                exception.getMessage(),
                request.getRequestURI()
        );
    }
    @ExceptionHandler(
            DepartmentNotFoundException.class
    )
    public ResponseEntity<ApiErrorResponse>
    handleDepartmentNotFound(
            DepartmentNotFoundException exception,
            HttpServletRequest request) {
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "DEPARTMENT_NOT_FOUND",
                exception.getMessage(),
                request.getRequestURI()
        );
    }
    @ExceptionHandler(
            ManagerNotFoundException.class
    )
    public ResponseEntity<ApiErrorResponse>
    handleManagerNotFound(
            ManagerNotFoundException exception,
            HttpServletRequest request) {
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "MANAGER_NOT_FOUND",
                exception.getMessage(),
                request.getRequestURI()
        );
    }
    @ExceptionHandler(
            DuplicateEmailException.class
    )
    public ResponseEntity<ApiErrorResponse>
    handleDuplicateEmail(
            DuplicateEmailException exception,
            HttpServletRequest request) {
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "DUPLICATE_EMPLOYEE_EMAIL",
                exception.getMessage(),
                request.getRequestURI()
        );
    }
    @ExceptionHandler(
            DuplicateDepartmentCodeException.class
    )
    public ResponseEntity<ApiErrorResponse>
    handleDuplicateDepartmentCode(
            DuplicateDepartmentCodeException exception,
            HttpServletRequest request) {
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "DUPLICATE_DEPARTMENT_CODE",
                exception.getMessage(),
                request.getRequestURI()
        );
    }
    @ExceptionHandler(
            InvalidRequestException.class
    )
    public ResponseEntity<ApiErrorResponse>
    handleInvalidRequest(
            InvalidRequestException exception,
            HttpServletRequest request) {
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "INVALID_REQUEST",
                exception.getMessage(),
                request.getRequestURI()
        );
    }
    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    public ResponseEntity<ValidationErrorResponse>
    handleValidationException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {
        Map<String, String> fieldErrors =
                new LinkedHashMap<>();
        for (FieldError fieldError
                : exception.getBindingResult()
                .getFieldErrors()) {
            fieldErrors.putIfAbsent(
                    fieldError.getField(),
                    fieldError.getDefaultMessage()
            );
        }
        ValidationErrorResponse response =
                new ValidationErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST
                                .getReasonPhrase(),
                        "VALIDATION_FAILED",
                        "Request validation failed",
                        request.getRequestURI(),
                        fieldErrors
                );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
    @ExceptionHandler(
            MethodArgumentTypeMismatchException.class
    )
    public ResponseEntity<ApiErrorResponse>
    handleTypeMismatch(
            MethodArgumentTypeMismatchException exception,
            HttpServletRequest request) {
        String message =
                "Invalid value '"
                        + exception.getValue()
                        + "' for parameter '"
                        + exception.getName()
                        + "'";
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "INVALID_PARAMETER",
                message,
                request.getRequestURI()
        );
    }
    @ExceptionHandler(
            HttpMessageNotReadableException.class
    )
    public ResponseEntity<ApiErrorResponse>
    handleUnreadableRequestBody(
            HttpMessageNotReadableException exception,
            HttpServletRequest request) {
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "MALFORMED_REQUEST",
                "The request body is missing or contains invalid JSON",
                request.getRequestURI()
        );
    }
    @ExceptionHandler(
            DataIntegrityViolationException.class
    )
    public ResponseEntity<ApiErrorResponse>
    handleDataIntegrityViolation(
            DataIntegrityViolationException exception,
            HttpServletRequest request) {
        log.warn(
                "Database constraint violation for request path {}",
                request.getRequestURI(),
                exception
        );
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "DATA_INTEGRITY_VIOLATION",
                "The request conflicts with an existing resource "
                        + "or violates a database constraint",
                request.getRequestURI()
        );
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse>
    handleUnexpectedException(
            Exception exception,
            HttpServletRequest request) {
        log.error(
                "Unexpected error while processing request path {}",
                request.getRequestURI(),
                exception
        );
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_SERVER_ERROR",
                "An unexpected server error occurred",
                request.getRequestURI()
        );
    }
    private ResponseEntity<ApiErrorResponse>
    buildErrorResponse(
            HttpStatus status,
            String errorCode,
            String message,
            String path) {
        ApiErrorResponse response =
                new ApiErrorResponse(
                        LocalDateTime.now(),
                        status.value(),
                        status.getReasonPhrase(),
                        errorCode,
                        message,
                        path
                );
        return ResponseEntity
                .status(status)
                .body(response);
    }
}
