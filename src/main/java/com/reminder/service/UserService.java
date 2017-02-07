package com.reminder.service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reminder.model.User;
import com.reminder.model.repo.ReminderRepository;
import com.reminder.model.repo.UserRepository;


@Service
public class UserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ReminderRepository reminderRepo;
	

	public User create(User user) {
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		return userRepo.saveAndFlush(user);
	}
	
	public User update(User user) {
		return userRepo.saveAndFlush(user);
		
	}
	
	public User byEmail(String email){
		return userRepo.findByEmail(email);
	}

	public List<User> findAll() {
		return userRepo.findAll();
	}

}
