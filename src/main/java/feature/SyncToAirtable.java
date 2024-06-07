package feature;

import java.io.IOException;
import java.io.StringWriter;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

import request.AirtableAPIRequest;
import request.GraphAPIRequest;

public class SyncToAirtable {
	
	public static void main(String args[]) {
		Timer timer = new Timer();
		TimerTask syncToAirtable = new TimerTask() {
			@Override
			public void run() {
				SyncToAirtable app = new SyncToAirtable();
				GraphAPIRequest gReq = new GraphAPIRequest();
				AirtableAPIRequest aReq = new AirtableAPIRequest();
				
				// PART 0: enter necessary inputs
				getInput(gReq, aReq);
				
				// MAIN PART: syncMembers (1) and syncChannels (2)
				for (int i = 1; i < 3; i++) {
					gReq.setOption(i);
					aReq.setOption(i);
					app.sync(gReq, aReq);
				}
				
				System.out.print("Finish!");
			}
		};
		
		timer.scheduleAtFixedRate(syncToAirtable, 0, 3600000);
	}

	private void sync(GraphAPIRequest gReq, AirtableAPIRequest aReq) {
		try{
			HttpClient client = HttpClient.newHttpClient();
		
			// PART 1: send getRequest to Team 
			HttpRequest getRequest = gReq.getRequest();
			System.out.println(getRequest);
			HttpResponse<String> teamResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
			showResponseStatus(teamResponse);
			
	        // PART 2: extract specific data (members/channels) and create a requestBody
			String requestBody = "";
			switch (gReq.getOption()) {
			case 1:
				JsonObject jsonMembers = new JsonExtractor().extractMembers(teamResponse);
				requestBody = convertToStr(jsonMembers);
				break;
			case 2:
				JsonObject jsonChannels = new JsonExtractor().extractChannels(teamResponse);
				requestBody = convertToStr(jsonChannels);
				break;
			}

	        // PART 3: send postRequest to Airtable
	        HttpRequest postRequest = aReq.postRequest(requestBody);
	        HttpResponse<String> airtableResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
	        showResponseStatus(airtableResponse);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void showResponseStatus(HttpResponse<String> response) {
		System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());
	}
	
	private static String convertToStr(JsonObject jsonObject) {
		StringWriter stringWriter = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {jsonWriter.write(jsonObject);}
        String requestBody = stringWriter.toString();
        return requestBody;
	}
	
	private static void getInput(GraphAPIRequest gReq, AirtableAPIRequest aReq) {
		Scanner inp = new Scanner(System.in);
		
		System.out.print("Please enter BASE_ID: ");
		try {
			String i = inp.nextLine();
			aReq.setBASE_ID(i);
			
		} catch (IllegalArgumentException e) {
			System.err.println("Error");
		}
		
		System.out.print("Please enter memberTableId: ");
		try {
			aReq.setMemberTableId(inp.nextLine());
		} catch (IllegalArgumentException e) {
			System.err.println("Error");
		}
		
		System.out.print("Please enter channelTableId: ");
		try {
			aReq.setChannelTableId(inp.nextLine());
		} catch (IllegalArgumentException e) {
			System.err.println("Error");
		}
		
		System.out.print("Please enter Airtable API Key: ");
		aReq.setAPI_KEY(inp.nextLine());
		
		System.out.print("Please enter TeamId: ");
		gReq.setTeamId(inp.nextLine());

		System.out.print("Please enter Graph ACCESS_TOKEN: ");
		gReq.setACCESS_TOKEN(inp.nextLine());
	}
}
