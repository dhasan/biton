package com.tr.biton.dao;

import java.util.List;

import com.tr.biton.orm.Wallet;

public interface WalletDAO {
	public void addWallet(Wallet w);
	public void updateWallet(Wallet w);
	public List<Wallet> listWallets();
	public Wallet getWalletById(int id);
	public Wallet getWalletByName(String name);

}
