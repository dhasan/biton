package com.tr.biton.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.wallet.Wallet;
import com.tr.biton.app.BitCoin;
import com.tr.biton.interfaces.AddressReceiveObserver;
import com.tr.biton.model.MainModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

@Controller
public class MainController{
	
	@Autowired
	private MainModel mainmodel;
	
	final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	Wallet contractwallet;

	@RequestMapping(value="/2")
	public String dve(ModelMap model) {
		HashMap<Integer, String> contractmap;
		
		byte[] escrow = mainmodel.stringToByteArray("022b457eb5dadb1d493bcdb8427a0abe238c9e8f2db58c64995f2cac494ab6e99d");//mainmodel.createSingleKeyWallet();
		byte[] buyer = mainmodel.stringToByteArray("0279ec6d1ef0654c7e370e2b0d2a8fc2d8bf6e486daf8c0d0dd7bca3c0c12f0db4");//mainmodel.createSingleKeyWallet();
		byte[] seller = mainmodel.stringToByteArray("03cba60e730bf6a912dcfc1dda2541472b38cbe592d556fae982c3ee7361143d78");//mainmodel.createSingleKeyWallet();
		
		logger.debug("escrow: "+ mainmodel.byteArrayToString(escrow));
		logger.debug("buyer: "+ mainmodel.byteArrayToString(buyer));
		logger.debug("seller: "+ mainmodel.byteArrayToString(seller	));
		
		contractmap = mainmodel.createContract(escrow, buyer, seller);
		logger.debug(contractmap.get(BitCoin.MULTISIG_ADDRESS));

		mainmodel.addWatchScriptToWallet(contractmap.get(BitCoin.SCRIPT), mainmodel.getContractsWallet());
		mainmodel.addAddressReceiveObserver(contractmap.get(BitCoin.MULTISIG_ADDRESS), new AddressReceiveObserver(){
			
			private HashMap<Integer, String> cm;
			
			public void onCoinsReceived(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance, byte[] sc) {
				
				cm.put(BitCoin.OUTPUT_SCRIPT, mainmodel.byteArrayToString(sc));
				cm.put(BitCoin.FEEDING_TRANSACTION, tx.toString());
				
				logger.debug("Coins prevbalance: "+prevBalance.toFriendlyString());
				logger.debug("Coins newbalance: "+newBalance.toFriendlyString());
				logger.debug("Coins osc: "+cm.get(BitCoin.OUTPUT_SCRIPT));
				logger.debug("Coins address: "+cm.get(BitCoin.MULTISIG_ADDRESS));
				logger.debug("Coins Feed transaction: " + cm.get(BitCoin.FEEDING_TRANSACTION));
			}
			
			public AddressReceiveObserver init(HashMap<Integer, String> hm){
				cm = hm;
				return this;
			}
		}.init(contractmap));
		
		return "WelcomePage";
	}
	
	@RequestMapping(value="/", method = RequestMethod.GET)
  	public String printHello(ModelMap model) {
 		return "WelcomePage";
	}

	public MainModel getMainmodel() {
		return mainmodel;
	}

	public void setMainmodel(MainModel mainmodel) {
		this.mainmodel = mainmodel;
	}


}