package com.tr.biton.dao;

import java.util.List;
import java.util.Set;

import com.tr.biton.orm.Address;
import com.tr.biton.orm.Location;
import com.tr.biton.orm.Order;
import com.tr.biton.orm.Product;
import com.tr.biton.orm.User;
import com.tr.biton.orm.User.UserType;

public interface UserDAO {
	public void addUser(User user);
	public List<User> getUsers();
	public User getUserById(int id);
	public List<User> getUserByRole(UserType role);
	public void updateUser(User u);
	void addOrder(User u, Order order);
	void addProduct(User u, Product product);
	void removeProduct(User u, Product product);
	void setLocation(User u, Location loc);
	void addShipsto(User u, Location loc);
	void removeShipsto(User u, Location loc);
	void setShipsto(User u, Set<Location> locs);
//	void addCarrier(User u, String carrier);
//	void removeCarrier(User u, String carrier);
//	void setCarriers(User u, Set<String> carriers);
//	void addAddress(User u, Address addr);
//	void removeAddress(User u, Address addr);
//	void setAddresses(User u, List<Address> addrs);
	
	public boolean isUserExistsbyEmail(String email);
	public boolean isUserExistsbyUsername(String username);
	public User getUserByToken(String token);
	public void detach(User user);
}
