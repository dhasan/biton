package com.tr.biton.service;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tr.biton.app.BitCoin;
import com.tr.biton.app.EscrowContractExtention;
import com.tr.biton.interfaces.EscrowContractObserver;

public class EscrowCoinService {
	
	private BitCoin bitcoin;
	
	final Logger logger = LoggerFactory.getLogger(EscrowCoinService.class);

	public Wallet createEscrowWallet(){ 
		Wallet w = bitcoin.createSinglekeyWallet();
		w.addExtension(new EscrowContractExtention(w, bitcoin.getContext(), bitcoin.getNetworkParams(), bitcoin.getChain(), bitcoin.getPeers()));
		return w;
	
	}
	
	public Address createEscrowContract(Wallet w, ECKey escrow, ECKey buyer, ECKey seller){
		EscrowContractExtention ext;
		ext = (EscrowContractExtention) w.getExtensions().get("com.tr.biton.app.EscrowContractExtention");
		
		return ext.createEscrowContract(escrow, buyer, seller);
	}
	
	public void escrowObserve(String address, Wallet w){
		EscrowContractExtention ext;
		ext = (EscrowContractExtention) w.getExtensions().get("com.tr.biton.app.EscrowContractExtention");
		ext.addEscrowContractObserver(address, new EscrowContractObserver(){

			public void Funded(String adddress, Coin by, Coin to) {
				// TODO Auto-generated method stub
				logger.info("FUNDEDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
				
			}

			public void Spend(String fromaddress, Coin amount, Coin left) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	

	public BitCoin getBitcoin() {
		return bitcoin;
	}

	public void setBitcoin(BitCoin bitcoin) {
		this.bitcoin = bitcoin;
	}
}
