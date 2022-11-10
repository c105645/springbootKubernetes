package com.stackroute.user.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.stackroute.user.service.MyUserDetailsService;
import com.stackroute.user.service.UserAuthServiceImpl;
import com.stackroute.user.util.exception.UserIdAndPasswordMismatchException;

import reactor.core.publisher.Mono;

@Component
public class MyCustomProvider implements ReactiveAuthenticationManager  {
	
	@Autowired
	 private MyUserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	 public Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {	  
	  String providedUsername = authentication.getPrincipal().toString();
	  Mono<UserDetails> user = userDetailsService.findByUsername(providedUsername);
	  
	  return user.map(u-> {
	      String providedPassword = authentication.getCredentials().toString();
	      String correctPassword = u.getPassword();
	      if(!passwordEncoder.matches(providedPassword, correctPassword))
	          throw new UserIdAndPasswordMismatchException("Incorrect Credentials");
	      
	      Authentication authenticationResult = 
	              new UsernamePasswordAuthenticationToken(u, authentication.getCredentials(), u.getAuthorities());
	      SecurityContext context = SecurityContextHolder.getContext();
	      context.setAuthentication(authenticationResult);
	      return authenticationResult;
	  });
	 
	 }
	}