package feature;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import request.GraphAPIRequest;

public class SyncToAirtable {
	
	public static void main(String args[]) {
		
		
		SyncToAirtable app = new SyncToAirtable();
		app.syncMembers();
		app.syncChannels();
		System.out.print("Finish!");
	}

	private void syncChannels() {
		// TODO Auto-generated method stub
		
	}

	private void syncMembers() {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest getRequest = new GraphAPIRequest().getRequest();
		try {
			HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
			// Print response status code and body
            System.out.println("Response Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();	
		} 
		
		
		
		
		
	}

}
