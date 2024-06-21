package feature;

import java.io.IOException;
import java.io.StringWriter;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

import request.GraphAPIRequest;

public class CreateNewChannels {

	public static void main(String[] args){

        //Part 0: enter neccessary inputs
        GraphAPIRequest gRep = new GraphAPIRequest();
        getInput(gRep);
        gRep.setOption(2); 
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
            HttpRequest postRequest = gRep.postRequest(requestBody);
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

		System.out.print("Please enter Graph ACCESS_TOKEN: ");
		gReq.setACCESS_TOKEN(inp.nextLine());
	}

}
