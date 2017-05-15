package com.tr.biton.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pagination {
	private Integer pageid_loc;
	private Integer pagesize_loc;
	private Integer pagecount_loc; 
	private Long itemscount_loc;
	private CountLisener cl;
	private Map<String, Object> args;
	private List data;
	
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

	public Long getItemscount() {
		return itemscount_loc;
	}

	
	
	public Pagination (	
			Map<String, Object> args,
			Integer pageid, 
			Integer pagesize,
			Integer pagecount,
			Long itemscount,
			CountLisener cl
			){
		pagi = new HashMap<String, Integer>();
		//this.args = args;
		//this.cl = cl;
		pageid_loc = pageid;
		pagesize_loc = pagesize;
		pagecount_loc = pagecount;
		itemscount_loc = itemscount;
		
		if (pagesize==null)	pagesize_loc= 10;
		else pagesize_loc = pagesize;
		if (pageid==null)	pageid_loc=1;
		else pageid_loc = pageid;
		if (itemscount==null){
			itemscount_loc =  cl.getCount(args);
		}else
			itemscount_loc = itemscount;
		
		if ((pagecount==null)){
			
			pagecount_loc = ((Integer)(itemscount_loc.intValue())/pagesize_loc);
			if  ((itemscount_loc%pagesize_loc)!=0){
				pagecount_loc++;
			}
		}else{
			pagecount_loc = pagecount;
			
		}
		
		while (pageid_loc>pagecount_loc){
			pageid_loc--;
		}
		if (pageid_loc<1) pageid_loc=1;
		
		data = cl.getData(pagesize_loc*(pageid_loc - 1), pagesize_loc, args);
		//return locationservice.getLocations(this.getPagesize()*(pag.getPageid()-1), pag.getPagesize(), args);
	}
	
	public List getData(){
		return data;
	}
	
	public Map<String, Integer> getPagi(){
		
		pagi.put("page", pageid_loc);
		pagi.put("pagesize", pagesize_loc);
		pagi.put("itemscount", (Integer)itemscount_loc.intValue());
		pagi.put("pagescount", pagecount_loc); 	
		
		return pagi;
	}	
}
