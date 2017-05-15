package com.tr.biton.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.tr.biton.orm.Location;

@Repository
public class LocationDAOImpl implements LocationDAO{

	private	static final Logger logger = LoggerFactory.getLogger(LocationDAOImpl.class);
	
	private	SessionFactory	sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Location> getLocations(Integer start, Integer count, Map<String, Object> args) {
		Session session = sessionFactory.getCurrentSession();
		
		String hql = "from Location";
		TypedQuery<Location> query = session.createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(count);
		List<Location> list = query.getResultList();
		//session.flush();
		session.clear();
		//session.close();
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long getLocations_count(Map<String, Object> args) {
		Session session = sessionFactory.getCurrentSession();
		
		String hql = "SELECT COUNT(*) from Location";
		Query query = session.createQuery(hql);
		Long count = (Long)query.uniqueResult();
		
		//session.flush();
		session.clear();
		//session.close();
		return count;
	}
	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
