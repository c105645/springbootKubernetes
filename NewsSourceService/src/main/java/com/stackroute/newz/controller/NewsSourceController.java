package com.stackroute.newz.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.newz.aspect.ToLog;
import com.stackroute.newz.dto.NewsSourceDto;
import com.stackroute.newz.service.NewsSourceService;
import com.stackroute.newz.service.NewsSourceServiceImpl;
import com.stackroute.newz.util.exception.NewsSourceAlreadyExists;
import com.stackroute.newz.util.exception.NewsSourceNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;


/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
@RequestMapping(NewsSourceController.NEWS_API_ENDPOINT)
@SecurityRequirement(name = "api-security-scheme")
public class NewsSourceController {
	
	 public static final String NEWS_API_ENDPOINT = "/api/v1";
	  public static final String NEWS_API = "/newssource";

   private final NewsSourceServiceImpl service;
	
	public NewsSourceController(NewsSourceServiceImpl service) {
		this.service = service;
	
	}
	

	/*
	 * Define a handler method which will create a specific newssource by reading the
	 * Serialized object from request body and save the newssource details in the
	 * database.This handler method should return any one of the status messages
	 * basis on different situations: 
	 * 1. 201(CREATED) - If the newssource created successfully. 
	 * 2. 409(CONFLICT) - If the newssourceId conflicts with any existing user.
	 * 
	 * This handler method should map to the URL "/api/v1/newssource" using HTTP POST method
	 */
	
	   @ToLog
	   @PostMapping(NEWS_API)
	   @ResponseStatus(HttpStatus.CREATED)
	   @Operation(summary = "Add a News Item")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "201", description = "New News Item added", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NewsSourceDto.class))}),
	            @ApiResponse(responseCode = "409", description = "News item with the title already exists", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "401", description = "Un-Authorized user", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(hidden = true))})
	    })
	   public NewsSourceDto addNewsItem(@Valid @RequestBody NewsSourceDto newssource) throws NewsSourceAlreadyExists {
		   NewsSourceDto returnedNews =  service.addNewsSource(newssource);  
		   return returnedNews;
	   }


	/*
	 * Define a handler method which will delete a newssource from a database.
	 * This handler method should return any one of the status messages basis 
	 * on different situations: 
	 * 1. 200(OK) - If the newssource deleted successfully from database. 
	 * 2. 404(NOT FOUND) - If the newssource with specified newsId is not found.
	 *
	 * This handler method should map to the URL "/api/v1/newssource/{newssourceId}" 
	 * using HTTP Delete method where "userId" should be replaced by a valid userId 
	 * without {} and "newssourceId" should be replaced by a valid newsId 
	 * without {}.
	 * 
	 */
	   
	   @ToLog
	   @DeleteMapping(NEWS_API + "/{newssourceId}")
	   @ResponseStatus(HttpStatus.OK)
	   @Operation(summary = "Delete a Newssource Item")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "Newssource Item deleted", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NewsSourceDto.class))}),
	            @ApiResponse(responseCode = "404", description = "No Newssource Item exists with the given id", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "401", description = "Un-Authorized user", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(hidden = true))})
	    })
	   public void deleteNewsItem(@PathVariable Long newssourceId) throws NewsSourceNotFoundException {
		service.deleteNewsSource(newssourceId);  
	   }
	
	/*
	 * Define a handler method which will update a specific newssource by reading the
	 * Serialized object from request body and save the updated newssource details in a
	 * database. This handler method should return any one of the status messages
	 * basis on different situations: 
	 * 1. 200(OK) - If the newssource updated successfully.
	 * 2. 404(NOT FOUND) - If the newssource with specified newssourceId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/newssource/{newssourceId}" using 
	 * HTTP PUT method where "newssourceId" should be replaced by a valid newssourceId
	 * without {}.
	 * 
	 */
	   
	   @ToLog
	   @PutMapping(NEWS_API + "/{newssourceId}")
	   @ResponseStatus(HttpStatus.OK)
	   @Operation(summary = "Update a news Item")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "Newssource Items deleted", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NewsSourceDto.class))}),
	            @ApiResponse(responseCode = "404", description = "Newssource don't exist with the given ids", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "401", description = "Un-Authorized user", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(hidden = true))})
	    })
	   public NewsSourceDto updateNewsItem(@RequestBody NewsSourceDto news, @PathVariable Long newsId) throws NewsSourceNotFoundException {
		   return service.updateNewsSource(news, newsId);  
	   }
	
	
	/*
	 * Define a handler method which will get us the specific newssource by a userId.
	 * This handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the newssource found successfully. 
	 * 2. 404(NOT FOUND) - If the newssource with specified newsId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/newssource/{userId}/{newssourceId}" 
	 * using HTTP GET method where "userId" should be replaced by a valid userId 
	 * without {} and "newssourceId" should be replaced by a valid newsId without {}.
	 * 
	 */
	   
	   @ToLog
	   @GetMapping(NEWS_API + "/{userId}/{newssourceId}")
	   @ResponseStatus(HttpStatus.OK)
	   @Operation(summary = "fetch a newssource item")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "Newssource fetched", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NewsSourceDto.class))}),
	            @ApiResponse(responseCode = "404", description = "Newssource with the given id don't exist", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "401", description = "Un-Authorized user", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(hidden = true))})
	    })
	   public NewsSourceDto GetNewsItemDetails(@PathVariable String userId, @PathVariable Long newssourceId) throws NewsSourceNotFoundException {
		   return service.getNewsSourceById(userId, newssourceId);  
	   }
	
	/*
	 * Define a handler method which will show details of all newssource created by specific 
	 * user. This handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the newssource found successfully. 
	 * 2. 404(NOT FOUND) - If the newssource with specified newsId is not found.
	 * This handler method should map to the URL "/api/v1/newssource/{userId}" using HTTP GET method
	 * where "userId" should be replaced by a valid userId without {}.
	 * 
	 */
	   
	   @ToLog
	   @GetMapping(NEWS_API + "/{userId}")
	   @ResponseStatus(HttpStatus.OK)
	   @Operation(summary = "fetch a newssource item")
	   @ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "Newssource fetched", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NewsSourceDto.class))}),
	            @ApiResponse(responseCode = "404", description = "Newssource with the given id don't exist", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "401", description = "Un-Authorized user", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(hidden = true))})
	    })
	   public List<NewsSourceDto> GetNewsItemDetailsOfAUser(@PathVariable String userId) throws NewsSourceNotFoundException {
		   return service.getAllNewsSourceByUserId(userId);  
	   }

    
}
