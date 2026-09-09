package com.plantthyme.plant_thyme_api.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// This handles bad *API* URLs (/api/plnats) — returns JSON to whatever CODE called it.
// This is NOT my frontend's 404 page. That's React Router handling bad *page* URLs for humans.
// Two separate systems: React Router = pages for people; this = JSON for API calls.

// @RestControllerAdvice = ONE global place to handle errors from ALL controllers.
// Instead of try/catch in every controller method, exceptions bubble up to here.
// extends ResponseEntityExceptionHandler = inherit Spring's built-in handlers for common web exceptions.
// ...then @Override just the ones I want to give my own custom JSON response.

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // A URL that doesn't map to any route, e.g. a typo like /api/plnats -> 404
    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex,
                                                                    HttpHeaders headers,
                                                                    HttpStatusCode status,
                                                                    WebRequest request) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("statusCode", HttpStatus.NOT_FOUND.value());
        errorResponse.put("message", "No data found for " + ex.getResourcePath());
        errorResponse.put("timestamp", new Date());
        errorResponse.put("errorCode", "NOT_FOUND");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // A field failed a validation rule (@NotBlank, @Size, etc.) -> 400
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, Object> errorResponse = new HashMap<>();
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(field -> field.getDefaultMessage())
                .collect(Collectors.toList());
        errorResponse.put("statusCode", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("message", errors);
        errorResponse.put("timestamp", new Date());
        errorResponse.put("errorCode", "VALIDATION_FAILED");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // The request body couldn't be read at all, e.g. "" sent for a date field -> 400
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("statusCode", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("message", "Request body is malformed or contains an invalid value.");
        errorResponse.put("timestamp", new Date());
        errorResponse.put("errorCode", "MALFORMED_REQUEST");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}