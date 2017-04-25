package com.tr.biton.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.tr.biton.orm.Wallet;

@Repository
public class WalletDAOImpl implements WalletDAO {
	private	static final Logger logger = LoggerFactory.getLogger(WalletDAOImpl.class);
	
	private	SessionFactory	sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void addWallet(Wallet w)	{
		Session	session	= this.sessionFactory.getCurrentSession();
		session.persist(w);
		logger.info("Wallet saved successfully, Wallet Details=" + w);
	}

	@Override
	public void updateWallet(Wallet w) {
		Session	session	=	this.sessionFactory.getCurrentSession();
		session.update(w);
		logger.info("Wallet updated successfully, Phone Details=" + w);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Wallet> listWallets() {
		Session	session	=	this.sessionFactory.getCurrentSession();
		List<Wallet> walletsList = session.createQuery("from WALLETS").list();
		for	(Wallet p : walletsList) {
			logger.info("Wallet List::" + p);
		}
		return	walletsList;
	}

	@Override
	public Wallet getWalletById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Wallet w = (Wallet)	session.load(Wallet.class, new Integer(id));
		logger.info("Wallet loaded successfully, Phone details=" + w);
		return	w;
	}
	
	@Override
	public Wallet getWalletByName(String name){
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from WALLETS where name = :name ");
		query.setParameter("name", name);
		List<Wallet> list = query.list();
		if (list.size()==0)
			return null;
		else if (list.size()>1){
			logger.warn("Thre is more than one wallet with same name");
			return (Wallet)list.get(0);
		}else
			return (Wallet)list.get(0);
	}
	
}
