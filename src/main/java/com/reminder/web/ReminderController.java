package com.reminder.web;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.reminder.model.Reminder;
import com.reminder.model.User;
import com.reminder.model.repo.ReminderRepository;
import com.reminder.service.EmailService;
import com.reminder.service.UserService;


@Controller
public class ReminderController {

	@Autowired
	UserService userService;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	EmailService emailSender;
	
	@Autowired
	ReminderRepository reminderRepo;
	
	@ModelAttribute("reminders")
	public List<Reminder> addReminders(){
		return reminderRepo
				.findByUser(currentUser())
				.stream()
				.filter( r -> r.getRemindOn().isAfter(LocalDateTime.now()))
				.collect(Collectors.toList());
	}
		
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String mainPage(Model model) {
		model.addAttribute("reminder", new Reminder());
		return "main";
	}
	
	@RequestMapping(value = "/add_reminder", method = RequestMethod.POST)
	public String login(@Valid Reminder reminder, Errors errors, RedirectAttributes model) {
		if(errors.hasErrors() || reminder.getRemindOn().isBefore(LocalDateTime.now().plusMinutes(10))){
			model.addFlashAttribute("warning", "Please fix the errors below:");
			if(reminder.getRemindOn().isBefore(LocalDateTime.now().plusMinutes(10)))
				errors.rejectValue("remindOn", "Size", "must be at least 10 minutes from now");			
			return "main";
		}

		User u = currentUser();
		if(u.getReminders().contains(reminder)){
			model.addFlashAttribute("warning", "This reminder already exists");
			return "redirect:/main";
		}
		
		u.link(reminder);
		userService.update(u);
		return "redirect:/main";
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteReminder (@PathVariable("id") Long id, RedirectAttributes model) {    
		User u = currentUser();
		Reminder r = u.getReminders().stream().filter( x -> x.getId() == id).findFirst().get();
		r.setUser(null);
		u.getReminders().remove(r);
		userService.update(u);
		model.addFlashAttribute("warning", "Reminder deleted");
		return "redirect:/main";
    }
	
	private User currentUser() {
		return userService.byEmail((String) session.getAttribute("userEmail"));
	}
	
}
