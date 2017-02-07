package com.reminder.web;

import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.reminder.model.Reminder;
import com.reminder.model.User;
import com.reminder.service.EmailService;
import com.reminder.service.UserService;
import com.reminder.web.command.PasswordChange;


@Controller
public class LoginController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	HttpSession session;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String userForm(Model model) {
		model.addAttribute("user", new User());
		return "user_form";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@Valid User candidate, Errors errors, RedirectAttributes model) {		
		if(errors.hasErrors()){
			return "user_form";		
		}
		User existing = userService.byEmail(candidate.getEmail());
		if(existing != null){
			if(BCrypt.checkpw(candidate.getPassword(), existing.getPassword())){ // sign in attempt with valid password
				session.setAttribute("userEmail", existing.getEmail());	
				return "redirect:/main";
			}
		}else{	
			return registerNewUser(candidate);
		}
		return "redirect:/";	
	}	
	
	private String registerNewUser(User candidate) {
		int code = new Random().nextInt(10000);
		session.setAttribute("code", code);
		session.setAttribute("candidate", candidate);
		emailService.send(candidate.getEmail(), 
				"Welcome to the reminder service!", 
				"Your registration code is: " + code);
		return "redirect:/email_confirm_form";
	}
	
	@RequestMapping(value = "/email_confirm", method = RequestMethod.POST)
	public String clientDone(@RequestParam String code, RedirectAttributes model) {
		if(code.trim().equalsIgnoreCase(session.getAttribute("code") + "")){
			User candidate = (User) session.getAttribute("candidate");
			userService.create(candidate);
		}
		session.invalidate();
		model.addFlashAttribute("warning", "Please try signing in");
		return "redirect:/";
	}
				
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(@ModelAttribute Reminder new_reminder, RedirectAttributes model) {			
		session.invalidate();
		model.addFlashAttribute("warning", "Successfully signed out");
		return "redirect:/";
	}
	
	@RequestMapping(value = "/email_confirm_form", method = RequestMethod.GET)
	public String confirmEmail(Model model) {
		return "email_confirm";
	}
	
	@RequestMapping(value="/change_password_form", method = RequestMethod.GET)
	public String changePasswordForm(Model model){
		model.addAttribute("request", new PasswordChange());
		return "change_password_form";
	}
	
	@RequestMapping(value="/change_password", method=RequestMethod.POST)
	public String changePassword(@Valid PasswordChange request, Errors errors, RedirectAttributes model){
		if(errors.hasErrors()){
			model.addFlashAttribute("warning", "something is not right");
			return "change_password_form";		
		}
		User current = currentUser();
		if(current == null || ! BCrypt.checkpw(request.getOld(), current.getPassword())){
			model.addFlashAttribute("warning", "please sign in again");
			return "redirect:/";
		}
		if(! request.getNew1().equals(request.getNew2())){
			errors.rejectValue("new2", "Match", "new passwords must match");
			return "change_password_form";		
		}
		current.setPassword(request.getNew1());
		userService.create(current);
		emailService.send(current.getEmail(), 
				"Your password for reminder service has been changed!", 
				"Please contact your administrator if you did not change it");
		return "redirect:/logout";
	}
	
	
	private User currentUser() {
		return userService.byEmail((String) session.getAttribute("userEmail"));
	}

}
