import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.ConnectionString;

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
import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

public class MongoUtils {
// Connection functions	
	public static MongoClient Connect(String host) { //only name/address
		MongoClient mongoClient = new MongoClient(host);
		return mongoClient;
	}
	
	public static MongoClient Connect(String host, int socket) { // name/ address and port
		MongoClient mongoClient = new MongoClient(host, socket);
		return mongoClient;
	}

	
	public static MongoClient Connect(String host, int socket, boolean sslUse) { // name/ address and port, Java >=7, problem: "javax.net.ssl.trustStore: The path to a trust store containing the certificate of the signing authority"
		String connectionURIString = "mongodb://" + host + ":" + socket + "/?streamType=netty&ssl="; // may be used later for easy extraction of info
		if (sslUse) {
			connectionURIString += "true";
		} else {
			connectionURIString += "false";
		}
		ConnectionString connectionString = new ConnectionString(connectionURIString);
		
		MongoClient mongoClient = new MongoClient(connectionString.toString());
		return mongoClient;
	}

}
