package com.jeff.modules.dao.daoimpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Qualifier;
import com.jeff.common.persistence.BaseDao;
import com.jeff.common.persistence.BaseDaoImpl;
import com.jeff.modules.dao.FormDao;
import com.jeff.modules.entity.Form;

@Repository("formDao")
public class FormDaoImpl extends BaseDaoImpl<Form> implements FormDao{
	@Autowired
	@Qualifier("baseDao")
	private BaseDao baseDao;

}