package com.baktybekov.demo.advice;

import com.baktybekov.demo.exception.ReceiverOfPaymentNotFound;
import com.baktybekov.demo.exception.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex
    ) {
        Map<String, Object> response = new HashMap<>();
        response.put("msg", "There is an error");
        response.put("code", HttpStatus.BAD_REQUEST.value());
        response.put("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(e -> {
            String fieldName = ((FieldError) e).getField();
            String errorMessage = e.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        response.put("errors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_XML)
                .body(response);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUserExists(UserAlreadyExistsException ex) {
        return ex.getMessage();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ReceiverOfPaymentNotFound.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleDestNotFound(ReceiverOfPaymentNotFound ex) {
        return ex.getMessage();
    }
}
