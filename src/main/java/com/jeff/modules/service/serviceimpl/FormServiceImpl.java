package com.jeff.modules.service.serviceimpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeff.modules.dao.FormDao;
import com.jeff.modules.entity.Form;
import com.jeff.modules.service.FormService;

@Service("formService")
public class FormServiceImpl implements FormService {
	@Autowired
	private FormDao formDao;

}