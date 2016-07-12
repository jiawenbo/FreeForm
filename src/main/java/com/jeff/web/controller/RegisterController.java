package com.jeff.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.jeff.modules.service.UserService;
import com.jeff.web.model.UserValidate;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

@Controller
public class RegisterController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/register")
	public String toRegister(){
		return "register";
	}
	
	@RequestMapping(value="/register/identify",method=RequestMethod.POST)
	@ResponseBody
	public String register(@RequestParam String mail,@RequestParam String password,
			@Valid @ModelAttribute("userValidate") UserValidate userValidate,
			BindingResult result)
	{
		if(result.hasErrors())
		{
			List<ObjectError> list = result.getAllErrors();
			
			return list.get(0).getDefaultMessage().toString();
		}
			else {
		String msg = userService.register(mail, password);
		if(msg.equals("ok"))
		{
			if(result.hasErrors())
			{
				return "Validate error";
			}
			else
				return "Register success";
		}
		else 
			return msg;
	}
	}
}
