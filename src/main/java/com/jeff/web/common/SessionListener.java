package com.jeff.web.common;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SessionListener implements HttpSessionListener{

	

	
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		
		HttpSession s = event.getSession();
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(s.getServletContext());
		s.invalidate();
	}
	
}
