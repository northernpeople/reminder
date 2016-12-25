package com.reminder.service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.reminder.model.Reminder;
import com.reminder.model.User;
import com.reminder.model.repo.ReminderRepository;


@Component
public class ReminderService {
	
	@Autowired
	UserService userService;
	
	@Autowired
	EmailService emailSender;
	
	@Autowired
	ReminderRepository reminderRepo;
	
	@Scheduled(fixedRate = 60000, initialDelay = 5000)
	public void sendReminders(){	

		for(User u : userService.findAll()){
			Set<Reminder> dueSoon = reminderRepo.findByUser(u).stream()
									.filter(r -> r.getRemindOn().isAfter(LocalDateTime.now()) 
											  && r.getRemindOn().isBefore(LocalDateTime.now().plusMinutes(1)))
									.collect(Collectors.toSet());
			for(Reminder r : dueSoon){
				emailSender.send(u.getEmail(), r.getTopic(), r.getText());
				reminderRepo.delete(r);
			}
					
		}		
	}


}
