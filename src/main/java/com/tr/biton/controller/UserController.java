package com.tr.biton.controller;

import java.security.Principal;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tr.biton.app.ELUtils;
import com.tr.biton.app.Util;
import com.tr.biton.form.UserLogin;
import com.tr.biton.form.UserRegister1;
import com.tr.biton.orm.Address;
import com.tr.biton.orm.CargoAgency;
import com.tr.biton.orm.User;
import com.tr.biton.orm.User.UserType;
import com.tr.biton.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private JavaMailSender mailSender;
	
	final Logger logger = LoggerFactory.getLogger(UserController.class);
	/*
	@RequestMapping(value="/userloginaction")
	public ModelAndView userlogin(@ModelAttribute("userlogin")UserLogin userlogin, Principal principal){
		ModelAndView model = new ModelAndView("locations");
		
		logger.info("username: "+userlogin.getUsername());
		logger.info("pass: "+userlogin.getPassword());
		if (principal!=null){
			logger.info("principal username: "+principal.getName());
		}else{
			logger.info("principal is null!!!!");
		}
		
		model.addObject("userregister", new UserRegister1());
		model.addObject("userlogin", new UserLogin());
	
		return model;
	}
	*/
	@RequestMapping(value="/verifyuserregistration/{username}/{email:.+}")
	public @ResponseBody Integer verifyuserregistration(@PathVariable(name="username") String username, @PathVariable(name="email") String email) {
		
		if (userService.isUserExistsbyUsername(username))
			return 1;
		else if (userService.isUserExistsbyEmail(email))
			return 2;
		else 
			return 0;
	}
	
	@RequestMapping(value="/verifyusername/{username}")
	public @ResponseBody Integer verifyusername(@PathVariable(name="username") String username) {
		
		if (userService.isUserExistsbyUsername(username))
			return 1;
		else 
			return 0;
	}
	
	@RequestMapping(value="/verifyemail/{email:.+}/wa")
	public @ResponseBody Integer verifyemail(@PathVariable(name="email") String email) {
		if (userService.isUserExistsbyEmail(email))
			return 2;
		else 
			return 0;
	}
	
	@RequestMapping(value="/useractivate/{token}")
	public ModelAndView useractivate(@PathVariable(name="token") String token){
		ModelAndView model;// = new ModelAndView("userregister2");
		
		User user = userService.getUserByToken(token);
		user.setActive(true);
		userService.updateUser(user);
		userService.detach(user);
		
		Address address = new Address();
		user.setAddress(address);
		
		if (user.getUsertype()==UserType.ROLE_BUYER){
			model = new ModelAndView("buyerregister");
		}else if (user.getUsertype()==UserType.ROLE_SELLER){
			model = new ModelAndView("sellerregister");
			CargoAgency  ca = new CargoAgency();
			user.setCargoagency(ca);
		}else{
			//This should not happen
			model = new ModelAndView();
		}
		
		model.addObject("user", user);
		return model;
	}
	
	@RequestMapping(value="/login")
	public ModelAndView login(){
		ModelAndView model = new ModelAndView("login");
		model.addObject("userregister", new UserRegister1());
		model.addObject("userlogin", new UserLogin());
		//model.addObject("userlogin2", new UserLogin());
		return model;
	}
	
	@RequestMapping(value="/userregistrationaction", method = RequestMethod.POST)
	public String userregistration(@ModelAttribute("userregister")UserRegister1 userregister, HttpServletRequest request){
		
		User user = new User();
		String token;
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setUsername(userregister.getUsername());
		user.setPassword(passwordEncoder.encode(userregister.getPassword()));
		user.setUsertype(userregister.getUsertype());
		user.setEmail(userregister.getEmail());
		user.setActive(false);
		token = Util.getSaltString(18);
		user.setToken(token);
		
		SimpleMailMessage email = new SimpleMailMessage();
	    email.setTo("dhasan@blue-src.com");
	    email.setSubject("email test");
	    email.setText(ELUtils.getURLWithContextPath(request)+"/useractivate/"+token);
	    
	    // sends the e-mail
	    mailSender.send(email); 
		
		userService.createUser(user);
		
		return "redirect:/";		
	}
}
