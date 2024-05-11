package com.example.salesManagement.exception.handler;

import com.example.salesManagement.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleIntegrityViolationException(DataIntegrityViolationException exception) {
        String errorMessage = exception.getMessage();
        int detailStartIndex = errorMessage.indexOf("Detail:");
        int detailEndIndex = errorMessage.indexOf("]", detailStartIndex);
        String detailMessage = errorMessage.substring(detailStartIndex, detailEndIndex + 1);

        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detailMessage);
    }

    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationException(ValidationException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNotFoundException(NotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler({InsufficientBalanceException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInsufficientBalanceException(InsufficientBalanceException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler({InsufficientInventoryException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInsufficientInventoryBalanceException(InsufficientInventoryException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleOtherExceptions(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong. Please contact admin.");
    }

}
