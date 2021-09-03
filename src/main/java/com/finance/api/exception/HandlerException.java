package com.finance.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

    @ControllerAdvice
    public class HandlerException {

        @ExceptionHandler(value ={ ApiRequestException.class } )
        public ResponseEntity<Object> handleRequest (ApiRequestException e ) {
            ApiExceptionHandleId apiExceptionHandleId =  new ApiExceptionHandleId(
                    e.getMessage(),
                    e,
                    HttpStatus.NOT_FOUND
            );
            return new ResponseEntity<>(apiExceptionHandleId , HttpStatus.NOT_FOUND);
        }

    }
