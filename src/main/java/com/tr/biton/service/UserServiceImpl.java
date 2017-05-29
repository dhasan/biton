package com.tr.biton.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tr.biton.dao.LocationDAO;
import com.tr.biton.dao.UserDAO;
import com.tr.biton.orm.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private	UserDAO userDAO;
	
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	@Transactional
	public void createUser(User user) {
		userDAO.addUser(user);		
	}

	@Override
	@Transactional
	public User getUserbyId(int id) {
		return userDAO.getUserById(id);
	}

	@Override
	@Transactional
	public User getUserbyUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Long getUsers_count(Map<String, Object> args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public List<User> getUsers(Integer first, Integer count, Map<String, Object> args) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
