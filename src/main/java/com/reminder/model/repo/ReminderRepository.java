package com.reminder.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reminder.model.Reminder;
import com.reminder.model.User;

public interface ReminderRepository extends JpaRepository<Reminder, Long>{
	List<Reminder> findByUser(User user);
}
