package com.jeff.common.utils;

import org.springframework.util.StringUtils;

public class StringUtil extends StringUtils{
	/**
	 * 
	 * @author jeffwcx
	 * @method toLowerCaseFirstLetter
	 * @param @param str
	 * @param @return
	 * @return String
	 * @description  让首字母小写
	 * @date 2015年11月28日 下午1:43:57
	 */
	public static String toLowerCaseFirstLetter(String str){
		return str.substring(0, 1).toLowerCase()+str.substring(1);
	}
}
