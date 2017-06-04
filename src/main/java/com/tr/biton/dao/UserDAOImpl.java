package com.tr.biton.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.tr.biton.orm.Address;
import com.tr.biton.orm.Location;
import com.tr.biton.orm.Order;
import com.tr.biton.orm.Product;
import com.tr.biton.orm.User;
import com.tr.biton.orm.User.UserType;

@Repository
public class UserDAOImpl implements UserDAO{
private	static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	
	private	SessionFactory	sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void addUser(User user){
		Session	session	= this.sessionFactory.getCurrentSession();
		session.persist(user);
		logger.info("New user added");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<User> getUsers(){
		Session	session	= this.sessionFactory.getCurrentSession();
		List<User> users = session.createQuery("from users").getResultList();
		return users;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<User> getUserByRole(UserType role){
		Session	session	= this.sessionFactory.getCurrentSession();
		Query<User> query = session.createQuery("from users where usertype = :usertype");
		query.setParameter("usertype", role);
		return query.getResultList();
	}
	
	@Override
	public User getUserById(int id){
		Session	session	= this.sessionFactory.getCurrentSession();
		User u = (User)session.load(User.class, id);
		return u;
	}
	
	@Override
	public void updateUser(User u){
		Session	session	= this.sessionFactory.getCurrentSession();
		session.update(u);
	}
	
	@Override
	public void addOrder(User u, Order order){
		Session	session	= this.sessionFactory.getCurrentSession();
		u.getOrders().add(order);
		session.update(u);
		logger.info("User updated");
	}
	
	@Override
	public void addProduct(User u, Product product){
		Session	session	= this.sessionFactory.getCurrentSession();
		u.getProducts().add(product);
		session.update(u);
	}
	
	@Override
	public void removeProduct(User u, Product product){
		Session	session	= this.sessionFactory.getCurrentSession();
		u.getProducts().remove(product);
		session.update(product);
	}
	
	@Override
	public void setLocation(User u, Location loc){
		Session	session	= this.sessionFactory.getCurrentSession();
		u.setLocation(loc);
		session.update(u);
	}
	
	@Override
	public void addShipsto(User u, Location loc){
		Session	session	= this.sessionFactory.getCurrentSession();
		u.getShipsto().add(loc);
		session.update(u);
	}
	
	@Override
	public void removeShipsto(User u, Location loc){
		Session	session	= this.sessionFactory.getCurrentSession();
		u.getShipsto().remove(loc);
		session.update(u);
	}
	
	@Override
	public void setShipsto(User u, Set<Location> locs){
		Session	session	= this.sessionFactory.getCurrentSession();
		u.setShipsto(locs);
		session.update(u);
	}
	
	@Override
	public void addCarrier(User u, String carrier){
		Session	session	= this.sessionFactory.getCurrentSession();
		u.getCarriernames().add(carrier);
		session.update(u);
	}
	
	@Override
	public void removeCarrier(User u, String carrier){
		Session	session	= this.sessionFactory.getCurrentSession();
		u.getCarriernames().remove(carrier);
		session.update(u);
	}
	
	@Override
	public void setCarriers(User u, Set<String> carriers){
		Session	session	= this.sessionFactory.getCurrentSession();
		u.setCarriernames(carriers);
		session.update(u);
	} 
	
	@Override
	public void addAddress(User u, Address addr){
		Session session	= this.sessionFactory.getCurrentSession();
		u.getAddresses().add(addr);
		session.update(u);
	}
	
	@Override
	public void removeAddress(User u, Address addr){
		Session session	= this.sessionFactory.getCurrentSession();
		u.getAddresses().remove(addr);
		session.update(u);
	}
	
	@Override
	public void setAddresses(User u, List<Address> addrs){
		Session session	= this.sessionFactory.getCurrentSession();
		u.setAddresses(addrs);
		session.update(u);
	}

	@Override
	public boolean isUserExistsbyEmail(String email) {
		Session session	= this.sessionFactory.getCurrentSession();
		logger.info("email: "+email);
		return (!(session.createQuery("select u from User u where u.email = :email").setParameter("email", email).getResultList().isEmpty()));
	}

	@Override
	public boolean isUserExistsbyUsername(String username) {
		Session session	= this.sessionFactory.getCurrentSession();
		return (!(session.createQuery("select u from User u where u.username = :username").setParameter("username", username).getResultList().isEmpty()));
	}

	@Override
	public User getUserByToken(String token) {
		Session session	= this.sessionFactory.getCurrentSession();
		User user = (User)session.createQuery("select u from User u where u.token = :token").setParameter("token", token).getSingleResult();
		
		return user;
	} 
}
