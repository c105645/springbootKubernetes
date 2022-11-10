package com.stackroute.newz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.stackroute.newz.dao.NewsSource;

@Repository
public interface NewssourceRepository extends JpaRepository<NewsSource, Long> {

}