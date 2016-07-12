package com.jeff.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeff.modules.service.UserService;
import com.jeff.web.model.UserValidate;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	public String toLogin(){
		return "login";
	}
	
	@RequestMapping(value="/login/identify",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(
			@Valid @ModelAttribute("userValidate") UserValidate userValidate,
			BindingResult result,HttpServletRequest request)
			{
		String mail=userValidate.getMail();
		String password=userValidate.getPassword();
		String message="";
				Map<String, Object> map = new HashMap<String, Object>();
				if(result.hasErrors())
				{
					List<ObjectError> list = result.getAllErrors();
					
					message=list.get(0).getDefaultMessage().toString();
				}
					else {
				Map<String,Object> m = userService.loginIdentify(mail, password);
				HttpSession session = request.getSession();
				if((boolean) m.get("message"))
				{
					message="OK";
					session.setAttribute("user", m.get("user"));
				}
				else 
				{
					message=(String)m.get("reason");	
				}
				

					}
				map.put("messages",message);
				return map;
				}
}
