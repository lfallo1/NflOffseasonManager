package com.lancefallon.usermgmt.config.exception.service;

import java.io.IOException;

import javax.security.sasl.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lancefallon.usermgmt.config.exception.model.CustomErrorMessage;
import com.lancefallon.usermgmt.config.exception.model.DatabaseException;
import com.lancefallon.usermgmt.config.exception.model.InvalidCredentialsException;

/**
 * global error handler / response generator
 * @author lancefallon
 *
 */
@ControllerAdvice
public class ErrorResponseHandler {
	@ExceptionHandler({AuthenticationException.class})
    ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) throws IOException {
        return new ResponseEntity<String>("Authentication exception: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(InvalidCredentialsException.class)
    ResponseEntity<CustomErrorMessage> handleInvalidCredentialsException(InvalidCredentialsException ex) throws IOException {
        return new ResponseEntity<CustomErrorMessage>(ex.getError(), HttpStatus.FORBIDDEN);
    }
	
	@ExceptionHandler(DatabaseException.class)
    ResponseEntity<CustomErrorMessage> handleDatabaseException(DatabaseException ex) throws IOException {
        return new ResponseEntity<CustomErrorMessage>(ex.getError(), HttpStatus.SERVICE_UNAVAILABLE);
    }
}