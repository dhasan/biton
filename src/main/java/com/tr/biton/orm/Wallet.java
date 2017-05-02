package com.tr.biton.orm;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnTransformer;

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
	@ColumnTransformer(
	          read="pgp_sym_decrypt(name::bytea, 'mySecretKey')", 
	          write="pgp_sym_encrypt(?, 'mySecretKey')")
	private String xprv;
	
	@Column(name = "xpub" ,unique = true)
	@ColumnTransformer(
	          read="pgp_sym_decrypt(name::bytea, 'mySecretKey')", 
	          write="pgp_sym_encrypt(?, 'mySecretKey')")
	private String xpub;
	
	@Column(name = "derive")
	private String derive;
	
	@Column(name = "lastindex")
	private int lastindex;
	
	@Column(name = "threshold")
	private int threshold;
	
	@ElementCollection
	@CollectionTable(name = "wallet_externalAddresses")
	private Set<String> externalAddresses;
	
	@Column(name = "payload")
	private byte[] payload;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "wallet_transaction",
		joinColumns = { 
				@JoinColumn(name = "wallet_id") 
		},inverseJoinColumns = { 
				@JoinColumn(name = "transaction_id") 
		})
	private List<Transaction> transactions;

	public int getId() {
		return id;
	}

	public void ListId(int id) {
		this.id = id;
	}

	public WalletType getWallettype() {
		return wallettype;
	}

	public void ListWallettype(WalletType wallettype) {
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

	public void ListXprv(String xprv) {
		this.xprv = xprv;
	}

	public String getXpub() {
		return xpub;
	}

	public void ListXpub(String xpub) {
		this.xpub = xpub;
	}

	public String getDerive() {
		return derive;
	}

	public void ListDerive(String derive) {
		this.derive = derive;
	}

	public int getLastindex() {
		return lastindex;
	}

	public void ListLastindex(int lastindex) {
		this.lastindex = lastindex;
	}
	
	public byte[] getPayload() {
		return payload;
	}

	public void ListPayload(byte[] payload) {
		this.payload = payload;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void ListTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public int getThreshold() {
		return threshold;
	}

	public void ListThreshold(int threshold) {
		this.threshold = threshold;
	}

	public Set<String> getExternalAddresses() {
		return externalAddresses;
	}

	public void ListExternalAddresses(Set<String> externalAddresses) {
		this.externalAddresses = externalAddresses;
	}
	
	public String toString(){
		return "{id: "+id+", name:"+name+", type:"+wallettype+", derive:"+derive+"}";
	}
	
	
}
