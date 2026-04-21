package org.komlev.wallet.exception.handler;

import jakarta.persistence.EntityNotFoundException;
import org.komlev.wallet.dto.ErrorResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalHandler.class);
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> GlobalErrorHandling(Exception e){
        log.error("Internal Server Exception handling: " + e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDto(
                        e.getMessage(),
                        "Internal Server Error: ",
                        LocalDateTime.now()
                ));
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> EntityNotFoundHandling(EntityNotFoundException e){
        log.error("Entity not found handling: " + e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDto(
                        e.getMessage(),
                        "Entity not found",
                        LocalDateTime.now()
                ));
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> HttpMessageNotReadableExceptionHandling(HttpMessageNotReadableException e){
        log.error("Http Message not readable Exception handling: " + e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(
                        e.getMessage(),
                        "Incorrect request format",
                        LocalDateTime.now()
                ));
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> IllegalArgumentExceptionHandling(IllegalArgumentException e){
        log.error("Illegal Argument Exception Handling: " + e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(
                        e.getMessage(),
                        "Incorrect request format",
                        LocalDateTime.now()
                ));
    }
    //
}
