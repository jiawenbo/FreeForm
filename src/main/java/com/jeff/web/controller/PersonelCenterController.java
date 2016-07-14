package com.jeff.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jeff.modules.entity.User;
import com.jeff.modules.service.UserService;
import com.jeff.web.model.UserValid;

@Controller
public class PersonelCenterController {
	
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/center")
	public String toCenter(HttpSession session){
		User user = (User)session.getAttribute("user");
		ModelAndView mv = new ModelAndView();
		mv.addObject("user", user);
		return "person-center";
	}
	
	@RequestMapping(value="user/save",method=RequestMethod.POST)
	@ResponseBody
	public Object save(@Valid @ModelAttribute("userValid") UserValid userValid,
			BindingResult result, HttpSession session){
		Map<String, String> map = new HashMap<String, String>();
		String msg = null;
		User user = (User)session.getAttribute("user");
		if(result.hasErrors())
		{
			List<ObjectError> list = result.getAllErrors();
			
			msg = list.get(0).getDefaultMessage().toString();
		}
		else {
			String headPic=userValid.getHeadPic();
			String password=userValid.getPassword();
			userService.modifyUserInfo(user.getId(),headPic,password);
			((User)session.getAttribute("user")).setHeadPic(headPic);
			msg = "ok";
		}
		map.put("message", msg);
		return map;
	}
}
