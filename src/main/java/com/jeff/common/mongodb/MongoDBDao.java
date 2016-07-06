package com.jeff.common.mongodb;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

@Component
public class MongoDBDao {
	
	private static MongoClient instance = null;
	@Value("#{appProperties['ip']}")
	private static String ip = "123.206.209.13";
	@Value("#{appProperties['port']}")
	private static Integer port = 27017;
	
	{
		instance = new MongoClient(ip, port);
	}
	
	public static MongoClient getClient() {
		if(instance==null) {
			instance = new MongoClient(ip, port);
		}
		return instance;
	}
	
	@SuppressWarnings("unused")
	private MongoDatabase getDatabase(final String dataBaseName) {
		return instance.getDatabase(dataBaseName);
	}
	
}
 