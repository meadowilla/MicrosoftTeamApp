package test;

import java.io.IOException;
import java.io.StringWriter;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.json.Json;
import javax.json.JsonObject;
//import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;

import request.AirtableAPIRequest;

public class testAirtableAPI {
	private void createRecord() {
		HttpClient client = HttpClient.newHttpClient();
		
		AirtableAPIRequest airtableReq = new AirtableAPIRequest();
		
		// Create JSON object for the new record
        JsonObject jsonBody = Json.createObjectBuilder()
                .add("fields", Json.createObjectBuilder()
                        .add("Name", "Linh")
                        .add("Age", 19)
                        .build())
                .add("fields", Json.createObjectBuilder()
                        .add("Name", "Nhi")
                        .add("Age", 19)
                        .build())
                .build();
        
        // Another way to create JSON object for the new record
//        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
//        jsonBuilder.add("fields", Json.createObjectBuilder()
//                .add("Name", "Linh")
//                .add("Age", 19)
//                .build());
//        jsonBuilder.add("fields", Json.createObjectBuilder()
//                .add("Name", "Nhi")
//                .add("Age", 19)
//                .build());
        
        // Convert JSON object to string
        StringWriter stringWriter = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
            jsonWriter.write(jsonBody);
        }
        String requestBody = stringWriter.toString();
        
		HttpRequest postRequest = airtableReq.postRequest(requestBody);
		
		try {
			HttpResponse<String> response = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
			// Print response status code and body
            System.out.println("Response Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void listRecords() {
		HttpClient client = HttpClient.newHttpClient();
		
		AirtableAPIRequest airtableReq = new AirtableAPIRequest();
        
		HttpRequest getRequest = airtableReq.getRequest();
		
		try {
			HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
			// Print response status code and body
            System.out.println("Response Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String [] args) {
		testAirtableAPI tester = new testAirtableAPI();
//		tester.listRecords();
		tester.createRecord();
	}

}
