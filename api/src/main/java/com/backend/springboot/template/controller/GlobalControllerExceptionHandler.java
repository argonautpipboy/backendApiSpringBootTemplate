package com.backend.springboot.template.controller;

import com.backend.springboot.template.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

	private static final Logger	log	= LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public void handleIllegalArgumentException(IllegalArgumentException ex) {
		logError(HttpStatus.BAD_REQUEST, ex);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(EntityNotFoundException.class)
	public void handleEntityNotFoundException(EntityNotFoundException ex) {
		logError(HttpStatus.NOT_FOUND, ex);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public void handleServerException(Exception ex) {
		logError(HttpStatus.INTERNAL_SERVER_ERROR, ex);
	}

	private void logError(HttpStatus httpStatus, Throwable ex) {
		if (log.isDebugEnabled()) {
			log.debug("HTTP Status " + httpStatus.value() + " (" + httpStatus.getReasonPhrase() + ")", ex);
		} else {
			log.error("HTTP Status {} ({}) : {}", httpStatus.value(), httpStatus.getReasonPhrase(),
					ex.getMessage());
		}
	}

}
