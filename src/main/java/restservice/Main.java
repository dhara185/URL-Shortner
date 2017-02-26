package restservice;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
	private static UrlMapperService urlMapperService = new UrlMapperService();
	private static ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) {
		port(6789);

		post("/getHash", (request, response) -> {
			UrlMapper oUrlMapper = mapper.readValue(request.body(), UrlMapper.class);
			String hash = urlMapperService.getHash(oUrlMapper.getStrURL());
			return mapper.writeValueAsString(hash);
		});

		post("/getURL", (request, response) -> {
			UrlMapper oUrlMapper = mapper.readValue(request.body(), UrlMapper.class);
			String url = urlMapperService.getURL(oUrlMapper.getHash());
			return mapper.writeValueAsString(url);
		});

		get("/:hash", (request, response) -> {
			String hash = request.params(":hash");
			String url = urlMapperService.getURL(hash);
			System.out.println(url);
			response.redirect("http://"+url,301);
			return url;
		});

		after((request, response) -> response.type("application/json"));
	}

}
