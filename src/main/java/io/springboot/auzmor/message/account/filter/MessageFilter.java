package io.springboot.auzmor.message.account.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import com.google.gson.Gson;

import io.springboot.auzmor.message.account.entity.AccountEntity;
import io.springboot.auzmor.message.account.repository.AccountDAOService;

@Service
public class MessageFilter extends GenericFilterBean{
	
	private final static Logger logger = Logger.getLogger(MessageFilter.class);
	
	@Autowired
	private AccountDAOService accountDAOService;
	

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		final boolean debug = logger.isDebugEnabled();
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
       
      /* Enumeration headerNames = request.getHeaderNames();
       while (headerNames.hasMoreElements()) {
           String key = (String) headerNames.nextElement();
           String value = request.getHeader(key);
           System.out.println("key "+key+" value : "+value);
       }
       
       System.out.println(request.getRemoteAddr());*/
        
        String username = request.getHeader("username");
        String password = request.getHeader("password");
        
        if(username == null || password == null) {
        	Map<String,String> responsejson = new HashMap<>();
        	responsejson.put("message", "Account not present");
        	Gson gson = new Gson();
        	response.sendError(403, gson.toJson(responsejson));
        	return;
        }
        
        AccountEntity account = this.accountDAOService.findByUsernameAndPassword(username, password);
        
        if(account == null) {
        	Map<String,String> responsejson = new HashMap<>();
        	responsejson.put("message", "Account not present");
        	Gson gson = new Gson();
        	response.sendError(403, gson.toJson(responsejson));
        	return;
        }
        
        chain.doFilter(request, response);	
	}

}
