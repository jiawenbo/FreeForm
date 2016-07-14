package com.jeff.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {
	
	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	@ResponseBody
	public Object uploadFile(@RequestParam("file") MultipartFile file, HttpSession session,HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		if(!file.isEmpty()&&null!=file){
			String fileName ="file"+System.currentTimeMillis()+"."+getFileMIMEName(file.getOriginalFilename());
			File root = new File(getRootUri(session)+"resources\\file\\");
			if(!root.exists()) {
				root.mkdirs();
			}
			File f = null;
			try {
				f = new File(root.getCanonicalFile()+"\\"+fileName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(f));
				stream.write(bytes);
				stream.flush();
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			map.put("url", getRootUrl(request)+"resources/file/"+fileName);
		}
		return map;
	}
	
	
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Object upload(@RequestParam("image") MultipartFile file, HttpSession session,HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		if(!file.isEmpty()&&null!=file){
			String fileName ="image"+System.currentTimeMillis()+"."+getFileMIMEName(file.getOriginalFilename());
			File root = new File(getRootUri(session)+"resources\\file\\");
			if(!root.exists()) {
				root.mkdirs();
			}
			File f = null;
			try {
				f = new File(root.getCanonicalFile()+"\\"+fileName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(f));
				stream.write(bytes);
				stream.flush();
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			map.put("url", getRootUrl(request)+"resources/file/"+fileName);
		}
		return map;
	}
	private static String getRootUri(HttpSession session){
		return session.getServletContext().getRealPath("/");
	}
	
	private static String getFileMIMEName(String allName){
		String[] names = allName.split("\\.");
		return names[names.length-1];
	}
	private static String getRootUrl(HttpServletRequest req){
		return req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+req.getContextPath()+"/";
	}
	
}
