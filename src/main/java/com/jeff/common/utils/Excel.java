package com.jeff.common.utils;
/**
 * 
 * @name HeadCell.java
 * @author jeffwcx
 * @description Excel表信息
 */
public class Excel {
	
	/**
	 * Excel中的标题
	 */
	private String title;
	
	/**
	 * Excel表格的表头值
	 */
	private String[] cellValues;
	/**
	 * Excel表格的各个各自宽度
	 */
	private int[] cellWidths;
	
	
	public String getTitle() {
		return title;
	}
	public String[] getCellValues() {
		return cellValues;
	}
	public int[] getCellWidths() {
		return cellWidths;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setCellValues(String[] cellValues) {
		this.cellValues = cellValues;
	}
	public void setCellWidths(int[] cellWidths) {
		this.cellWidths = cellWidths;
	}
	
}
