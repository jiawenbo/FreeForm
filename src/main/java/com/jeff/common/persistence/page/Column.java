package com.jeff.common.persistence.page;

public class Column{
	private String name;
	private String data;
	private Boolean searchable;
	private Boolean orderable;
	private String search;
	private String searchValue;
	private Boolean searchRegex;
	private String order;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
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
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public Boolean getSearchable() {
		return searchable;
	}
	public void setSearchable(Boolean searchable) {
		this.searchable = searchable;
	}
	public Boolean getOrderable() {
		return orderable;
	}
	public void setOrderable(Boolean orderable) {
		this.orderable = orderable;
	}
}

