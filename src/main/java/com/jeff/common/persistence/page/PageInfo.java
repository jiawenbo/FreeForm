package com.jeff.common.persistence.page;

import java.util.List;

/**
 * 
 * @name PageInfo.java
 * @author jeffwcx
 * @description 从数据库中得到的一页信息
 */
public class PageInfo<T> {
	//请求计时器
	private Integer draw;
	//未经过滤的总记录数
	private Integer recordsTotal;
	//过滤后的总记录数
	private Integer recordsFiltered;
	//得到的数据
	private List<T> data;
	//可选，错误信息
	private String error;
	
	public PageInfo(){
		
	}
	
	public PageInfo(Integer draw, Integer recordsTotal, Integer recordsFiltered, List<T> data, String error){
		this.draw = draw;
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsFiltered;
		this.data = data;
		this.error = error;
	}
	
	public Integer getDraw() {
		return draw;
	}
	public void setDraw(Integer draw) {
		this.draw = draw;
	}
	public Integer getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> list) {
		this.data = list;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
}
