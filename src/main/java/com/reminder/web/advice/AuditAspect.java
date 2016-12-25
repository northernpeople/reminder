//package com.reminder.web.advice;
//
//import javax.servlet.http.HttpSession;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class AuditAspect {
//	
//	@Autowired
//	HttpSession session;
//	
//	private static final Logger log = LoggerFactory.getLogger(AuditAspect.class);
//	
//	    @Pointcut("within(@org.springframework.stereotype.Controller *)")
//	    public void controller() {}
//
//	    @Pointcut("execution(* *(..))")
//	    public void method() {}
//	    
//	    @Pointcut("execution(* com.reminder.web.LoginController.*(..))")
//	    public void login() {}
//
//	    @AfterReturning("controller() && method()")
//	    public void logAfterMethodInControllerClass(JoinPoint jp) {
//	    	if(session.getAttribute("userEmail") != null){
//	    		log.info("{}-{}", session.getAttribute("userEmail"), jp.toLongString());
//	    	}
//	    }
//  
//	    @Around("controller() && method() && not login()")
//	    public Object onlyLetSignedInUsersIn(ProceedingJoinPoint jp) {
//	    	try {
//			    if(session.getAttribute("userEmail") != null){
//			    	return jp.proceed();
//			    } else {
//			    	return "redirect:/";
//			    }
//		    } catch (Throwable e) {
//	    		log.info("Attempting to call a method while unathenticated exception: {} method: {}", e.getMessage(),  jp.toLongString());
//		    }
//	    	return "redirect:/";
//	    }
//}
