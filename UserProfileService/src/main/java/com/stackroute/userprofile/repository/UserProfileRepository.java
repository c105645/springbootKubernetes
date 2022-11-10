package com.stackroute.userprofile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stackroute.userprofile.model.UserProfile;

@Repository 
public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
	
	@Query(value = "SELECT n FROM UserProfile n WHERE n.email = ?1")
	Optional<UserProfile> findUserProfileByEmail(String email);

}
