//package com.reminder.web;
//
//import javax.validation.Valid;
//
//import org.mindrot.jbcrypt.BCrypt;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.Errors;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.reminder.model.User;
//
//@Controller
//public class AdminController {
//
//	
//	
//	@RequestMapping(value = "/admin", method = RequestMethod.POST)
//	public String login(@Valid User candidate, Errors errors, RedirectAttributes model) {		
//		if(errors.hasErrors()){
//			return "user_form";		
//		}
//		User existing = userService.byEmail(candidate.getEmail());
//		if(existing != null){
//			if(BCrypt.checkpw(candidate.getPassword(), existing.getPassword())){ // sign in attempt with valid password
//				session.setAttribute("userEmail", existing.getEmail());	
//				return "redirect:/main";
//			}
//		}else{	
//			return registerNewUser(candidate);
//		}
//		return "redirect:/";	
//	}
//}
