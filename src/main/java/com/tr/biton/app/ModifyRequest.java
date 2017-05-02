package com.tr.biton.app;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ModifyRequest extends HttpServletRequestWrapper{

	private HttpServletRequest req;  
	private String value=null;  
	
	private	static final Logger logger = LoggerFactory.getLogger(ModifyRequest.class);

	public ModifyRequest(HttpServletRequest request) {
		super(request);
		req=request;  
		
	}
	@Override  
    public String getHeader(String name){  
         value=super.getHeader(name); 
         logger.info("name: "+name+" value: "+value+" req: "+req.getRequestURI());
        
       
         return value;  
    }  

}
