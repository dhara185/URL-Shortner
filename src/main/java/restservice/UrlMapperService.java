package restservice;

import java.util.Random;

public class UrlMapperService {

	MongoDBMS oMongoDB = new MongoDBMS();
	RedisDBMS oRedisDB = new RedisDBMS();

	public static String generateHash(int length) {
		char[] chArr = new char[length];
		Random rn = new Random();
		String strArr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";

		for (int i = 0; i < length; i++) {
			int index = rn.nextInt(strArr.length());
			chArr[i] = strArr.charAt(index);
		}

		return new String(chArr);
	}

	public String getHash(String url) {
		// check if exist in RedisDB
		String hashUrl = oRedisDB.getHash(url);
		if (hashUrl != null) {
			return hashUrl;
		}

		// check if exist in MongoDB
		hashUrl = oMongoDB.getHash(url);
		if (hashUrl != null) {
			oRedisDB.setHash(url, hashUrl);
			return hashUrl;
		}

		hashUrl = generateHash(10);
		oRedisDB.setHash(url, hashUrl);
		oMongoDB.setHash(url, hashUrl);

		return hashUrl;
	}

	public String getURL(String hash) {

		// get from RedisDB
		String url = oRedisDB.getURL(hash);
		if (url != null) {
			return url;
		}

		// If not exist in Redis then get from MongoDB
		url = oMongoDB.getHash(hash);
		if (url != null) {
			return url;
		}

		return "No URL for Hash";
	}

}
