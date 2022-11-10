package com.stackroute.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.stackroute.user.dto.LoginRequest;
import com.stackroute.user.dto.LoginResponse;
import com.stackroute.user.dto.LoginResponse.Status;
import com.stackroute.user.dto.RegisterRequest;
import com.stackroute.user.service.UserAuthServiceImpl;
import com.stackroute.user.util.exception.UserAlreadyExistsException;

import reactor.core.publisher.Mono;


/*
 * As in this assignment, we are working on creating RESTful web service, hence annotate
 * the class with @RestController annotation. A class annotated with the @Controller annotation
 * has handler methods which return a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
@RequestMapping(UserAuthController.AUTH_API_ENDPOINT)
public class UserAuthController {
	
	 public static final String AUTH_API_ENDPOINT = "/api/v1/auth";
	  public static final String REGISTER_API = "/register";
	  public static final String LOGIN_API = "/login";


	  
	 
    /*
	 * Autowiring should be implemented for the UserAuthService. (Use Constructor-based
	 * autowiring) Please note that we should not create an object using the new
	 * keyword
	 */
	
	private final UserAuthServiceImpl userAuthService;
	


    public UserAuthController(UserAuthServiceImpl userAuthService) {
    	this.userAuthService = userAuthService;
 
	}

    /*
	 * Define a handler method which will create a specific user by reading the
	 * Serialized object from request body and save the user details in the
	 * database. This handler method should return any one of the status messages
	 * basis on different situations:
	 * 1. 201(CREATED) - If the user created successfully. 
	 * 2. 409(CONFLICT) - If the userId conflicts with any existing user, 
	 * UserAlreadyExistsException is caught.
	 * 
	 * This handler method should map to the URL "/api/v1/auth/register" using HTTP POST method
	 */
    
    @PostMapping(REGISTER_API)
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@Valid @RequestBody RegisterRequest user) throws UserAlreadyExistsException {
    	userAuthService.saveUser(user.getUser());
    }

	/* 
	 * Define a handler method which will authenticate a user by reading the Serialized user
	 * object from request body containing the username and password. The username and password should be validated 
	 * before proceeding ahead with JWT token generation. The user credentials will be validated against the database entries. 
	 * The error should be return if validation is not successful. If credentials are validated successfully, then JWT
	 * token will be generated. The token should be returned back to the caller along with the API response.
	 * This handler method should return any one of the status messages basis on different
	 * situations:
	 * 1. 200(OK) - If login is successful
	 * 2. 401(UNAUTHORIZED) - If login is not successful
	 * 
	 * This handler method should map to the URL "/api/v1/auth/login" using HTTP POST method
	*/
    @PostMapping(LOGIN_API)
    public Mono<ResponseEntity<LoginResponse>> login(@RequestBody LoginRequest user) throws Exception {
        
        System.out.println("Auth service");
    	
    	Mono<String> token = userAuthService.authenticateAndgetAToken(user);
    	
    	return token.map(tk -> {
    	    HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Authorization", tk);
            
            LoginResponse response = new LoginResponse();
            response.setStatus(Status.Successful);
            response.setUsername(user.getUsername());
            
            return ResponseEntity.ok()
              .headers(responseHeaders)
              .body(response);
    	});
        
       
    }


}
