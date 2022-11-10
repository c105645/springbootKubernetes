package com.stackroute.user.service;

import com.stackroute.user.util.exception.UserAlreadyExistsException;
import com.stackroute.user.util.exception.UserIdAndPasswordMismatchException;
import com.stackroute.user.util.exception.UserNotFoundException;

import reactor.core.publisher.Mono;

import com.stackroute.user.dao.User;
import com.stackroute.user.dto.LoginRequest;
import com.stackroute.user.dto.UserDto;

public interface UserAuthService {
	
	/*
	 * Should not modify this interface. You have to implement these methods in
	 * corresponding Impl classes
	 */
    boolean saveUser(UserDto user) throws UserAlreadyExistsException;
    
    Mono<String> authenticateAndgetAToken(LoginRequest user) throws UserNotFoundException;

}
