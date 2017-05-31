package com.tr.biton.controller;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tr.biton.form.UserLogin;
import com.tr.biton.form.UserRegister1;

import com.tr.biton.orm.User;
import com.tr.biton.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value="/userloginaction")
	public ModelAndView userlogin(@ModelAttribute("userlogin")UserLogin userlogin){
		ModelAndView model = new ModelAndView("mainpage");
		
		logger.info("username: "+userlogin.getUsername());
		logger.info("pass: "+userlogin.getPassword());
		
		model.addObject("userregister", new User());
		model.addObject("userlogin", new User());
		return model;
	}
	
	@RequestMapping(value="/verifyuserregistration/{username}/{email:.+}")
	public @ResponseBody Integer verifyuserregistration(@PathVariable(name="username") String username, @PathVariable(name="email") String email) {
		Integer ret = 0;
		if (userService.isUserExistsbyUsername(username))
			return 1;
		else if (userService.isUserExistsbyEmail(email))
			return 2;
		else 
			return 0;
	}
	
	@RequestMapping(value="/verifyusername/{username}")
	public @ResponseBody Integer verifyusername(@PathVariable(name="username") String username) {
		Integer ret = 0;
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
	
	
	
	@RequestMapping(value="/userregistrationaction")
	public String userregistration(@ModelAttribute("userregister")UserRegister1 userregister){
		//ModelAndView model = new ModelAndView("mainpage");
		User user = new User();

		user.setUsername(userregister.getUsername());
		user.setPassword(userregister.getPassword());
		user.setUsertype(userregister.getUsertype());
		user.setEmail(userregister.getEmail());
		//user.setLocalbalanceAsLong(1234);
		//logger.info("val:----------- "+user.getLocalbalanceAsLong());
		
		userService.createUser(user);
		
		//model.addObject("userregister", new User());
		//model.addObject("userlogin", new User());
		return "redirect:/";		
	}
}
