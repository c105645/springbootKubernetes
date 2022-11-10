package com.stackroute.userprofile.service;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.userprofile.dto.UserProfileDto;
import com.stackroute.userprofile.model.UserProfile;
import com.stackroute.userprofile.repository.UserProfileRepository;
import com.stackroute.userprofile.util.exception.UserProfileAlreadyExistsException;
import com.stackroute.userprofile.util.exception.UserProfileNotFoundException;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class UserProfileServiceImpl implements UserProfileService {

	/*
	 * Autowiring should be implemented for the UserProfileRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	
	private final UserProfileRepository repository;
	private final ModelMapper modelMapper;
	
	
	
	@Autowired
	public UserProfileServiceImpl(UserProfileRepository repository, 
			ModelMapper modelMapper) {
		this.repository = repository;
		this.modelMapper = modelMapper;
	}

	/*
	 * This method should be used to save a new userprofile.Call the corresponding method
	 * of Respository interface.
	 */

    public UserProfileDto registerUser(UserProfileDto user) throws UserProfileAlreadyExistsException {
	       repository.findUserProfileByEmail(user.getEmail())
				.orElseThrow(() -> new UserProfileAlreadyExistsException("User with this email id alredy exists"));

				UserProfile userProfileEntity = modelMapper.map(user, UserProfile.class);
				UserProfile savedEntity =  repository.save(userProfileEntity);
				return modelMapper.map(savedEntity, UserProfileDto.class);
    }

	/*
	 * This method should be used to update a existing userprofile.Call the corresponding
	 * method of Respository interface.
	 */

    @Override
    public UserProfileDto updateUser(String userId, UserProfileDto user) throws UserProfileNotFoundException {
    	repository.findById(userId).orElseThrow(() -> new UserProfileNotFoundException("User not found"));;
		UserProfile userEntity = repository.save(modelMapper.map(user, UserProfile.class));
		return modelMapper.map(userEntity, UserProfileDto.class);
    }

	/*
	 * This method should be used to delete an existing user. Call the corresponding
	 * method of Respository interface.
	 */

    @Override
    public void deleteUser(String userId) throws UserProfileNotFoundException {
    	repository.findById(userId).orElseThrow(() -> new UserProfileNotFoundException("User Not Found"));
		repository.deleteById(userId);	
    }
    
	/*
	 * This method should be used to get userprofile by userId.Call the corresponding
	 * method of Respository interface.
	 */

    @Override
    public UserProfileDto getUserById(String userId) throws UserProfileNotFoundException {
    	UserProfile user = repository.findById(userId).orElseThrow(() -> new UserProfileNotFoundException("user not found"));
		return modelMapper.map(user, UserProfileDto.class);
    }

	
}
