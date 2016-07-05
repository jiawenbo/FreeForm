package com.jeff.common.utils;

import java.util.Random;

public class Rate {
	/**
	 * @author jeffwcx
	 * @method lottery
	 * @param @param rateArr，概率数组，数组每项以%为单位
	 * @param @return 得到中奖数组项的下标
	 * @return int
	 * @description  随机概率函数
	 * @date 2016年2月25日 下午7:24:22
	 */
	public static int lottery(int [] rateArr){
		Random random = new Random();
		int val = random.nextInt(100)+1;
		int ok=-1;
		for(int i=0; i<rateArr.length; i++){
			int start = 0;
			for(int j=0; j<=i-1; j++){
				start+=rateArr[j];
			}
			if(i==0){
				start=1;
			}
			else
			{
				start++;
			}
			int end = start+rateArr[i]-1;
			int[] nowRange = {start,end};
			if(val>=nowRange[0]&&val<=nowRange[1]){
				ok=i;
				break;
			}
		}
		return ok;
	}
}
