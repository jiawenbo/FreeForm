package com.jeff.common.utils;

import java.util.List;

public class Pdf {
	private String title;
	private String[] header;
	private List<Object[]> data;
	
	public Pdf(){}
	
	public Pdf(String title, String[] header, List<Object[]> data){
		this.title = title;
		this.header = header;
		this.data = data;
	}
	
	public String getTitle() {
		return title;
	}
	public String[] getHeader() {
		return header;
	}
	public List<Object[]> getData() {
		return data;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setHeader(String[] header) {
		this.header = header;
	}
	public void setData(List<Object[]> data) {
		this.data = data;
	}
}
