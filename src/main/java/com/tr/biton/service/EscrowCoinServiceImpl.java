package com.tr.biton.service;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Context;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tr.biton.app.BitCoin;
import com.tr.biton.app.EscrowContractExtention;
import com.tr.biton.interfaces.EscrowCoinService;
import com.tr.biton.interfaces.EscrowContractObserver;


//@Service
public class EscrowCoinServiceImpl implements EscrowCoinService, Serializable{
	
	private BitCoin bitcoin;
	
	final Logger logger = LoggerFactory.getLogger(EscrowCoinServiceImpl.class);

	public Wallet createEscrowWallet(int conformations){ 
		Wallet w = bitcoin.createSinglekeyWallet();
		EscrowContractExtention ext = new EscrowContractExtention();
		ext.addListeners(w, bitcoin.getContext(), 
				bitcoin.getNetworkParams(), 
				bitcoin.getChain(), 
				bitcoin.getPeers(),
				conformations);
		w.addExtension(ext);
		
		return w;
	
	}
	
	//TODO: Create separette watchonly class 
	public Wallet createWatchOnlyWalletFromXPUB(String xpub){
		return bitcoin.createWatchOnlyWalletFromXPUB(xpub);
	}
	
	public void deleteEscrowContract(Wallet w, Address address){
		EscrowContractExtention ext;
		ext = (EscrowContractExtention) w.getExtensions().get("com.tr.biton.app.EscrowContractExtention");
		ext.getEntries().remove(address).toString();
		
		w.removeWatchedAddress(address);
	}
	
	public EscrowCoinServiceImpl(BitCoin bitcoin){
		this.bitcoin = bitcoin;
	}
	
	public Address createEscrowContract(Wallet w, ECKey escrow, ECKey buyer, ECKey seller){
		EscrowContractExtention ext;
		
		ext = (EscrowContractExtention) w.getExtensions().get("com.tr.biton.app.EscrowContractExtention");
		
		return ext.createEscrowContract(escrow, buyer, seller);
	}
	
	public void escrowObserve(Address address, final Wallet w){
		EscrowContractExtention ext;
		ext = (EscrowContractExtention) w.getExtensions().get("com.tr.biton.app.EscrowContractExtention");
		ext.addEscrowContractObserver(address, new EscrowContractObserver(){

			public void Funded(String address, Coin by, Coin to) {
				// TODO Auto-generated method stub
				logger.info("FUNDED ADDRESS: "+address+" by: "+by.toFriendlyString()+" to: "+to.toFriendlyString());
				
				logger.info(EscrowCoinServiceImpl.this.toString(w, Address.fromBase58(w.getParams(), address)));
				
				
				
			}

			public void Spend(String fromaddress, Coin amount, Coin left) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	
	}
	
	public String toString(Wallet w){
		EscrowContractExtention ext;
		ext = (EscrowContractExtention) w.getExtensions().get("com.tr.biton.app.EscrowContractExtention");
		return ext.toString();
	}
	
	public String toString(Wallet w, Address address){
		EscrowContractExtention ext;
		ext = (EscrowContractExtention) w.getExtensions().get("com.tr.biton.app.EscrowContractExtention");

		return ext.toString(address);
	}
	
	public void saveEscrowWallet(Wallet w,File f){
		try {
			w.saveToFile(f);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Wallet restoreEscrowWallet(String fname){
		Wallet w = null;
		try {
			w = Wallet.loadFromFile(new File(fname));
		} catch (UnreadableWalletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return w;
	}
	
	

//	public BitCoin getBitcoin() {
//		return bitcoin;
//	}
//
//	public void setBitcoin(BitCoin bitcoin) {
//		this.bitcoin = bitcoin;
//	}

}
