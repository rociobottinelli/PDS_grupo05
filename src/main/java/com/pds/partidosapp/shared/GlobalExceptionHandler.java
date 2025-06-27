package com.pds.partidosapp.shared;

import com.pds.partidosapp.shared.exceptions.DuplicateException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import com.pds.partidosapp.shared.exceptions.NotFoundException;

import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private Map<String, Object> buildResponse(HttpStatus status, String message) {
    return Map.of(
        "code", status.value(),
        "error", status.getReasonPhrase(),
        "message", message,
        "timestamp", LocalDateTime.now().toString());
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleNotFound(NotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(buildResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
    Map<String, String> fieldErrors = new HashMap<>();
    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
        "code", HttpStatus.BAD_REQUEST.value(),
        "error", "Validation Failed",
        "fields", fieldErrors,
        "timestamp", LocalDateTime.now().toString()));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage()));
  }

  @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
  public ResponseEntity<Map<String, Object>> handleJsonParseError(
      org.springframework.http.converter.HttpMessageNotReadableException ex) {
    String message = "Error en el formato del JSON. Verifica que los tipos de datos sean correctos.";
    if (ex.getMessage().contains("deporteId")) {
      message = "El campo deporteId debe ser un número válido, no un objeto.";
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(buildResponse(HttpStatus.BAD_REQUEST, message));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage()));
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<Map<String, Object>> handleIllegalState(IllegalStateException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(buildResponse(HttpStatus.CONFLICT, ex.getMessage()));
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(buildResponse(HttpStatus.FORBIDDEN, "Acceso denegado"));
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<Map<String, Object>> handleUnauthorized(BadCredentialsException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(buildResponse(HttpStatus.UNAUTHORIZED, "Credenciales inválidas"));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
    ex.printStackTrace(); // opcional: loguear el error
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno inesperado"));
  }

  @ExceptionHandler(DuplicateException.class)
  public ResponseEntity<Map<String, Object>> handleDeporteDuplicado(DuplicateException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage()));
  }
}