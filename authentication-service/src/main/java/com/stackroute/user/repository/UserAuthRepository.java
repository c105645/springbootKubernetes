package com.stackroute.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.user.dao.User;


/*
* This class is implementing the JpaRepository interface for Note.
* Annotate this class with @Repository annotation
* */

@Repository
public interface UserAuthRepository  extends JpaRepository<User, Long>{

	public Optional<User> findUserByUsername(String username);


}
