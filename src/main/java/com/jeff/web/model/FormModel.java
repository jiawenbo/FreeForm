package com.jeff.web.model;

import java.util.List;

public class FormModel{
	private Integer index;
	private List<Object> content;
	private String name;
	private String css;
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public List<Object> getContent() {
		return content;
	}
	public void setContent(List<Object> content) {
		this.content = content;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCss() {
		return css;
	}
	public void setCss(String css) {
		this.css = css;
	}
	
}
