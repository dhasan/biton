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
import com.tr.biton.app.CountLisener;
import com.tr.biton.app.EscrowContractExtention;
import com.tr.biton.app.Pagination;
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

import javax.print.attribute.standard.PagesPerMinuteColor;

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
	
	private Map<String, Integer> pagi(	
							Integer pageid, 
							Integer pagesize,
							Integer pagecount,
							Integer itemscount){
		
		Integer pageid_loc,pagesize_loc,pagecount_loc,itemscount_loc;
		
		Map<String, Integer> pagi = new HashMap<String, Integer>();
		
		//Integer itemscount=0;
		if (pagesize==null)	pagesize_loc= 10;
		else pagesize_loc = pagesize;
		if (pageid==null)	pageid_loc=1;
		else pageid_loc = pageid;
		if (itemscount==null){
			itemscount_loc =  (Integer)locationservice.getLocations_count().intValue();
		}else
			itemscount_loc = itemscount;
		
		if ((pagecount==null)){
			
			pagecount_loc = (itemscount_loc/pagesize_loc);
			if  ((itemscount_loc%pagesize_loc)!=0){
				pagecount_loc++;
			}
		}else{
			pagecount_loc = pagecount;
			
		}
		//itemscount_loc = itemscount;
		
		while (pageid_loc>pagecount_loc){
			pageid_loc--;
		}
		if (pageid_loc<2) pageid_loc=1;
		
		
		return pagi;
		
	}
	
	@RequestMapping(value="/locations")
	public ModelAndView locs(	@RequestParam(value="page", required=false) Integer page,
								@RequestParam(value="pagesize", required=false) Integer pagesize,
								@RequestParam(value="pagescount", required=false) Integer pagecount,
								@RequestParam(value="itemscount", required=false) Integer itemscount) {
		
		
		ModelAndView model = new ModelAndView("locations");
		Pagination pag = new Pagination(page, pagesize, pagecount, itemscount, new CountLisener() {
			@Override
			public Integer getCount() {
				// TODO Auto-generated method stub
				return (Integer)locationservice.getLocations_count().intValue();
			}
		});
		List<Location> locs = locationservice.getLocations(pag.getPagesize()*(pag.getPageid()-1), pag.getPagesize());
		model.addObject("locs", locs);
		model.addAllObjects(pag.getPagi());
		
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