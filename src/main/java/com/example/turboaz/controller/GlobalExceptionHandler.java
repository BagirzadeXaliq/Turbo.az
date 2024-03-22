package com.example.turboaz.controller;

import com.example.turboaz.exception.*;
import com.example.turboaz.model.ExceptionDTO;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDTO handleNotFoundException(NotFoundException ex){
        log.error("NotFoundException ->  {}", ex.getMessage());
        return new ExceptionDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionDTO handleConflictException(ConflictException ex){
        log.error("ConflictException ->  {}", ex.getMessage());
        return new ExceptionDTO(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handleBadRequestException(BadRequestException ex){
        log.error("BadRequestException ->  {}", ex.getMessage());
        return new ExceptionDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handleMaxValueExceededException(ConstraintViolationException ex) {
        log.error("ConstraintViolationException ->  {}", ex.getMessage());
        return new ExceptionDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDTO handleNotFound(EntityNotFoundException entityNotFoundException){
        log.error("ActionLog.error not found: {} ", entityNotFoundException.getMessage());
        return new ExceptionDTO(HttpStatus.NOT_FOUND.value(), entityNotFoundException.getMessage());
    }

    @ExceptionHandler(EntityExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionDTO handleNotFound(EntityExistException entityExistException){
        log.error("ActionLog.error not found: {} ", entityExistException.getMessage());
        return new ExceptionDTO(HttpStatus.CONFLICT.value(), entityExistException.getMessage());
    }

    @ExceptionHandler(InvalidUserTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handleNotFound(InvalidUserTypeException invalidUserTypeException){
        log.error("ActionLog.error not found: {} ", invalidUserTypeException.getMessage());
        return new ExceptionDTO(HttpStatus.BAD_REQUEST.value(), invalidUserTypeException.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.error("HttpMessageNotReadableException ->  {}", ex.getMessage());
        return new ExceptionDTO(HttpStatus.BAD_REQUEST.value(), "Invalid JSON data");
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST )
    public ExceptionDTO handler(ValidationException exception) {
        log.error("ActionLog.error validation exception message: {}", exception.getMessage());
        return new ExceptionDTO(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionDTO handler(AccessDeniedException exception){
        log.error("ActionLog.error access denied exception message: {}", exception.getMessage());
        return new ExceptionDTO(HttpStatus.FORBIDDEN.value(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionDTO handleGlobal(Exception e) {
        return new ExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionDTO handleAuthenticationException(AuthenticationException e){
        log.error("AuthenticationException ->  {}", e.getMessage());
        return new ExceptionDTO(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handleBadCredentialsException(BadCredentialsException e){
        log.error("BadCredentialsException -> {}", e.getMessage());
        return new ExceptionDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

}