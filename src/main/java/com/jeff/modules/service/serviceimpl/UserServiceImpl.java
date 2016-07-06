package com.jeff.modules.service.serviceimpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jeff.modules.dao.UserDao;
import com.jeff.modules.entity.User;
import com.jeff.modules.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

}