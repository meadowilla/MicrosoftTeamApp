package feature.addMember;

import java.io.IOException;
import java.io.StringWriter;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

import entity.Owner;
import feature.AccessTokenGetter;
import request.GraphAPIRequest;

public class AddNewMembersToChannel{
	public AddNewMembersToChannel(String teamId, String channelId, String userId, Owner owner) {
		// Part 0: enter necessary inputs
        GraphAPIRequest gReq = new GraphAPIRequest();
        gReq.setTeamId(teamId);
        gReq.setChannelId(channelId);
        gReq.setOption(3); // Set the suitable option before making the request
        gReq.setACCESS_TOKEN(owner.getAccessToken());

        try{
            // Send request with HttpClient
            HttpClient client = HttpClient.newHttpClient();
            
            // Part 1: create channel
            JsonObject jsonObject = Json.createObjectBuilder()
            		.add("@odata.type", "#microsoft.graph.aadUserConversationMember")
                    .add("roles", Json.createArrayBuilder().build())
                    .add("user@odata.bind", "https://graph.microsoft.com/v1.0/users('" + userId +"')")
                    .build();
            // Convert JSON object into string
            StringWriter stringWriter = new StringWriter();
            try(JsonWriter jsonWriter = Json.createWriter(stringWriter)){
                jsonWriter.write(jsonObject);
            }
            String requestBody = stringWriter.toString();
               
            // Part 2: send post request to add new members to channels
            HttpRequest postRequest = gReq.postRequest(requestBody);
            System.out.println(postRequest);
            HttpResponse<String> teamResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
//            showResponseStatus(teamResponse);
        } catch (IOException | InterruptedException | IllegalArgumentException e) {
            e.printStackTrace();
        }
	}
/*
	public static void main(String[] args){
        // Part 0: enter necessary inputs
        GraphAPIRequest gReq = new GraphAPIRequest();
        getInput(gReq);
        gReq.setOption(3); // Set the suitable option before making the request
    
        Scanner inp = new Scanner(System.in);
        System.out.print("Please enter UserId: ");
                
        
        try{
            // Send request with HttpClient
            HttpClient client = HttpClient.newHttpClient();
            
            // Part 1: create channel
            JsonObject jsonObject = Json.createObjectBuilder()
            		.add("@odata.type", "#microsoft.graph.aadUserConversationMember")
                    .add("roles", Json.createArrayBuilder().build())
                    .add("user@odata.bind", "https://graph.microsoft.com/v1.0/users('" + inp.nextLine() +"')")
                    .build();
            // Convert JSON object into string
            StringWriter stringWriter = new StringWriter();
            try(JsonWriter jsonWriter = Json.createWriter(stringWriter)){
                jsonWriter.write(jsonObject);
            }
            String requestBody = stringWriter.toString();
               
            // Part 2: send post request to add new members to channels
            HttpRequest postRequest = gReq.postRequest(requestBody);
            System.out.println(postRequest);
            HttpResponse<String> teamResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
//            showResponseStatus(teamResponse);
        } catch (IOException | InterruptedException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    private static void showResponseStatus(HttpResponse<String> response) {
        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());
    }

    private static void getInput(GraphAPIRequest gReq){
        Scanner inp = new Scanner(System.in);

        System.out.print("Please enter TeamId: ");
        String teamId = inp.nextLine();
        gReq.setTeamId(teamId);

        System.out.print("Please enter ChannelId: ");
        String channelId = inp.nextLine();
        gReq.setChannelId(channelId);

//        gReq.setACCESS_TOKEN(new AccessTokenGetter().get());
//		System.out.println("Getting Graph ACCESS_TOKEN...");

    }
*/
}
