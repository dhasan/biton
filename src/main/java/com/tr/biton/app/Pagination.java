package com.tr.biton.app;

import java.util.HashMap;
import java.util.Map;

public class Pagination {
	private Integer pageid_loc;
	private Integer pagesize_loc;
	private Integer pagecount_loc; 
	private Integer itemscount_loc;
	private CountLisener cl;
	
	Map<String, Integer> pagi;
	
	public Integer getPageid() {
		return pageid_loc;
	}

	public Integer getPagesize() {
		return pagesize_loc;
	}

	public Integer getPagecount() {
		return pagecount_loc;
	}

	public Integer getItemscount() {
		return itemscount_loc;
	}

	
	
	public Pagination (	
			Integer pageid, 
			Integer pagesize,
			Integer pagecount,
			Integer itemscount,
			CountLisener cl
			){
		pagi = new HashMap<String, Integer>();
		
		pageid_loc = pageid;
		pagesize_loc = pagesize;
		pagecount_loc = pagecount;
		itemscount_loc = itemscount;
		
		if (pagesize==null)	pagesize_loc= 10;
		else pagesize_loc = pagesize;
		if (pageid==null)	pageid_loc=1;
		else pageid_loc = pageid;
		if (itemscount==null){
			itemscount_loc =  cl.getCount();
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
		
		while (pageid_loc>pagecount_loc){
			pageid_loc--;
		}
		if (pageid_loc<2) pageid_loc=1;
	}
	
	public Map<String, Integer> getPagi(){
		
		pagi.put("page", pageid_loc);
		pagi.put("pagesize", pagesize_loc);
		pagi.put("itemscount", itemscount_loc);
		pagi.put("pagescount", pagecount_loc); 
		
		
		return pagi;
	}
	
}
