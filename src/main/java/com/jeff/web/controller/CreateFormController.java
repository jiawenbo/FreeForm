package com.jeff.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeff.modules.entity.User;
import com.jeff.modules.service.FormService;


@Controller
public class CreateFormController {
	@Autowired
	private FormService formService;
	
	
	@RequestMapping("/createform")
	public String createForm(){
		return "create-form";
	};
	
	
	@RequestMapping(value="/getFormlist", method=RequestMethod.GET)
	@ResponseBody
	public Object getFormList(HttpSession session) {
		User user = (User) session.getAttribute("user");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", formService.getFormList(user.getId()));
		return map;
	}
	
	
	@RequestMapping(value="/deleteForm", method=RequestMethod.POST)
	@ResponseBody
	public Object deleteFormItem(@RequestParam String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		formService.deleteForm(id);
		map.put("message", "ok");
		return map;
	}
	
	@RequestMapping(value="/createForm", method=RequestMethod.GET)
	@ResponseBody
	public Object createForm(HttpSession session) {
		User user = (User) session.getAttribute("user");
		Map<String, Object> map = new HashMap<String, Object>();
		String id = formService.addForm(user.getId());
		map.put("message", "ok");
		map.put("id", id);
		return map;
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String toEdit(){
		return "edit";
	}
	
	@RequestMapping(value="/getformcontent", method = RequestMethod.POST)
	@ResponseBody
	public Object getFormContent(String formId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Object o = formService.getFormContent(formId);
		map.put("content", o);
		return map;
	}
	
	
	@RequestMapping(value="/saveform", method = RequestMethod.POST)
	@ResponseBody
	public Object saveForm(String data, String id, String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(null!=data&&null!=id&&null!=name) {
			formService.saveForm(data, id, name);
			map.put("message", "ok");
		} else {
			map.put("message", "error");
		}
		return map;
	}
	
	@RequestMapping(value="/publish",method = RequestMethod.POST)
	@ResponseBody
	public Object publish(String id, HttpServletRequest req) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(id!=null) {
			formService.publishForm(id, true);
			map.put("message", "ok");
			map.put("url", getRootUrl(req)+"form?id="+id);
		} else {
			map.put("message", "error");
		}
		return map;
	}
	
	@RequestMapping(value="/form",method = RequestMethod.GET)
	public String form(@RequestParam String id){
		if(formService.isPublish(id)) {
			return "form";
		}
		return "404";
	};
	
	
	@RequestMapping(value="/submitdata",method = RequestMethod.POST)
	@ResponseBody
	public Object submitData(String id, String data) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(null!=data&&null!=id) {
			formService.submitData(id, data);
			map.put("message", "ok");
		} else {
			map.put("message", "error");
		}
		return map;
	}
	
	@RequestMapping("/formdata")
	public String formDataPage(){
		return "form-data";
	}
	
	
	@RequestMapping(value="/lookdata",method = RequestMethod.GET)
	@ResponseBody
	public Object lookData(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		List list = new ArrayList<>();
		if(null!=id) {
			list = formService.lookData(id);
			map.put("message", "ok");
			map.put("content", list);
		} else {
			map.put("message", "error");
		}
		return map;
	}
	
	
	private static String getRootUrl(HttpServletRequest req){
		return req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+req.getContextPath()+"/";
	}
	
	
	
}
