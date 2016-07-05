package com.jeff.common.tools;

import java.util.HashMap;
import java.util.Map;

public class JsonTool {
	public static Map<String, Object> genSuccessMsg(Object data){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "ok");
		map.put("data", data);
		return map;
	}
	
	
	public static Map<String, Object> genErrorMsg(Object data){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "error");
		map.put("data", data);
		return map;
	}
}
