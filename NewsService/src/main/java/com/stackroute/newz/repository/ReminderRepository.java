package com.stackroute.newz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.newz.dao.Reminder;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {

}
