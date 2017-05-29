package com.tr.biton.app;

public class PagiModel {
	private Integer page;
	private Integer pagesize;
	private Integer pagecount;
	private Long itemscount;
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPagesize() {
		return pagesize;
	}
	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}
	public Integer getPagecount() {
		return pagecount;
	}
	public void setPagecount(Integer pagecount) {
		this.pagecount = pagecount;
	}
	public Long getItemscount() {
		return itemscount;
	}
	public void setItemscount(Long itemscount) {
		this.itemscount = itemscount;
	}
	
	
}
