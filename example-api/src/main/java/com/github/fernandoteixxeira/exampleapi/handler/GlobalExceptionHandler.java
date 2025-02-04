package com.github.fernandoteixxeira.exampleapi.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Exception controller.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<ApiError> constraintViolationException(ConstraintViolationException exception) {
        val errors = exception.getConstraintViolations().stream()
                .map(GlobalExceptionHandler::createError)
                .toList();
        val apiError = new ApiError("some fields contain errors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException exception,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        val error = createError(exception);
        val apiError = new ApiError("some fields contain errors", List.of(error));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ){
        val attributeErrors = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(GlobalExceptionHandler::createError)
                .toList();

        val validationErrors = exception.getBindingResult().getGlobalErrors()
                .stream()
                .map(GlobalExceptionHandler::createError)
                .toList();

        val errors = Stream.concat(attributeErrors.stream(), validationErrors.stream()).collect(
                Collectors.toList());
        val apiError = new ApiError("some fields contain errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException exception,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        val apiError = new ApiError(exception.getMostSpecificCause().getMessage(), null);
        return ResponseEntity.status(status).body(apiError);
    }

    private static Error createError(ObjectError error) {
        val message = error.getDefaultMessage();
        return new Error("validation", null, null, message);
    }

    private static Error createError(FieldError error) {
        val field = error.getField();
        val value = String.valueOf(error.getRejectedValue());
        val message = error.getDefaultMessage();
        return new Error("attribute", field, value, message);
    }

    private static Error createError(ConstraintViolation<?> error) {
        val field = error.getPropertyPath().toString();
        val value = error.getInvalidValue().toString();
        val message = error.getMessage();
        return new Error("path", field, value, message);
    }

    private static Error createError(MissingServletRequestParameterException exception) {
        val field = exception.getParameterName();
        val errorMessage = MessageFormat.format("{0} parameter is missing", field);
        return new Error("path", field, null, errorMessage);
    }
}
