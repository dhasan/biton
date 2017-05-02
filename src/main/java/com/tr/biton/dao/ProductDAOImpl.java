package com.tr.biton.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tr.biton.orm.Category;
import com.tr.biton.orm.Location;
import com.tr.biton.orm.Product;
import com.tr.biton.orm.User;
import com.tr.biton.orm.Wallet;

public class ProductDAOImpl implements ProductDAO {
	
private	static final Logger logger = LoggerFactory.getLogger(WalletDAOImpl.class);

	final static String getProducts_query = "from products";
	
	private	SessionFactory	sessionFactory;
	
	@Override
	public void addProduct(Product p){
		Session	session	= this.sessionFactory.getCurrentSession();
		session.persist(p);
	}
	
	@Override
	public void updateProduct(Product p){
		Session	session	= this.sessionFactory.getCurrentSession();
		session.update(p);
	}
	
	@Override
	public void removeProduct(Product p){
		Session	session	= this.sessionFactory.getCurrentSession();
		session.delete(p);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Product> getProducts(int first, int max){
		Session	session	=	this.sessionFactory.getCurrentSession();
		TypedQuery<Product> query = session.createQuery(getProducts_query);
		query.setFirstResult(first);
		query.setMaxResults(max);
		return query.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Product> getProductsByUser(User u,int first, int max){
		Session	session	=	this.sessionFactory.getCurrentSession();
		TypedQuery<Product> query = session.createQuery("from products where user = :user");
		query.setParameter("user", u);
		query.setFirstResult(first);
		query.setMaxResults(max);
		return query.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Product> getProductsByCategory(Category cat, int first, int max){
		Session	session	=	this.sessionFactory.getCurrentSession();
		TypedQuery<Product> query = session.createQuery("form products where category = :cat");
		query.setParameter("cat", cat);
		query.setFirstResult(first);
		query.setMaxResults(max);
		return query.getResultList();
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<Product> getProductsByUserLocation(Location loc, int first, int max){
		Session	session	=	this.sessionFactory.getCurrentSession();
		TypedQuery<Product> query = session.createQuery("form products where user.location = :loc");
		query.setParameter("loc", loc);
		query.setFirstResult(first);
		query.setMaxResults(max);
		return query.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Product> getProductsByShipsto(Location loc, int first, int max){
		Session	session	=	this.sessionFactory.getCurrentSession();
		TypedQuery<Product> query = session.createQuery("from products as p left join locations as l where l = :loc");
		query.setParameter("loc", loc);
		query.setFirstResult(first);
		query.setMaxResults(max);
		return query.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Product> getProductsByShipstos(List<Location> locs, int first, int max){
		Session	session	=	this.sessionFactory.getCurrentSession();
		TypedQuery<Product> query = session.createQuery("from products as p left join locations as l where l = :loc");
		query.setParameter("loc", locs);
		
		return query.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Product> getProductsLikeNameOrDescription(String name){
		Session	session	=	this.sessionFactory.getCurrentSession();
		TypedQuery<Product> query = session.createQuery("from products where name like :name or description like :name");
		query.setParameter("name", "%"+name+"%");
		return query.getResultList();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
}
