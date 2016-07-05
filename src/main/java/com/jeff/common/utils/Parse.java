package com.jeff.common.utils;

public class Parse {
	public Parse(){}
	
	public static String parseBytesToHexStr(byte[] b){
		String result = "";
		for(int i = 0; i < b.length; i++){
			int x = b[i]&0xFF;
			if(x<16){
				result+="0";
			}
			result+=Integer.toString(x, 16).toUpperCase();
		}
		return result;
	}
	
	public static byte[] parseHexStrToBytes(String hexStr){
		int l = hexStr.length();
		if(l<2||l%2!=0){
			return null;
		}
		byte[] result = new byte[l/2];
		for(int i=0; i<l/2; i++){
			int f = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
			int s = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
			result[i] = (byte)(f*16+s);
		}
		return result;
	}
}
