package com.stackroute.newz.repository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stackroute.newz.dao.News;


@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
	
	@Query(value = "SELECT n FROM News n WHERE n.title = ?1")
	Optional<News> findNewsByTitle(String title);
	
	@Query(value = "SELECT n FROM News n WHERE n.userId = ?1")
	List<News> findNewsByUserId(String userId);
	
	
	@Query(value = "SELECT n FROM News n WHERE n.userId = ?1 and n.newsId = ?2")
	Optional<News> findNewsByNewsIdAndUserId(String userId, Long newsId);
	
	@Modifying
	@Query(value = "DELETE FROM News n WHERE n.userId = ?1 and n.newsId = ?2")
	int DeleteByUserIdAndNewsId(String userId, Long newsId);
	
	@Modifying
	@Query(value = "DELETE FROM News n WHERE n.userId = ?1")
	int DeleteByUserId(String userId);

}
