package com.jeff.web.controller;

import java.lang.reflect.Method;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.jeff.modules.entity.User;
import com.jeff.modules.service.UserService;
import com.jeff.web.model.UserValid;
import com.jeff.web.model.UserValidate;

@Controller
public class PersonelCenterController {
	
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/center")
	public String toCenter(){
		return "center";
	}
	
	@RequestMapping(value="user/save",method=RequestMethod.POST)
	@ResponseBody
	public String save(@Valid @ModelAttribute("userValid") UserValid userValid,
			BindingResult result){
		
		if(result.hasErrors())
		{
			List<ObjectError> list = result.getAllErrors();
			
			return list.get(0).getDefaultMessage().toString();
		}
		else {
		
		String headPic=userValid.getHeadPic();
		String password=userValid.getPassword();
		userService.modifyUserInfo(userValid.getId(),headPic,password);
		return "ok";
		}
	}
	
	@RequestMapping(value="user/check",method=RequestMethod.POST)
	@ResponseBody
	public String check(@ModelAttribute User user){
		
		String id=user.getId();
		userService.checkUserInfo(id);
		return "ok";
	}
}
