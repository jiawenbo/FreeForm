package com.jeff.common.utils;


public class Base64 {
	private final static String c ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	public static String encode(String str){
		byte[] bytes = str.getBytes();
		String binaryStr = "";
		for(int i=0; i<bytes.length; i++){
			String temp = Integer.toBinaryString(bytes[i]&0xFF);
			int l = temp.length();
			if(l<8){
				for(int j=0; j<8-l; j++){
					temp="0"+temp;
				}
			}
			binaryStr+=temp;
		}
		int bl = binaryStr.length();
		String r = "";
		int plus=0;
		if(str.length()%3!=0){
			plus=(3-str.length()%3)+1;
		}
		for(int k=0; k<bl/6+plus; k++){
			String temp = binaryStr.substring(k*6);
			if(!"".equals(temp)){
				if(temp.length()<6){
					for(int s=0; s<6-temp.length(); s++){
						binaryStr+="0";
					}
				}
				int num = Integer.valueOf(binaryStr.substring(k*6, (k+1)*6),2);
				r+=c.substring(num,num+1);
			}
			else
			{
				binaryStr+="000000";
				r+="=";
			}
			
		}
		return r;
	}
	
	
	public static String decode(String str){
		String binaryStr = "";
		for(int i = 0; i<str.length(); i++){
			if(str.charAt(i)!='='){
				int t = c.indexOf(str.charAt(i));
				String s = Integer.toBinaryString(t);
				if(s.length()<6){
					int l = s.length();
					for(int m=0; m<6-l; m++){
						s="0"+s;
					}
				}
				binaryStr+=s;
			}
		}
		int plus=0;
		String result = "";
		if(binaryStr.length()%8!=0){
			plus=1;
		}
		int l = binaryStr.length()/8+plus;
		for(int j=0; j<l; j++){
			if(j==l-1){
				String b = binaryStr.substring(j*8);
				if(b.contains("1")){
					for(int x=0; x<8-b.length(); x++){
						binaryStr+="0";
					}
				}
				else{
					break;
				}
				
			}
			result+=(char)Integer.valueOf(binaryStr.substring(j*8, (j+1)*8),2).byteValue();
		}
		
		return result;
	}
	
}
