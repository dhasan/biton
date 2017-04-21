package com.tr.biton.model;


import java.util.HashMap;
import java.util.List;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionConfidence;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.listeners.ScriptsChangeEventListener;
import org.bitcoinj.wallet.listeners.WalletCoinsReceivedEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.tr.biton.app.BitCoin;
import com.tr.biton.interfaces.AddressReceiveObserver;



public class MainModel {
	
	HashMap <String, AddressReceiveObserver> observers = new HashMap<String, AddressReceiveObserver>();
	private BitCoin bitcoin;
	
	private Wallet contractswallet;
	
	final Logger logger = LoggerFactory.getLogger(MainModel.class);
	
	public byte[] createSingleKeyWallet(){		
		Wallet w = bitcoin.createSinglekeyWallet();
		return w.getImportedKeys().get(0).getPubKey();
	}	
	
	public MainModel(BitCoin bitc){
		bitcoin = bitc;
		contractswallet = bitcoin.createSinglekeyWallet();
		addWallet(contractswallet);
		addReceiveListener(contractswallet,0);
	}
	
	public Wallet getContractsWallet(){
		return contractswallet;
	}
	
	public void addAddressReceiveObserver(String address, AddressReceiveObserver listener){
		observers.put(address, listener);	
	}
	
	public void removeAddressReceiveObserver(String address){
		observers.remove(address);
	}
	
	public HashMap<Integer, String> createContract(byte[] escrow, byte[] buyer, byte[] seller){
		
		return bitcoin.createContract(escrow, buyer, seller);
	}
	
	public String byteArrayToString(byte[] a){
		return bitcoin.byteArrayToHex(a);
	}
	
	public void addReceiveListener(Wallet w, final int conformations){
		bitcoin.setContext();
		//logger.info("ppppiiiiiiiiiiiiiiiii");
		w.addCoinsReceivedEventListener(new WalletCoinsReceivedEventListener(){
			
			private void broadcast(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance){
				for (TransactionOutput txo : tx.getOutputs()) {
				    //System.out.println(item);
					if (txo.getAddressFromP2PKHScript(bitcoin.getNetworkParams())!=null){
						logger.info("Output address: "+txo.getAddressFromP2PKHScript(bitcoin.getNetworkParams()).toString());
						AddressReceiveObserver obs = observers.get(txo.getAddressFromP2PKHScript(bitcoin.getNetworkParams()).toString());
						if (obs!=null){
							obs.onCoinsReceived(wallet, tx, prevBalance, newBalance, txo.getScriptBytes());
							return;
						}
					}
					if (txo.getAddressFromP2SH(bitcoin.getNetworkParams())!=null){
						logger.debug("Output script address: "+txo.getAddressFromP2SH(bitcoin.getNetworkParams()).toString());
						AddressReceiveObserver obs = observers.get(txo.getAddressFromP2SH(bitcoin.getNetworkParams()).toString());
						if (obs!=null){
							obs.onCoinsReceived(wallet, tx, prevBalance, newBalance, txo.getScriptBytes());
							return;
						}
					}
					//w.getBal
				}
			}
			
			public void onCoinsReceived(final Wallet wallet, final Transaction tx, final Coin prevBalance, final Coin newBalance) {
				// TODO Auto-generated method stub
				bitcoin.setContext();
				
				if (conformations==0){
					broadcast(wallet,tx,prevBalance, newBalance);
				}else{
					Futures.addCallback(tx.getConfidence().getDepthFuture(conformations), new FutureCallback<TransactionConfidence>() {

		                public void onSuccess(TransactionConfidence result) {
		                	broadcast(wallet,tx,prevBalance, newBalance);
		                }
	
		                public void onFailure(Throwable t) {
		                    throw new RuntimeException(t);
		                    //TODO: send Error
		                }
		            });
				}				
			}
			
		});
		
		w.addScriptsChangeEventListener(new ScriptsChangeEventListener(){

			public void onScriptsChanged(Wallet wallet, List<Script> scripts, boolean isAddingScripts) {

				logger.info("ccccccccccccccccccccccccccccccccccccccccccccccccccccc: ");
				
			}
			
		});
	}
	
	public long getWalletDate(Wallet w){
		return bitcoin.getWalletDate(w);
	}
	
	public void addWallet(Wallet w){
		bitcoin.addWallet(w);
	}
	
	public Wallet createWallet(){
		return bitcoin.createSinglekeyWallet();
	}
	
	
	public Wallet createWatchFromScript(String script){
		Wallet w = bitcoin.createSinglekeyWallet();
		bitcoin.addScriptToWallet(script,w);
		return w;
	}
	
	public void addWatchScriptToWallet(String script, Wallet w){
		bitcoin.addScriptToWallet(script,w);
	}
	
	public Wallet createWatchFromAddress(String hash160){
		Wallet w = bitcoin.createSinglekeyWallet();
		bitcoin.addAddressToWallet(hash160,w);
		return w;
	}
	
	public byte[] stringToByteArray(String s){
		return bitcoin.hexStringToByteArray(s);
	}
	
	public BitCoin getBitcoin() {
		return bitcoin;
	}

	public void setBitcoin(BitCoin bitcoin) {
		this.bitcoin = bitcoin;
	}
	
}
