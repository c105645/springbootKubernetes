package com.stackroute.newz.util.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ControllerAdvice
public class NewsControllerAdvice  {
	
	   @ExceptionHandler(NewsSourceNotCreatedException.class)
	    @ResponseStatus(HttpStatus.NOT_FOUND)
	    public ResponseEntity<Object> newsNotFoundHandler(NewsSourceNotCreatedException ex, WebRequest req) {
		 Map<String, Object> body = new LinkedHashMap<>();
	        body.put("timestamp", LocalDateTime.now());
	        body.put("message", ex.getMessage());

	        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(NewsSourceNotFoundException.class)
	    @ResponseStatus(HttpStatus.NOT_FOUND)
	    public ResponseEntity<Object>  newsAlreadyExistsHandler(NewsSourceNotFoundException ex, WebRequest req) {
	    	 Map<String, Object> body = new LinkedHashMap<>();
		        body.put("timestamp", LocalDateTime.now());
		        body.put("message", ex.getMessage());

		        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	    }
	    
	    @ExceptionHandler(NewsSourceAlreadyExists.class)
	    @ResponseStatus(HttpStatus.CONFLICT)
	    public ResponseEntity<Object>  newsAlreadyExistsHandler(NewsSourceAlreadyExists ex, WebRequest req) {
	    	 Map<String, Object> body = new LinkedHashMap<>();
		        body.put("timestamp", LocalDateTime.now());
		        body.put("message", ex.getMessage());

		        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
	    }
	    	    
	    @ExceptionHandler(MethodArgumentNotValidException.class) 
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest req) {
	    	Map<String, Object> errors = new LinkedHashMap<String, Object>();
	        errors.put("timestamp", LocalDateTime.now());
	        ex.getBindingResult().getAllErrors().forEach(error -> {
	            String fieldName = ((FieldError) error).getField();
	            String errorMessage = error.getDefaultMessage();
				errors.put(fieldName, errorMessage);
	        });
	        
	        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);	 

	    }


}
