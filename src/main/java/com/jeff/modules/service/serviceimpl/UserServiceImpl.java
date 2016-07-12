package com.jeff.modules.service.serviceimpl;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeff.modules.dao.UserDao;
import com.jeff.modules.entity.User;
import com.jeff.modules.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	

	@Override
	public Map<String, Object> loginIdentify(String mail,String password) {
		User user = userDao.getByParamNamesAndValues(new String[]{"mail"}, new Object[]{mail});
		Map<String,Object> map = new HashMap<String, Object>();
		if(user!=null){
			if(!user.getPassword().equals(password)){
				map.put("message", false);
				map.put("reason", "Password error");
			}
			else
			{
				map.put("message", true);
				//userDao.updateByHql("update User set online=true where id="+user.getId());
				map.put("user", user);
			}
		}
		else
		{
			map.put("message", false);
			map.put("reason", "Mail error");
		}
		return map;
	}
	
	@Override
	public String register(String mail,String password){
		User user = userDao.getByParamNameAndValue("mail", mail);
		if(user!=null){
			return "User already exists";
		}
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");  
        Date date = new Date();  
        String registerTime= f.format(date);
		user = new User();
		user.setRegisterTime(registerTime);
		user.setMail(mail);
		user.setPassword(password);
		userDao.save(user);
		return "ok";
		
	}
	
	@Override
	public void checkUserInfo(String id){
		User user = userDao.getByParamNameAndValue("id", id);
		user.getHeadPic();
		user.getMail();
		user.getRegisterTime();
		user.getUserName();
		
	}
	
	@Override
	public void modifyUserInfo(String id,String headPic,String password){
		User user = userDao.getByParamNameAndValue("id", id);
		user.setHeadPic(headPic);
		user.setPassword(password);	
		userDao.update(user);
	}
}