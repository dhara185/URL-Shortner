package restservice;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoDBMS {

	private MongoDatabase db;

	private void getConnection() {
		MongoClient mongoClient = new MongoClient();
		this.db = mongoClient.getDatabase("urlshortnerdb");
	}

	public String getHash(String url) {
		getConnection();
		MongoCollection<Document> table = db.getCollection("urlmaps");

		Document query = new Document();
		query.put("url", url);
		MongoCursor<Document> cursor = table.find(query).iterator();

		while (cursor.hasNext()) {
			Document obj = (Document) cursor.next();
			return obj.getString("hashUrl");
		}
		return null;
	}

	public void setHash(String url, String hashUrl) {
		getConnection();
		MongoCollection<Document> table = db.getCollection("urlmaps");

		Document document = new Document();
		document.put("hashUrl", hashUrl);
		document.put("url", url);
		table.insertOne(document);
	}

	public String getURL(String hash) {
		getConnection();
		MongoCollection<Document> table = db.getCollection("urlmaps");

		BasicDBObject query = new BasicDBObject();
		query.put("hashUrl", hash);
		MongoCursor<Document> cursor = table.find(query).iterator();

		while (cursor.hasNext()) {
			Document obj = cursor.next();
			return obj.getString("url");
		}
		return null;
	}

}
