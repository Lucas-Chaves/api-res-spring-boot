package com.lucas.demorparkapi.web.exception;


import com.lucas.demorparkapi.exception.EntityNotFoundException;
import com.lucas.demorparkapi.exception.PasswordInvalidException;
import com.lucas.demorparkapi.exception.USerNameUniqueViolationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> MethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request, BindingResult result) {
        log.error("Api Error - ", exception);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).body(new ErrorMessage(request,  HttpStatus.UNPROCESSABLE_ENTITY, "Campo(s) invalido(s)", result));
    }

    @ExceptionHandler(USerNameUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> UniqueViolationException(RuntimeException exception, HttpServletRequest request) {
        log.error("Api Error - ", exception);
        return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON).body(new ErrorMessage(request,  HttpStatus.CONFLICT, exception.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> EntityNotFoundException(RuntimeException exception, HttpServletRequest request) {
        log.error("Api Error - ", exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new ErrorMessage(request,  HttpStatus.NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ErrorMessage> PasswordInvalidException(RuntimeException exception, HttpServletRequest request) {
        log.error("Api Error - ", exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(new ErrorMessage(request,  HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

}
