package com.jeff.modules.service;


import java.util.List;
import java.util.Map;

import com.jeff.modules.entity.Form;

public interface FormService {
	List<Form> getFormList(String userId);
	void deleteForm(String id);
	String addForm(String userId);
	Object getFormContent(String formId);
	void saveForm(String data, String id, String name);
	void publishForm(String id, Boolean isPublish);
	Boolean isPublish(String id);
	void submitData(String id, String data);
	List<String> lookData(String id);
	
}