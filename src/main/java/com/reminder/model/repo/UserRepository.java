package com.reminder.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reminder.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String Email);
}