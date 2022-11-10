package com.stackroute.user.service;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stackroute.user.dao.User;
import com.stackroute.user.dto.MyUserDetails;
import com.stackroute.user.repository.UserAuthRepository;

import reactor.core.publisher.Mono;

@Service
public class MyUserDetailsService implements ReactiveUserDetailsService {
	
	private final UserAuthRepository userAuthRepo;

	public MyUserDetailsService(UserAuthRepository userAuthRepo) {
		this.userAuthRepo = userAuthRepo;
	}

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        // TODO Auto-generated method stub
        User user = userAuthRepo.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with Id" + username + "dont exist"));
        MyUserDetails desired=new MyUserDetails(user);
        System.out.println("desired : " + desired.getUsername() + " " + desired.getPassword() + " " + desired.getAuthorities() );

        return Mono.just(desired);
    }
	
	
}
