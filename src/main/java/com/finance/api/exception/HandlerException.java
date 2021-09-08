package com.finance.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

    @ControllerAdvice
    public class HandlerException {

        @ExceptionHandler(value ={ ApiRequestExceptionId.class } )
        public ResponseEntity<Object> handleRequest (ApiRequestExceptionId e ) {
            ApiExceptionHandle apiExceptionHandle =  new ApiExceptionHandle(
                    e.getMessage(),
                    e,
                    HttpStatus.NOT_FOUND
            );
            return new ResponseEntity<>(apiExceptionHandle, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(value ={ authRequestException.class } )
        public ResponseEntity<Object> registerException (authRequestException e ) {
            ApiExceptionHandle apiExceptionHandle =  new ApiExceptionHandle(
                    e.getMessage(),
                    e,
                    HttpStatus.BAD_REQUEST
            );
            return new ResponseEntity<>(apiExceptionHandle, HttpStatus.BAD_REQUEST);
        }

    }
