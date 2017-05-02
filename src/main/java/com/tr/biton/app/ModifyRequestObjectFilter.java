package com.tr.biton.app;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;  
import javax.servlet.FilterChain;  
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;  
import javax.servlet.ServletRequest;  
import javax.servlet.ServletResponse;  
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;  

public class ModifyRequestObjectFilter implements Filter{
	private	static final Logger logger = LoggerFactory.getLogger(ModifyRequestObjectFilter.class);
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		final HttpServletRequest hsRequest = (HttpServletRequest) request;
		
		 try{  
			 
			 String url = hsRequest.getRequestURI().substring(hsRequest.getContextPath().length());
			 String supportedLanguages = "/ES/,/UK/,/PT/,/en/";
		        List<String> listOfLanguages = Arrays.asList(supportedLanguages.split(","));
		         
		           //If the URL already contains any of the allowed language identifiers, we continue with the original flow
		        for(String language : listOfLanguages)
		        {
		            if(StringUtils.startsWithIgnoreCase(url, language))
		            {
		                chain.doFilter(request, response);
		            }
		        }
			 
			 RequestDispatcher dispatcher = request.getRequestDispatcher("/en/".concat(url));
		     dispatcher.forward(request, response);
			 logger.info("dreeeeee: "+url);
             //request=(ServletRequest)new ModifyRequest((HttpServletRequest)request);  
             //chain.doFilter(request, response);  
        }catch(Exception e){  
             System.out.println("errrrrrrror "+e);  
        }  
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
