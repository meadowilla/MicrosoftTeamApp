package request;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;

public class AirtableAPIRequest extends Request{
	
	private final String BASE_ID = "appXDA4TnCSLcxrFd";
	private final String TABLE_ID = "tblHuJ78eV9N6KrdH";
	private final String API_KEY = "pata7gpoK9I7TewmW.49814cc55ff1155354cb30fb6725f677d5e2a3ff841f1b90ea122b751df195e0";
	private final String URL = "https://api.airtable.com/v0/" + BASE_ID + "/" + TABLE_ID;

	@Override
	public HttpRequest postRequest(String bodyString) {
		BodyPublisher requestBody = BodyPublishers.ofString(bodyString);
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(URL)) // to be modified
				.POST(requestBody)
				.header("Authorization", "Bearer " + API_KEY)
				.header("Content-Type", "application/json")
				.build();
		return request;
	}

	@Override
	public HttpRequest getRequest() {		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(URL))
				.GET()
				.header("Authorization", "Bearer " + API_KEY)
				.header("Content-Type", "application/json")
				.build();
		return request;
	}

}
