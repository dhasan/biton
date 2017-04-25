package com.tr.biton.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tr.biton.dao.WalletDAO;
import com.tr.biton.orm.Wallet;

@Service
public class WalletServiceImpl implements WalletService{
	
	@Autowired
	private	WalletDAO walletDAO;
	
	@Override
	@Transactional
	public void addWallet(Wallet w)	{
		walletDAO.addWallet(w);
	}
	
	
	
	public void setWalletDAO(WalletDAO walletDAO) {
		this.walletDAO = walletDAO;
	}

}
