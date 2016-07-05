package com.jeff.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
	public static void addCookie(HttpServletResponse response,String name, String value, int maxAge){
		Cookie cookie=new Cookie(name,value);
		cookie.setPath("/");
		if(maxAge>0){
			cookie.setMaxAge(maxAge);
		}
		response.addCookie(cookie);
		
	}
	public static Cookie getCookieByName(HttpServletRequest request, String name){
		Cookie[] cookies = request.getCookies();
		if(null!=cookies){
			for(Cookie cookie : cookies){
				if(cookie.getName().equals(name)){
					return cookie;
				}
			}
		}
		return null;
	}
}
