package com.vote4tech.portalciudadania.exceptions;

import java.time.LocalDateTime;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFound(
      ResourceNotFoundException ex,
      HttpServletRequest request) {

    ErrorResponse error = new ErrorResponse(
      LocalDateTime.now(),
      HttpStatus.NOT_FOUND.value(),
      "Not Found",
      ex.getMessage(),
      request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleBadRequest(
      BadRequestException ex,
      HttpServletRequest request) {

    ErrorResponse error = new ErrorResponse(
      LocalDateTime.now(),
      HttpStatus.BAD_REQUEST.value(),
      "Bad Request",
      ex.getMessage(),
      request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponse> handleBusiness(
      BusinessException ex,
      HttpServletRequest request) {

    ErrorResponse error = new ErrorResponse(
      LocalDateTime.now(),
      HttpStatus.CONFLICT.value(),
      "Conflict",
      ex.getMessage(),
      request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  // Error típico: unique, foreign key, not null, etc.
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleDataIntegrity(
      DataIntegrityViolationException ex,
      HttpServletRequest request) {

    ErrorResponse error = new ErrorResponse(
      LocalDateTime.now(),
      HttpStatus.BAD_REQUEST.value(),
      "Database Constraint Error",
      "Error de integridad en la base de datos",
      request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  // Error general de acceso a base de datos
  @ExceptionHandler(DataAccessException.class)
  public ResponseEntity<ErrorResponse> handleDatabase(
      DataAccessException ex,
      HttpServletRequest request) {

    ErrorResponse error = new ErrorResponse(
      LocalDateTime.now(),
      HttpStatus.INTERNAL_SERVER_ERROR.value(),
      "Database Error",
      "Ocurrió un error interno al acceder a la base de datos",
      request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

  // Cualquier otra cosa no controlada
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneral(
      Exception ex,
      HttpServletRequest request) {

    ErrorResponse error = new ErrorResponse(
      LocalDateTime.now(),
      HttpStatus.INTERNAL_SERVER_ERROR.value(),
      "Internal Server Error",
      "Ocurrió un error interno en el servidor",
      request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}