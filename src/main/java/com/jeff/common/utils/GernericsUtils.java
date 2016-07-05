package com.jeff.common.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GernericsUtils {
	public static Class getSuperClassGenricType(Class clazz){
		return getSuperClassGenricType(clazz,0);
	}
	
	
	public static Class getSuperClassGenricType(Class clazz,int index) throws IndexOutOfBoundsException{
		Type getType = clazz.getGenericSuperclass();
		if(!(getType instanceof ParameterizedType)){
			return Object.class;
		}
		
		Type[] params=((ParameterizedType) getType).getActualTypeArguments();
		
		if(index>=params.length||index<0){
			return Object.class;
		}
		
		if(!(params[index] instanceof Class)){
			return Object.class;
		}
		
		return (Class) params[index];
	}
}
