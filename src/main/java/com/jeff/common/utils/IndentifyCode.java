package com.jeff.common.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;

public class IndentifyCode {
	// width and height of the picture
	private int width = 150;
	private int height = 50;
	private String resultString = "";
	private Random random  = new Random();
	
	public IndentifyCode(){
		
	}
	
	//mark random colors to render font,line, background
	public Color getRandomColor(int fc, int bc){
		if(fc>200){
			fc = 200;
		}
		if(fc<0){
			fc = 0;
		}
		if(bc>255){
			bc = 255;
		}
		if(bc<0){
			bc = 0;
		}
		int r = fc+random.nextInt(bc-fc);
		int g = fc+random.nextInt(bc-fc);
		int b = fc+random.nextInt(bc-fc);
		return new Color(r,g,b);	
	}
	
	//mark random lines
	
	public void drawRandomLines(Graphics2D g, int nums){
		g.setColor(this.getRandomColor(160, 200));
		for(int i = 0; i < nums; i++){
			int x1 = random.nextInt(width);
			int y1 = random.nextInt(height);
			int x2 = random.nextInt(12);
			int y2 = random.nextInt(40);
			g.drawLine(x1, y1, x2, y2);
		}
	}
	
	
	//mark random character includes numbers or letters
	
	
	public String drawRandomString(int length, Graphics2D g){
		StringBuffer strbuf = new StringBuffer();
		String result ="";
		int temp = 0;
		for(int i = 0; i < length; i++){
			switch (random.nextInt(4)) {
			case 0:
				temp = random.nextInt(26)+65;
				result=String.valueOf((char)temp);
				break;
			case 1:
				temp = random.nextInt(26)+97;
				result=String.valueOf((char)temp);
				break;
			default:
				temp = random.nextInt(10)+48;
				result=String.valueOf((char)temp);
				break;
			}
			Color color = new Color(20+random.nextInt(20),20+random.nextInt(20),20+random.nextInt(20));
			g.setColor(color);
			
			AffineTransform trans = new AffineTransform();
			trans.rotate(random.nextInt(20)*3.14/180,30*i+5,25);
			
			float scacleSize = random.nextFloat() + 0.8f;
			if(scacleSize>1f){
				scacleSize=1f;
			}
			
			trans.scale(scacleSize, scacleSize);
			g.setTransform(trans);
			g.drawString(result, 30*i+24, 38);
			
			strbuf.append(result);
		}
		
		g.dispose();
		resultString = strbuf.toString();
		return resultString;
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public void setWidth(int w){
		this.width = w;
	}
	
	public int getHeight(){
		return this.height;
	}
	
	public void setHeight(int h){
		this.height = h;
	}
	
	
	public String getResultString(){
		return this.resultString;
	}
	
}
