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

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.stackroute.newz.aspect.ToLog;
import com.stackroute.newz.dto.NewsDto;
import com.stackroute.newz.service.NewsServiceImpl;
import com.stackroute.newz.util.exception.NewsAlreadyExistsException;
import com.stackroute.newz.util.exception.NewsNotFoundExeption;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */

@RestController
@RequestMapping(NewsController.NEWS_API_ENDPOINT)
@SecurityRequirement(name = "api-security-scheme")
public class NewsController {
	
	  public static final String NEWS_API_ENDPOINT = "/api/v1";
	  public static final String NEWS_API = "/news";
	  public static final String NEWS_API_CHECK_IF_AUTHENTICATED = "/isAuthenticated";


	/*
	 * Autowiring should be implemented for the NewsService. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword
	 */
	  
		private final NewsServiceImpl service;
		
		public NewsController(NewsServiceImpl service) {
			this.service = service;
		}


	   @ToLog
	   @GetMapping(NEWS_API + NEWS_API_CHECK_IF_AUTHENTICATED)
	   @ResponseStatus(HttpStatus.OK)
	   public String checkIfAuthenticated() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	   }

	/*
	 * Define a handler method which will create a specific news by reading the
	 * Serialized object from request body and save the news details in the
	 * database.This handler method should return any one of the status messages
	 * basis on different situations: 
	 * 1. 201(CREATED) - If the news created successfully. 
	 * 2. 409(CONFLICT) - If the newsId conflicts with any existing user.
	 * 
	 * This handler method should map to the URL "/api/v1/news" using HTTP POST method
	 */
	   @ToLog
	   @PostMapping(NEWS_API)
	   @ResponseStatus(HttpStatus.CREATED)
	   @Operation(summary = "Add a News Item")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "201", description = "New News Item added", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NewsDto.class))}),
	            @ApiResponse(responseCode = "409", description = "News item with the title already exists", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "401", description = "Un-Authorized user", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(hidden = true))})
	    })
	   public NewsDto addNewsItem(@Valid @RequestBody NewsDto news) throws NewsAlreadyExistsException {
		   NewsDto returnedNews =  service.addNews(news);  
		   return returnedNews;
	   }

	/*
	 * Define a handler method which will delete a news from a database.
	 * This handler method should return any one of the status messages basis 
	 * on different situations: 
	 * 1. 200(OK) - If the news deleted successfully from database. 
	 * 2. 404(NOT FOUND) - If the news with specified newsId is not found.
	 *
	 * This handler method should map to the URL "/api/v1/news/{userId}/{newsId}" 
	 * using HTTP Delete method where "userId" should be replaced by a valid userId 
	 * without {} and "newsId" should be replaced by a valid newsId 
	 * without {}.
	 * 
	 */
	   @ToLog
	   @DeleteMapping(NEWS_API + "/{userId}/{newsId}")
	   @ResponseStatus(HttpStatus.OK)
	   @Operation(summary = "Delete a News Item")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "News Item deleted", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NewsDto.class))}),
	            @ApiResponse(responseCode = "404", description = "No News Item exists with the given id", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "401", description = "Un-Authorized user", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(hidden = true))})
	    })
	   public boolean deleteNewsItem(@PathVariable String userId, @PathVariable Long newsId) throws NewsNotFoundExeption {
		return service.deleteNews(userId, newsId);  
	   }

	/*
	 * Define a handler method which will delete all the news of a specific user from 
	 * a database. This handler method should return any one of the status messages 
	 * basis on different situations: 
	 * 1. 200(OK) - If the newsId deleted successfully from database. 
	 * 2. 404(NOT FOUND) - If the note with specified newsId is not found.
	 *
	 * This handler method should map to the URL "/api/v1/news/{userId}" 
	 * using HTTP Delete method where "userId" should be replaced by a valid userId 
	 * without {} and "newsid" should be replaced by a valid newsId 
	 * without {}.
	 * 
	 */
	   
	   @ToLog
	   @DeleteMapping(NEWS_API + "/{userId}")
	   @ResponseStatus(HttpStatus.OK)
	   @Operation(summary = "Delete all news items of a user")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "News Items deleted", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NewsDto.class))}),
	            @ApiResponse(responseCode = "404", description = "No News item exists for the given user", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "401", description = "Un-Authorized user", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(hidden = true))})
	    })
	   public boolean deleteNewsItemOfAUser(@PathVariable String userId) throws NewsNotFoundExeption {
		return service.deleteAllNewsOfAUser(userId);  
	   }
	
	/*
	 * Define a handler method which will update a specific news by reading the
	 * Serialized object from request body and save the updated news details in a
	 * database. 
	 * This handler method should return any one of the status messages
	 * basis on different situations: 
	 * 1. 200(OK) - If the news updated successfully.
	 * 2. 404(NOT FOUND) - If the news with specified newsId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/news/{userId}/{newsId}" using 
	 * HTTP PUT method where "userId" should be replaced by a valid userId 
	 * without {} and "newsid" should be replaced by a valid newsId without {}.
	 * 
	 */
	   @ToLog
	   @PutMapping(NEWS_API + "/{userId}/{newsId}")
	   @ResponseStatus(HttpStatus.OK)
	   @Operation(summary = "Update a news Item")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "News Items deleted", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NewsDto.class))}),
	            @ApiResponse(responseCode = "404", description = "News don't exist with the given ids", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "401", description = "Un-Authorized user", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(hidden = true))})
	    })
	   public NewsDto updateNewsItem(@RequestBody NewsDto news, @PathVariable String userId, @PathVariable Long newsId) throws NewsNotFoundExeption {
		   return service.updateNews(news, newsId, userId);  
	   }
	
	/*
	 * Define a handler method which will get us the specific news by a userId.
	 * This handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the news found successfully. 
	 * 2. 404(NOT FOUND) - If the news with specified newsId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/news/{userId}/{newsId}" 
	 * using HTTP GET method where "userId" should be replaced by a valid userId 
	 * without {} and "newsid" should be replaced by a valid newsId without {}.
	 * 
	 */
	   @ToLog
	   @GetMapping(NEWS_API + "/{userId}/{newsId}")
	   @ResponseStatus(HttpStatus.OK)
	   @Operation(summary = "fetch a news item")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "News Item fetched", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NewsDto.class))}),
	            @ApiResponse(responseCode = "404", description = "News item with the given id don't exist", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "401", description = "Un-Authorized user", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(hidden = true))})
	    })
	   public NewsDto GetNewsItemDetails(@PathVariable String userId, @PathVariable Long newsId) throws NewsNotFoundExeption {
		   return service.getNewsByNewsIdAndUserId(userId, newsId);  
	   }

	/*
	 * Define a handler method which will show details of all news created by specific 
	 * user. This handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the news found successfully. 
	 * 2. 404(NOT FOUND) - If the news with specified newsId is not found.
	 * This handler method should map to the URL "/api/v1/news/{userId}" using HTTP GET method
	 * where "userId" should be replaced by a valid userId without {}.
	 * 
	 */

	   @GetMapping(NEWS_API + "/{userId}")
	   @ResponseStatus(HttpStatus.OK)
	   @Operation(summary = "Fetch all news items of a user")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "News Items deleted", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NewsDto.class))}),
	            @ApiResponse(responseCode = "404", description = "No news items exists for the given user", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "401", description = "Un-Authorized user", content = {@Content(schema = @Schema(hidden = true))}),
	            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(hidden = true))})
	    })
	   public List<NewsDto> GetNewsItemDetailsOfAUser(@PathVariable String userId) throws NewsNotFoundExeption {
		   List<NewsDto> news = service.getAllNewsByUserId(userId);
		   return news;  
	   }

}
