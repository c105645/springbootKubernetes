package com.stackroute.newz.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.stackroute.newz.model.NewsSource;

@Repository
public interface NewsSourceRepository extends JpaRepository<NewsSource, Long> {
	
	@Query(value = "SELECT n FROM NewsSource n WHERE n.newssourceName = ?1")
	Optional<NewsSource> findNewsByName(String newssourceName);
	
	
	@Query(value = "SELECT n FROM NewsSource n WHERE n.newssourceCreatedBy = ?1 and n.newssourceId = ?2")
	Optional<NewsSource> findNewsSourceBySourceIdAndUserId(String userId, Long newssourceId);
	
	@Query(value = "SELECT n FROM NewsSource n WHERE n.newssourceCreatedBy = ?1")
	List<NewsSource> findNewsSourceByUserId(String userId);
}