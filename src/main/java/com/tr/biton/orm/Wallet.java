package com.tr.biton.orm;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

enum WalletType{
	OFFLINE,
	WATCHONLY,
	NORMAL
}

@Entity
@Table(name	= "WALLETS")
public class Wallet {
	@Id
	@Column(name = "id" ,unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private	int	id;
	
	@Column(name = "wallettype")
	@Enumerated(EnumType.ORDINAL)
	private WalletType wallettype;
	
	@Column(name = "name")
	private String name;	

	@Column(name = "xprv" ,unique = true)
	private String xprv;
	
	@Column(name = "xpub" ,unique = true)
	private String xpub;
	
	@Column(name = "derive")
	private String derive;
	
	@Column(name = "lastindex")
	private int lastindex;
//	
//	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinTable(name = "wallet_transaction",
//		joinColumns = { 
//				@JoinColumn(name = "wallet_id", nullable = false, updatable = false) 
//		},inverseJoinColumns = { 
//				@JoinColumn(name = "transaction_id", nullable = false, updatable = false) 
//		})
//	private Set<Transaction> transactions;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public WalletType getWallettype() {
		return wallettype;
	}

	public void setWallettype(WalletType wallettype) {
		this.wallettype = wallettype;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getXprv() {
		return xprv;
	}

	public void setXprv(String xprv) {
		this.xprv = xprv;
	}

	public String getXpub() {
		return xpub;
	}

	public void setXpub(String xpub) {
		this.xpub = xpub;
	}

	public String getDerive() {
		return derive;
	}

	public void setDerive(String derive) {
		this.derive = derive;
	}

	public int getLastindex() {
		return lastindex;
	}

	public void setLastindex(int lastindex) {
		this.lastindex = lastindex;
	}
	
	public String toString(){
		return "{id: "+id+", name:"+name+", type:"+wallettype+", derive:"+derive+"}";
	}
	
}
