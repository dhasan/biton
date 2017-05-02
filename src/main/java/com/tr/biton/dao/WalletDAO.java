package com.tr.biton.dao;

import java.util.List;

import com.tr.biton.orm.Transaction;
import com.tr.biton.orm.Wallet;

public interface WalletDAO {
	public void addWallet(Wallet w);
	public void updateWallet(Wallet w);
	public List<Wallet> listWallets();
	public Wallet getWalletById(int id);
	public Wallet getWalletByName(String name);
	public void addWalletTransaction(Wallet w, Transaction tx);
	public void addWalletExternalAddress(Wallet w, String eaddress);
	public void removeWalletExternalAddress(Wallet w, String address);

}
