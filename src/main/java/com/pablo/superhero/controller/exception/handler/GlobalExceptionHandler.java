package com.pablo.superhero.controller.exception.handler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice("com.pablo.superhero.controller")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ ResponseStatusException.class })
	protected ResponseEntity<?> handleResponseStatusException(ResponseStatusException exception,
			 WebRequest request) {
		Map<String, Object> response = new HashMap<>();
		response.put("status",  exception.getRawStatusCode());
		response.put("message", exception.getReason());
		response.put("timestamp", LocalDateTime.now());
		
		return ResponseEntity.status(exception.getRawStatusCode()).body(response);
	}

}
