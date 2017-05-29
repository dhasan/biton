package com.tr.biton.service;

import java.util.List;
import java.util.Map;

import com.tr.biton.orm.User;

public interface UserService {
	public void createUser(User user);
	public User getUserbyId(int id);
	public User getUserbyUsername(String username);
	
	public Long getUsers_count(Map<String, Object> args);
	public List<User> getUsers(Integer first, Integer count, Map<String, Object> args);
}
