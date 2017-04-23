package com.tr.biton.model;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tr.biton.app.BitCoin;
import com.tr.biton.interfaces.EscrowCoinService;




public class MainModel {
	
	
	EscrowCoinService escrowservice;
	Wallet escrowwallet;
	
	final Logger logger = LoggerFactory.getLogger(MainModel.class);
	
	public void escrowTest(){
		escrowwallet = escrowservice.createEscrowWallet(1);
		ECKey escrow = ECKey.fromPublicOnly(BitCoin.hexStringToByteArray("022b457eb5dadb1d493bcdb8427a0abe238c9e8f2db58c64995f2cac494ab6e99d"));
		ECKey buyer = ECKey.fromPublicOnly(BitCoin.hexStringToByteArray("0279ec6d1ef0654c7e370e2b0d2a8fc2d8bf6e486daf8c0d0dd7bca3c0c12f0db4"));
		ECKey seller = ECKey.fromPublicOnly(BitCoin.hexStringToByteArray("03cba60e730bf6a912dcfc1dda2541472b38cbe592d556fae982c3ee7361143d78"));
		
		Address escrowaddress = escrowservice.createEscrowContract(escrowwallet, escrow, buyer, seller);
		logger.info("Escrow address: "+escrowaddress.toString());
		
		escrowservice.escrowObserve(escrowaddress, escrowwallet);
		//String str = escrowservice.toString(escrowwallet, escrowaddress);
		//logger.info(str);
	}
	
	public void saveWallet(Wallet w){
		
	}
	
	public Wallet restoreWallet(String fname){
		return escrowwallet;
		
	}
	
	public MainModel(EscrowCoinService escrowservice){
		this.escrowservice = escrowservice;
	}
	
}
