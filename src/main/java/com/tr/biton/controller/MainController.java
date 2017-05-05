package com.tr.biton.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.wallet.Wallet;
import com.tr.biton.app.BitCoin;
import com.tr.biton.app.EscrowContractExtention;
import com.tr.biton.model.MainModel;
import com.tr.biton.orm.Location;
import com.tr.biton.service.LocationService;
import com.tr.biton.service.WalletService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController{
	
	@Autowired
	private MainModel mainmodel;
	
	@Autowired
	private WalletService walletservice;
	
	@Autowired
	private LocationService locationservice;
	
	

	final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	Wallet contractwallet;

	@RequestMapping(value="/2")
	public String dve(ModelMap model) {
		//HashMap<Integer, String> contractmap;
		
//		com.tr.biton.orm.Wallet w = new com.tr.biton.orm.Wallet();
//		w.setName("testnamee");
//		walletservice.addWallet(w);
		
		
		return "WelcomePage";
	}
	
	
	@RequestMapping(value="/en/2")
	public String dveen(ModelMap model) {
		//HashMap<Integer, String> contractmap;
		
		com.tr.biton.orm.Wallet w = new com.tr.biton.orm.Wallet();
		w.setName("testnamee");
		walletservice.addWallet(w);
		logger.info("dddddddddddddddddddddddddd");
		
		return "WelcomePage";
	}
	
	@RequestMapping(value="/locations")
	public ModelAndView locs(	@RequestParam(value="page", required=false) Integer page,
								@RequestParam(value="pagesize", required=false) Integer pagesize,
								@RequestParam(value="pagecount", required=false) Integer pagecount) {
		
		
		ModelAndView model = new ModelAndView("locations");
		Map<String, Long> pagination = new HashMap<String, Long>();
		
		Long itemscount=(long)0;
		if (pagesize==null)	pagesize= 10;
		if (page==null)	page=1;
		if (pagecount==null){
			itemscount =  locationservice.getLocations_count();
			pagecount = (int) (itemscount/pagesize);
			if  ((itemscount%pagesize)!=0){
				pagecount++;
			}
		}
		while (page>pagecount){
			page--;
		}
		List<Location> locs = locationservice.getLocations(pagesize*(page-1), pagesize);
		
		model.addObject("locs", locs);
		
		return model;
	}
	
	@RequestMapping(value="/", method = RequestMethod.GET)
  	public String printHello(ModelMap model) {
		
		mainmodel.escrowTest();
		
 		return "WelcomePage";
	}
	
	@RequestMapping(value="/save", method = RequestMethod.GET)
  	public String savewallet(ModelMap model) {
		
		mainmodel.saveEscrowWallet(new File("escrowWallet.wallet"));
		
 		return "WelcomePage";
	}
	
	@RequestMapping(value="/restore", method = RequestMethod.GET)
  	public String restorewallet(ModelMap model) {
		
		mainmodel.restoreWallet(new String("escrowWallet.wallet"));
		EscrowContractExtention ext = (EscrowContractExtention) mainmodel.getEscrowwallet().getExtensions().get("com.tr.biton.app.EscrowContractExtention");
		if (ext==null)
			logger.info("eeror extension is zero");
		else
			ext.toString();
		
		return "WelcomePage";
	}

	public MainModel getMainmodel() {
		return mainmodel;
	}

	public void setMainmodel(MainModel mainmodel) {
		this.mainmodel = mainmodel;
	}

	public void setWalletservice(WalletService walletservice) {
		this.walletservice = walletservice;
	}
}