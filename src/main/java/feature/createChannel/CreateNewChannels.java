package feature.createChannel;

import java.io.IOException;
import java.io.StringWriter;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonWriter;

import entity.Owner;
import feature.AccessTokenGetter;
import request.GraphAPIRequest;

public class CreateNewChannels {
	public CreateNewChannels(String teamId, String displayName, String description, String membershipType, Owner owner) {
		GraphAPIRequest gReq = new GraphAPIRequest();
		
		gReq.setTeamId(teamId);
		gReq.setACCESS_TOKEN(owner.getAccessToken());
		gReq.setOption(2);
		
		try{
            // HttpClient is used to send all requests
            HttpClient client = HttpClient.newHttpClient();
            
            // Part 1: create channel
            JsonObject jsonObject = Json.createObjectBuilder()
        
                .add("displayName", displayName)
                .add("description", description)
                .add("membershipType", membershipType)
                .build();
        
            // Convert JSON object into string
            StringWriter stringWriter = new StringWriter();
            try(JsonWriter jsonWriter = Json.createWriter(stringWriter)){
                jsonWriter.write(jsonObject);
            }
            String requestBody = stringWriter.toString();
        
            // Part 2: send post request to add new channels to team
            HttpRequest postRequest = gReq.postRequest(requestBody);
			HttpResponse<String> teamResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
			showResponseStatus(teamResponse);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args){

        //Part 0: enter neccessary inputs
        GraphAPIRequest gReq = new GraphAPIRequest();
        getInput(gReq);
        gReq.setOption(2); 
        try{
            // HttpClient is used to send all requests
            HttpClient client = HttpClient.newHttpClient();
            
            // Part 1: create channel
            JsonObject jsonObject = Json.createObjectBuilder()
        
                .add("displayName", "Private Channel by Nguyen")
                .add("description", "For testing")
                .add("membershipType", "private")
                .build();
        
            // Convert JSON object into string
            StringWriter stringWriter = new StringWriter();
            try(JsonWriter jsonWriter = Json.createWriter(stringWriter)){
                jsonWriter.write(jsonObject);
            }
            String requestBody = stringWriter.toString();
        
            // Part 2: send post request to add new channels to team
            HttpRequest postRequest = gReq.postRequest(requestBody);
			HttpResponse<String> teamResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
			showResponseStatus(teamResponse);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
    }
    private static void showResponseStatus(HttpResponse<String> response) {
		System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());
	}
    private static void getInput(GraphAPIRequest gReq) {
		Scanner inp = new Scanner(System.in);
		
		System.out.print("Please enter TeamId: ");
		gReq.setTeamId(inp.nextLine());

		gReq.setACCESS_TOKEN(new AccessTokenGetter().get());
		System.out.println("Getting Graph ACCESS_TOKEN...");
	}

}
