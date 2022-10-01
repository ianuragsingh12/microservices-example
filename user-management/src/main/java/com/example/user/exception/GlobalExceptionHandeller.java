/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.user.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author ianur
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandeller extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> defaultHandler(Exception e) {
        logError(new Throwable().getStackTrace()[0].getMethodName(), e);

        return buildResponseEntity(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e));
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Object> alreadyExistsExceptionHandler(AlreadyExistsException e) {
        logError(new Throwable().getStackTrace()[0].getMethodName(), e);

        ErrorResponse err = new ErrorResponse(BAD_REQUEST);
        err.setMessage(e.getLocalizedMessage());
        return buildResponseEntity(err);
    }

    private void logError(String mName, Exception e) {
        log.error("--- " + this.getClass().getSimpleName() + "::" + mName + " ---");
        log.error(e.toString() + "\n");
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorResponse er) {
        return new ResponseEntity<>(er, er.getStatus());
    }
}
