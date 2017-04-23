package com.tr.biton.app;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.ECKey;

import com.tr.biton.interfaces.EscrowContractObserver;

public class EscrowContractEntry implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final Integer ESCROW = 	0;
	public static final Integer BUYER = 	1;
	public static final Integer SELLER = 	2;
	
	private Address multisigAddress;
	private byte[] hash160;
	private HashSet<byte[]> transactions;
	private byte[] redeemScript;
	private byte[] script;
	private HashMap<Integer, ECKey> keys;
	private HashMap<Integer, byte[]> signs;
	private Coin balance;
	private EscrowContractObserver observer;
	public HashMap<Integer, byte[]> getSigns() {
		return signs;
	}

	public void setSigns(HashMap<Integer, byte[]> signs) {
		this.signs = signs;
	}
	
	public void addSing(byte[] sign, Integer key){
		signs.put(key, sign);
	}
	
	public void removesign(Integer key){
		signs.remove(key);
	}

	public byte[] getSign(Integer key){
		return signs.get(key);
	}
	public EscrowContractObserver getObserverbs() {
		return observer;
	}

	public void setObserver(EscrowContractObserver observer) {
		this.observer = observer;
	}

	public EscrowContractEntry(){
		
		transactions = new HashSet<byte[]>();
		keys = new HashMap<Integer, ECKey>();
		signs = new HashMap<Integer, byte[]>();
		balance = Coin.ZERO;
	}
	
	public void addBalance(Coin by){
		balance = balance.add(by);
	}
	
	public void subtractBalance(Coin by){
		balance = balance.subtract(by);
	}
	
	public Coin getBalance() {
		return balance;
	}

	public void setBalance(Coin balance) {
		this.balance = balance;
	}

	public void addKey(ECKey key, Integer keyid){
		keys.put(keyid, key);
	}
	
	public byte[] getHash160() {
		return hash160;
	}

	public void setHash160(byte[] hash160) {
		this.hash160 = hash160;
	}

	public byte[] getRedeemScript() {
		return redeemScript;
	}

	public void setRedeemScript(byte[] redeemScript) {
		this.redeemScript = redeemScript;
	}

	public byte[] getScript() {
		return script;
	}

	public void setScript(byte[] script) {
		this.script = script;
	}

	public HashMap<Integer, ECKey> getKeys() {
		return keys;
	}

	public void addTransaction(byte[] tx){
		transactions.add(tx);
	}
	
	public void removeTransaction(byte[] tx){
		transactions.remove(tx);
	}	
	
	public HashSet<byte[]> getTransactions() {
		return transactions;
	}

	public void setTransactions(HashSet<byte[]> transactions) {
		this.transactions = transactions;
	}
	
	public Address getMultisigAddress() {
		return multisigAddress;
	}

	public void setMultisigAddress(Address multisigAddress) {
		this.multisigAddress = multisigAddress;
	}

	
}
