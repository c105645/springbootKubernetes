package com.stackroute.userprofile.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;


import com.stackroute.userprofile.aspect.ToLog;
import com.stackroute.userprofile.dto.UserProfileDto;
import com.stackroute.userprofile.service.UserProfileServiceImpl;
import com.stackroute.userprofile.util.exception.UserProfileAlreadyExistsException;
import com.stackroute.userprofile.util.exception.UserProfileNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


/*
 * As in this assignment, we are working on creating RESTful web service, hence annotate
 * the class with @RestController annotation. A class annotated with the @Controller annotation
 * has handler methods which return a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */

@RestController
@RequestMapping(UserProfileController.USER_API_ENDPOINT)
@SecurityRequirement(name = "api-security-scheme")
public class UserProfileController {
	
	 public static final String USER_API_ENDPOINT = "/api/v1";
	  public static final String USER_API = "/userprofile";

	/*
	 * Autowiring should be implemented for the UserService. (Use Constructor-based
	 * autowiring) Please note that we should not create an object using the new
	 * keyword
	 */
	  
	  private final UserProfileServiceImpl service;
		
	

    public UserProfileController(UserProfileServiceImpl service) {
    	this.service = service;
    }

	/*
	 * Define a handler method which will create a specific userprofile by reading the
	 * Serialized object from request body and save the user details in the
	 * database. This handler method should return any one of the status messages
	 * basis on different situations:
	 * 1. 201(CREATED) - If the userprofile created successfully. 
	 * 2. 409(CONFLICT) - If the userId conflicts with any existing user, return the 
	 * UserProfileAlreadyExistsException along with the status
	 * 
	 * This handler method should map to the URL "/api/v1/user" using HTTP POST method
	 */
    
    
	 @ToLog
	 @PostMapping(USER_API)
	 @ResponseStatus(HttpStatus.CREATED)
	   @Operation(summary = "Add a new profile")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "201", description = "profile added", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserProfileDto.class))}),
	            @ApiResponse(responseCode = "409", description = "User profile already exists", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "401", description = "Un-Authorized user", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(hidden = true))})
	    })
	 public UserProfileDto registerUser(@Valid @RequestBody UserProfileDto user) throws UserProfileAlreadyExistsException {
	    return service.registerUser(user);     	
   }
   

	/*
	 * Define a handler method which will update a specific userprofile by reading the
	 * Serialized object from request body and save the updated user details in a
	 * database. This handler method should return any one of the status messages
	 * basis on different situations: 
	 * 1. 200(OK) - If the userprofile is updated successfully.
	 * 2. 404(NOT FOUND) - If the userprofile with specified userId is not found,return the 
	 * UserProfileNotFoundException along with the status
	 * 
	 * This handler method should map to the URL "/api/v1/userprofile/{userid}" using HTTP PUT method.
	 */
	 @ToLog
	 @ResponseStatus(HttpStatus.OK)
	 @PutMapping(USER_API + "/{userId}")
	 @Operation(summary = "Update an existing profile")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "profile Updated", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserProfileDto.class))}),
	            @ApiResponse(responseCode = "404", description = "User profile dont exists", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "401", description = "Un-Authorized user", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(hidden = true))})
	    })	
	 public UserProfileDto updateUser(@Valid @RequestBody UserProfileDto user, @PathVariable String userId) throws UserProfileNotFoundException {
	    return service.updateUser(userId, user);     	
    }

	/*
	 * Define a handler method which will delete an userprofile from a database.
	 * This handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the userprofile is deleted successfully from database. 
	 * 2. 404(NOT FOUND) - If the user with specified userId is not found, return 
	 * the UserProfileNotFoundException along with the status.
	 *
	 * This handler method should map to the URL "/api/v1/userprofile/{userId}" using 
	 * HTTP Delete method where "userId" should be replaced by a valid userId
	 * 
	 */
	 
		
	 @DeleteMapping(USER_API + "/{userId}")
	 @ResponseStatus(HttpStatus.OK)
	 @Operation(summary = "Delete a profile")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "profile deleted", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserProfileDto.class))}),
	            @ApiResponse(responseCode = "404", description = "User profile does not exist", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "401", description = "Un-Authorized user", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(hidden = true))})
	    })	
	 public void updateUser(@PathVariable String userId) throws UserProfileNotFoundException {
	    service.deleteUser(userId);     	
    }

	/*
	 * Define a handler method which will show details of a specific user. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the userprofile found successfully. 
	 * 2. 404(NOT FOUND) - If the userprofile with specified userId is not found, return 
	 * UserProfileNotFoundException message along with the status.
	 * This handler method should map to the URL "/api/v1/userprofile/{userId}" using 
	 * HTTP GET method where "id" should be replaced by a valid userId without {}.
	 */

	 @GetMapping(USER_API + "/{userId}")
	 @ResponseStatus(HttpStatus.OK)
	 @Operation(summary = "Delete a profile")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "profile fetched", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserProfileDto.class))}),
	            @ApiResponse(responseCode = "404", description = "User profile does not exist", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "401", description = "Un-Authorized user", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(hidden = true))})
	    })	
	 public UserProfileDto getUser(@PathVariable String userId) throws UserProfileNotFoundException {
	    return service.getUserById(userId);     	
    }

}
