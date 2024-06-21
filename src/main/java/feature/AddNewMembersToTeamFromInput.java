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

public class AddNewMembersToTeamFromInput {

	public static void main(String[] args){
        // Part 0: enter necessary inputs
        GraphAPIRequest gRep = new GraphAPIRequest();
        getInput(gRep);
        System.out.print("Please enter UserId: ");
        Scanner inp = new Scanner(System.in);        

        // Ensure the option is set before making the request
        gRep.setOption(3);
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
            HttpRequest postRequest = gRep.postRequest(requestBody);
            System.out.println(postRequest);
            HttpResponse<String> teamResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
            showResponseStatus(teamResponse);
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

        System.out.print("Please enter Graph ACCESS_TOKEN: ");
        String accessToken = inp.nextLine();
        gReq.setACCESS_TOKEN(accessToken);

    }
}
