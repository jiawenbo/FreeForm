package com.jeff.common.persistence.page;

public class Order {
	private Integer column;
	private String dir;
	private String name;
	public Order(){}
	public Order(Integer column, String dir){
		this.column = column;
		this.dir = dir;
	}
	public Integer getColumn() {
		return column;
	}
	public void setColumn(Integer column) {
		this.column = column;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
