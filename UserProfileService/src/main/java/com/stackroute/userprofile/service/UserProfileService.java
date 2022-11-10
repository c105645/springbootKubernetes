package com.stackroute.userprofile.service;



import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.stackroute.userprofile.dto.UserProfileDto;
import com.stackroute.userprofile.util.exception.UserProfileAlreadyExistsException;
import com.stackroute.userprofile.util.exception.UserProfileNotFoundException;

@Service
public interface UserProfileService {

	/*
	 * Should not modify this interface. You have to implement these methods in
	 * corresponding Impl classes
	 */

	UserProfileDto registerUser(UserProfileDto user) throws UserProfileAlreadyExistsException;

  UserProfileDto updateUser(String userId,@Valid UserProfileDto user) throws UserProfileNotFoundException;

  void deleteUser(String userId) throws UserProfileNotFoundException;

  UserProfileDto getUserById(String userId) throws UserProfileNotFoundException;


	
	
}
