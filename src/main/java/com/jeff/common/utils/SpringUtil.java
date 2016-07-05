package com.jeff.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @name SpringUtil.java
 * @author jeffwcx
 * @description 从spring容器中获取其中任意的bean
 */
public class SpringUtil implements ApplicationContextAware{

	private static ApplicationContext context = null;	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
	}
	
	
	public synchronized static Object getBean(String beanName){
		return context.getBean(beanName);
	}
	
}
