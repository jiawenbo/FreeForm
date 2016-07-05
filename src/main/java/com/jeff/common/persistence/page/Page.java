package com.jeff.common.persistence.page;

import java.util.List;

public class Page {
	//请求次数计时数
	private Integer draw;
	//第一条数据起始位置
	private Integer start;
	//每页显示条数
	private Integer length;
	//全局搜索条件
	private String searchValue;
	//全局搜索是否按正则表达式来处理
	private Boolean searchRegex;
	//排序规则（哪行需要排序，以及排序规则是怎么样的）
	private Order order;
	private List<String> tables;
	//全局搜索规则
	private List<Column> columns;
	
	public Page(){
		
	}
	
	public Page(Integer draw, Integer start, Integer length, String searchValue, Boolean searchRegex,List<Column> columns,Order order,List<String> tables){
		this.draw = draw;
		this.start = start;
		this.length = length;
		this.searchValue = searchValue;
		this.searchRegex = searchRegex;
		this.columns = columns;
		this.order = order;
		this.tables = tables;
		
	}
	
	public Integer getDraw() {
		return draw;
	}
	public void setDraw(Integer draw) {
		this.draw = draw;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public Boolean getSearchRegex() {
		return searchRegex;
	}
	public void setSearchRegex(Boolean searchRegex) {
		this.searchRegex = searchRegex;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<String> getTables() {
		return tables;
	}

	public void setTables(List<String> tables) {
		this.tables = tables;
	}
	
}
