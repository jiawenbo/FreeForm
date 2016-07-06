package com.jeff.common.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

@Component
public class MongoDBDao {
	private static MongoClient instance = null;
	@Value("#{appProperties['mongodb.ip']}")
	private static String ip;
	@Value("#{appProperties['mongodb.port']}")
	private static Integer port;
	@Value("#{appProperties['mongodb.username']}")
	private static String username;
	@Value("#{appProperties['mongodb.password']}")
	private static String password;
	@Value("#{appProperties['mongodb.database']}")
	private static String database;
	
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
 