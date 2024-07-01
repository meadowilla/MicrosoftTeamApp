package feature;


import java.io.BufferedReader;
import java.io.FileReader;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class CSVtoJSONConvertor {
	private String csvFile;
	
	public CSVtoJSONConvertor(String csvFile) {
		this.csvFile = csvFile;
	}

	public JsonObject convert() {
//	public static void main(String [] args) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){
			String line;
			br.readLine();
			
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				if (values.length == 4) {
					JsonObjectBuilder jsonObjectBuilder1 = Json.createObjectBuilder()
							.add("@odata.type", "microsoft.graph.aadUserConversationMember")
							.add("roles", Json.createArrayBuilder().add(values[3]).build())
							.add("user@odata.bind", "https://graph.microsoft.com/v1.0/users('" + values[1] + "')");
					jsonArrayBuilder.add(jsonObjectBuilder1);
				} else {
					JsonObjectBuilder jsonObjectBuilder2 = Json.createObjectBuilder()
							.add("@odata.type", "microsoft.graph.aadUserConversationMember")
							.add("roles", Json.createArrayBuilder().build())
							.add("user@odata.bind", "https://graph.microsoft.com/v1.0/users('" + values[1] + "')");
					jsonArrayBuilder.add(jsonObjectBuilder2);
				}
				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JsonObject jsonNewMembers = Json.createObjectBuilder()
				.add("values", jsonArrayBuilder)
				.build();
		
//		System.out.println("String: " + jsonNewMembers.toString());
		return jsonNewMembers;
	}

}
