package com.tr.biton.controller;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@RequestMapping(value="/userregistrationaction")
	public String userregistration(@ModelAttribute("userregister")UserRegister1 userregister){
		//ModelAndView model = new ModelAndView("mainpage");
		User user = new User();

		user.setUsername(userregister.getUsername());
		user.setPassword(userregister.getPassword());
		user.setUsertype(userregister.getUsertype());
		user.setEmail(userregister.getEmail());
		user.setLocalbalanceAsLong(1234);
		logger.info("val:----------- "+user.getLocalbalanceAsLong());
		
		userService.createUser(user);
		
		//model.addObject("userregister", new User());
		//model.addObject("userlogin", new User());
		return "redirect:/";		
	}
}
