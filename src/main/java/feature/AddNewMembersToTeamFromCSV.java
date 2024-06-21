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

public class AddNewMembersToTeamFromCSV {

	public static void main(String[] args) {
		// PART 0: enter necessary inputs
		GraphAPIRequest gReq = new GraphAPIRequest();
		getInput(gReq);
		Scanner inp = new Scanner(System.in);
		System.out.print("Please enter the path to csv file: ");
		try {
			HttpClient client = HttpClient.newHttpClient();
			
			// PART 1: create requestBody from CSV data
			JsonObject jsonNewMembers = new CSVtoJSONConvertor(inp.nextLine()).convert();
			String requestBody = convertToStr(jsonNewMembers);
			
			System.out.println(requestBody);

			// PART 2: send post request to add new members to team
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
	
	private static String convertToStr(JsonObject jsonObject) {
		StringWriter stringWriter = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {jsonWriter.write(jsonObject);}
        String requestBody = stringWriter.toString();
        return requestBody;
	}
	
	private static void getInput(GraphAPIRequest gReq) {
		Scanner inp = new Scanner(System.in);
		
		System.out.print("Please enter TeamId: ");
		gReq.setTeamId(inp.nextLine());

		System.out.print("Please enter Graph ACCESS_TOKEN: ");
		gReq.setACCESS_TOKEN(inp.nextLine());
	}

}
