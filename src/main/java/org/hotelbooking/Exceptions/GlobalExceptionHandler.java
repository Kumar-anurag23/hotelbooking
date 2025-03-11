package org.hotelbooking.Exceptions;

import org.hotelbooking.Reponse.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exceptionHandler(Exception ex){
        return ResponseHandler.getResponse(HttpStatus.BAD_REQUEST,"hotel not found",false,null);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> exceptionHandler(NullPointerException ex){
        return ResponseHandler.getResponse(HttpStatus.BAD_REQUEST,"hotel not found",false,null);
    }


}
