package org.hotelbooking.Exceptions;

import org.hotelbooking.Reponse.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLDataException;
import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException ex) {
        return ResponseHandler.getResponse(HttpStatus.NOT_FOUND, "Hotel not found", false, null);
    }

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<Object> handleArithmeticException(ArithmeticException ex) {
        return ResponseHandler.getResponse(HttpStatus.BAD_REQUEST, "Divide by zero error", false, null);
    }

    @ExceptionHandler(SQLDataException.class)
    public ResponseEntity<Object> handleSQLDataException(SQLDataException ex) {
        return ResponseHandler.getResponse(HttpStatus.BAD_REQUEST, "SQL data exception occurred", false, null);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Object> handleSQLException(SQLException ex) {
        return ResponseHandler.getResponse(HttpStatus.BAD_REQUEST, "SQL exception occurred", false, null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return ResponseHandler.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", false, null);
    }
}
