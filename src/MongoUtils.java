
package com.mkyong.core;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.ConnectionString;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

public class MongoUtils {

	public class DataBaseObj {
		public MongoClient mongoClient;
		public MongoDatabase database;
		public MongoCollection<Document> collection;
		
		/** 
		 * Connection methods
		 * 
		 */	

		
		public boolean Connect(String host) { //only name/address
			try {
				MongoClient mClient = new MongoClient(host);
				self.mongoClient = mClient;
				return true;
			} catch (UnknownHostException e) {
				e.printStackTrace();
				return false;
		    } catch (MongoException e) {
		    	e.printStackTrace();
		    	return false;
		    }
		}
		
		public boolean Connect(String host, int socket) { // name/ address and port
			try {
				MongoClient mClient = new MongoClient(host, socket);
				self.mongoClient =  mClient;
				return true;
			} catch (UnknownHostException e) {
				e.printStackTrace();
		    	return false;
		    } catch (MongoException e) {
		    	e.printStackTrace();
		    	return false;
		    }
		}
	
		
		public boolean Connect(String host, int socket, boolean sslUse) { // name/ address and port, Java >=7, problem: "javax.net.ssl.trustStore: The path to a trust store containing the certificate of the signing authority"
				String connectionURIString = "mongodb://" + host + ":" + socket + "/?streamType=netty&ssl="; // may be used later for easy extraction of info
				if (sslUse) {
					connectionURIString += "true";
				} else {
					connectionURIString += "false";
				}
				MongoClientURI connectionString = new MongoClientURI(connectionURIString);
				
				try {
					MongoClient mClient = new MongoClient(connectionString);
					self.mongoClient = mClient;
					return true;
				} catch (UnknownHostException e) {
					e.printStackTrace();
			    	return false;
			    } catch (MongoException e) {
			    	e.printStackTrace();
			    	return false;
			    }
		}	
		
		public boolean Connect(String host, int socket, String username, String password, String dbName, boolean sslUse) { // name/ address and port, Java >=7, problem: "javax.net.ssl.trustStore: The path to a trust store containing the certificate of the signing authority"
			String connectionURIString = "mongodb://" + username + ":" + password + "@" + host + ":" + socket + "/?streamType=netty&authSource=" + dbName + "&ssl="; // may be used later for easy extraction of info
			if (sslUse) {
				connectionURIString += "true";
			} else {
				connectionURIString += "false";
			}
			MongoClientURI connectionString = new MongoClientURI(connectionURIString);
			
			try {
				MongoClient mClient = new MongoClient(connectionString);
				self.mongoClient = mClient;
				return true;
			} catch (UnknownHostException e) {
				e.printStackTrace();
		    	return false;
		    } catch (MongoException e) {
		    	e.printStackTrace();
		    	return false;
		    }
		}		

		
		/** 
		 * Accessing database
		 * 
		 */	
		
		public List<String> getDatabaseNames(){ //databases on server
		    List<String> dbs;
		    try {
				if (this.mongoClient != null) {
					MongoCursor<String> dbsCursor = this.MongoClient.listDatabaseNames().iterator();
				    while(dbsCursor.hasNext()) {
				        dbs.add(dbsCursor.next());
				    }
				}	
	
				return dbs;
		    }  catch (MongoException e) {
		    	e.printStackTrace();
		    }
		}
		
		public boolean accessDB (String dbName) { //gets database objetc
			try {
				if (this.mongoClient != null) {
					this.database = mongoClient.getDatabase(dbname);
					return true;
				}
				return false;

			}  catch (MongoException e) {
		    	e.printStackTrace();
		    	return false;
		    }
		}
		
		/** 
		 * Accessing or creating a collection
		 * 
		 */	
		
		public List<String> getCollectionNames() {
			List<String> collectionNames;
			try {
				if (this.database != null) {
					for (String name : this.database.listCollectionNames()) {
					    collectionNames.add(name);
					}
				}
			}  catch (MongoException e) {
		    	e.printStackTrace();
		    	return collectionNames;
		    }
			return collectionNames;
		}
		
		public boolean accessCollection (String colName) { // gets collection object
			try {
				if (this.database != null) {
					this.collection = this.database.getCollection(colName);
					return true;
				}
				return false;
			}  catch (MongoException e) {
		    	e.printStackTrace();
		    	return false;
		    }
		}
		
		
	}
}
