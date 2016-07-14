package com.jeff.web.model;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class UserValid {
	
	private String password;
	private String HeadPic;
	
	@NotEmpty(message="{password.empty.error}")
	@Pattern(regexp = "^(?!([a-zA-Z]+|\\d+)$)[a-zA-Z\\d]{6,20}$", message = "{password.content.error}")
	@Length(min=6,max=20,message="{password.length.error}")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getHeadPic() {
		return HeadPic;
	}
	public void setHeadPic(String headPic) {
		HeadPic = headPic;
	}
}
