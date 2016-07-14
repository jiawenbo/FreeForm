package com.jeff.modules.service.serviceimpl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeff.common.mongodb.MongoDBDao;
import com.jeff.modules.dao.FormDao;
import com.jeff.modules.entity.Form;
import com.jeff.modules.service.FormService;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

@Service("formService")
public class FormServiceImpl implements FormService {
	@Autowired
	private FormDao formDao;

	@Override
	public List<Form> getFormList(String userId) {
		return formDao.listByNameParamAndValue("userId", userId);
	}

	@Override
	public void deleteForm(String id) {
		formDao.deleteById(id);
		MongoCollection<Document> collection = MongoDBDao.getClient().getDatabase("freeform").getCollection("form");
		collection.deleteOne(Filters.eq("formid", id));
	}

	@Override
	public String addForm(String userId) {
		Form form = new Form();
		form.setUserId(userId);
		form.setName("表单名称");
		form.setIsPublish(false);
		formDao.save(form);
		MongoCollection<Document> collection = MongoDBDao.getClient().getDatabase("freeform").getCollection("form");
		Document document = new Document("formid", form.getId());
		collection.insertOne(document);
		return form.getId();
	}

	@Override
	public Object getFormContent(String formId) {
		Object document = null;
		MongoCollection<Document> collection = MongoDBDao.getClient().getDatabase("freeform").getCollection("form");
		FindIterable<Document> findIterable = collection.find(Filters.eq("formid", formId));
		MongoCursor<Document> mongoCursor = findIterable.iterator();  
		while(mongoCursor.hasNext()){  
           document = mongoCursor.next().get("formcontent");
           break;
         }  
		return document;
	}

	@Override
	public void saveForm(String data, String id, String name) {
		formDao.updateByHql("update Form set name='"+name+"' where id='"+id+"'");
		MongoCollection<Document> collection = MongoDBDao.getClient().getDatabase("freeform").getCollection("form");
		collection.updateOne(Filters.eq("formid", id), new Document("$set", new Document("formcontent", data)));
	}
	
	@Override
	public void publishForm(String id, Boolean isPublish) {
		formDao.updateByHql("update Form set isPublish="+isPublish+" where id='"+id+"'");
	}

	@Override
	public Boolean isPublish(String id) {
		Form form = formDao.getById(id);
		return form.getIsPublish();
	}

	@Override
	public void submitData(String id, String data) {
		MongoCollection<Document> collection = MongoDBDao.getClient().getDatabase("freeform").getCollection("formdata");
		Document document = new Document("formid", id);
		document.append("formdata", data);
		collection.insertOne(document);
	}
	@Override
	public List<String> lookData(String id) {
		List<String> result = new ArrayList<>();
		MongoCollection<Document> collection = MongoDBDao.getClient().getDatabase("freeform").getCollection("formdata");
		FindIterable<Document> findIterable = collection.find(Filters.eq("formid", id));
		MongoCursor<Document> mongoCursor = findIterable.iterator(); 
		while(mongoCursor.hasNext()){  
            result.add(mongoCursor.next().get("formdata").toString());  
        }
		
		return result;
	}
	

}