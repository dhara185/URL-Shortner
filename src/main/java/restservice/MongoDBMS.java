package restservice;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class MongoDBMS {

	private DB db;

	private void getConnection() {
		MongoClient mongoClient = new MongoClient();
		this.db = mongoClient.getDB("urlshortnerdb");
	}

	public String getHash(String url) {
		getConnection();
		DBCollection table = db.getCollection("urlmaps");

		BasicDBObject query = new BasicDBObject();
		query.put("url", url);
		DBCursor cursor = table.find(query);

		while (cursor.hasNext()) {
			BasicDBObject obj = (BasicDBObject) cursor.next();
			return obj.getString("hashUrl");
		}
		return null;
	}

	public void setHash(String url, String hashUrl) {
		getConnection();
		DBCollection table = db.getCollection("urlmaps");

		BasicDBObject document = new BasicDBObject();
		document.put("hashUrl", hashUrl);
		document.put("url", url);
		table.insert(document);
	}

	public String getURL(String hash) {
		getConnection();
		DBCollection table = db.getCollection("urlmaps");

		BasicDBObject query = new BasicDBObject();
		query.put("hashUrl", hash);
		DBCursor cursor = table.find(query);

		while (cursor.hasNext()) {
			BasicDBObject obj = (BasicDBObject) cursor.next();
			return obj.getString("url");
		}
		return null;
	}

}
