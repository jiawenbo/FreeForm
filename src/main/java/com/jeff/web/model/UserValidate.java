package com.jeff.web.model;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

public class UserValidate {
	
	private String mail;
	private String password;
	
	
	@NotEmpty(message="{mail.empty.error}")
	//@Email(message = "{mail.name.error}")
	@Pattern(regexp = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$", message = "{mail.name.error}")
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	@NotEmpty(message="{password.empty.error}")
	@Length(min=6,max=20,message="{password.length.error}")
	@Pattern(regexp = "^(?!([a-zA-Z]+|\\d+)$)[a-zA-Z\\d]{6,20}$", message = "{password.content.error}")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
