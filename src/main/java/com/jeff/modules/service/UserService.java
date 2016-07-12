package com.jeff.modules.service;


import java.util.Map;

import com.jeff.modules.entity.User;

public interface UserService {

	

	Map<String, Object> loginIdentify(String mail, String password);

	String register(String mail, String password);

	void checkUserInfo(String id);

	void modifyUserInfo(String id, String headPic, String password);
}