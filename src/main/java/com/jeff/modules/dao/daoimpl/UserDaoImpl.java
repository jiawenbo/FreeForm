package com.jeff.modules.dao.daoimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.jeff.common.persistence.BaseDao;
import com.jeff.common.persistence.BaseDaoImpl;
import com.jeff.modules.dao.UserDao;
import com.jeff.modules.entity.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{
	@Autowired
	@Qualifier("baseDao")
	private BaseDao baseDao;

}