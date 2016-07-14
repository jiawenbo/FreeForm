package com.jeff.common.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoDBDao {
	private static MongoClient instance = null;
	private static MongoDatabase mongoDatabase = null;
	/*private static String ip = "123.206.209.13";
	private static Integer port = 27017;
	private static String username = "jeffwcx";
	private static String password = "jeffwcx";*/
	private static String database = "freeform";
	
	{
		/*ServerAddress serverAddress = new ServerAddress(ip, port);
		MongoCredential credential = MongoCredential
				.createScramSha1Credential(username, database, password.toCharArray());
		List<MongoCredential> credentials = new ArrayList<MongoCredential>();
		credentials.add(credential);*/
		instance = new MongoClient( "localhost" , 27017 );
		mongoDatabase = instance.getDatabase(database); 
//		instance = new MongoClient(serverAddress, credentials);
	}
	
	public static MongoClient getClient() {
		if(instance==null) {
			instance = new MongoClient( "localhost" , 27017 );
			mongoDatabase = instance.getDatabase(database); 
		}
		return instance;
	}
	
	@SuppressWarnings("unused")
	private MongoDatabase getDatabase(final String dataBaseName) {
		return instance.getDatabase(dataBaseName);
	}
	
	public static void main(String[] args) {
		MongoCollection<Document> collection = MongoDBDao.getClient().getDatabase("freeform").getCollection("form");
		System.out.println("ok");
		FindIterable<Document> findIterable = collection.find();  
        MongoCursor<Document> mongoCursor = findIterable.iterator();  
        while(mongoCursor.hasNext()){  
           System.out.println(mongoCursor.next());  
        }  
	}
	
}
 