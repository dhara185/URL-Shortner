package restservice;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDBMS {

	private Jedis jedis;

	private void getConnection() {
		JedisPool pool = new JedisPool("localhost", 6379);
		this.jedis = pool.getResource();
	}

	public String getHash(String url) {
		getConnection();
		Map<String, String> retrieveMap = this.jedis.hgetAll("urlMAP");
		for (String keyMap : retrieveMap.keySet()) {
			String hashMapURL = retrieveMap.get(keyMap);
			if (url.equals(hashMapURL)) {
				return keyMap;
			}
		}
		return null;
	}

	public void setHash(String url, String hashUrl) {
		getConnection();
		Map<String, String> map = new HashMap<>();
		map.put(hashUrl, url);
		this.jedis.hmset("urlMAP", map);
	}

	public String getURL(String hash) {
		getConnection();
		Map<String, String> retrieveMap = jedis.hgetAll("urlMAP");
		for (String keyMap : retrieveMap.keySet()) {
			if (hash.equals(keyMap)) {
				return retrieveMap.get(keyMap);
			}
		}
		return null;
	}

}
