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
		//HashMap<Integer, String> contractmap;
		
		
		return "WelcomePage";
	}
	
	@RequestMapping(value="/", method = RequestMethod.GET)
  	public String printHello(ModelMap model) {
		
		mainmodel.escrowTest();
		
 		return "WelcomePage";
	}

	public MainModel getMainmodel() {
		return mainmodel;
	}

	public void setMainmodel(MainModel mainmodel) {
		this.mainmodel = mainmodel;
	}


}