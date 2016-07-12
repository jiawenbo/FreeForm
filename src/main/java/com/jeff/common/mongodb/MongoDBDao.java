package com.jeff.common.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

public class MongoDBDao {
	private static MongoClient instance = null;
	private static String ip = "123.206.209.13";
	private static Integer port = 27017;
	private static String username = "jeffwcx";
	private static String password = "jeffwcx";
	private static String database = "freeform";
	
	{
		ServerAddress serverAddress = new ServerAddress(ip, port);
		MongoCredential credential = MongoCredential
				.createScramSha1Credential(username, database, password.toCharArray());
		List<MongoCredential> credentials = new ArrayList<MongoCredential>();
		credentials.add(credential);
		instance = new MongoClient(serverAddress, credentials);
	}
	
	public static MongoClient getClient() {
		if(instance==null) {
			ServerAddress serverAddress = new ServerAddress(ip, port);
			MongoCredential credential = MongoCredential
					.createScramSha1Credential(username, database, password.toCharArray());
			List<MongoCredential> credentials = new ArrayList<MongoCredential>();
			credentials.add(credential);
			instance = new MongoClient(serverAddress, credentials);
		}
		return instance;
	}
	
	@SuppressWarnings("unused")
	private MongoDatabase getDatabase(final String dataBaseName) {
		return instance.getDatabase(dataBaseName);
	}
	
}
 