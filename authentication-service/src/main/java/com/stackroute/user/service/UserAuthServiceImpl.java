package com.stackroute.user.service;

import com.stackroute.user.util.JWT.JwtTokenUtil;
import com.stackroute.user.util.exception.UserAlreadyExistsException;
import com.stackroute.user.util.exception.UserIdAndPasswordMismatchException;
import com.stackroute.user.util.exception.UserNotFoundException;

import reactor.core.publisher.Mono;

import com.stackroute.user.dao.User;
import com.stackroute.user.dto.LoginRequest;
import com.stackroute.user.dto.MyUserDetails;
import com.stackroute.user.dto.UserDto;
import com.stackroute.user.repository.UserAuthRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.Optional;

   /*
	* Service classes are used here to implement additional business logic/validation 
	* This class has to be annotated with @Service annotation.
	* @Service - It is a specialization of the component annotation. It doesn't currently 
	* provide any additional behavior over the @Component annotation, but it's a good idea 
	* to use @Service over @Component in service-layer classes because it specifies intent 
	* better. Additionally, tool support and additional behavior might rely on it in the 
	* future.
	*/
@Service
public class UserAuthServiceImpl implements UserAuthService {

	/*
	 * Autowiring should be implemented for the UserAuthRepository and SQL Operation.
	 * (Use Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */

	/*
	 * This method should be used to find an existing User with correct password.
	 */
	
	private final UserAuthRepository userAuthRepo;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	private final ReactiveAuthenticationManager manager;
	private final JwtTokenUtil jwtutilservice;

	public UserAuthServiceImpl(UserAuthRepository userAuthRepo, ModelMapper modelMapper, PasswordEncoder passwordEncoder,
	        ReactiveAuthenticationManager manager,
			JwtTokenUtil jwtutilservice) { 
		this.userAuthRepo = userAuthRepo;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
		this.manager = manager;
		this.jwtutilservice = jwtutilservice;
	}

	/*
	 * This method should be used to save a new User.
	 */
    
    @Override
    public boolean saveUser(UserDto userdto) throws UserAlreadyExistsException {
    	Optional<User> ouser = userAuthRepo.findUserByUsername(userdto.getUsername());
    	
    	if(ouser.isPresent()) {
    		throw new UserAlreadyExistsException("User already exists");
    	}else {
    		User user = modelMapper.map(userdto, User.class);
    		user.setPassword(passwordEncoder.encode(user.getPassword()));
    		User savedUser = userAuthRepo.save(user);
    		if(savedUser != null) {
    			return true;
    		}else {
    			return false;
    		}
    	}
    	
    }

	@Override
	public Mono<String> authenticateAndgetAToken(LoginRequest user) throws UserNotFoundException {
		Authentication a = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());                
        return manager.authenticate(a).map(auth -> String.valueOf("Bearer " + jwtutilservice.generateToken(auth)));
  
	}

}
