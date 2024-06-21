package feature;

import java.io.StringReader;
import java.net.http.HttpResponse;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;

public class JsonExtractor {
	
	public String extractToken(HttpResponse<String> response) {
		JsonReader reader = Json.createReader(new StringReader(response.body()));
		JsonObject jsonTokenResponse = reader.readObject();
		String token = jsonTokenResponse.getString("access_token");
		return token;
	}
	
	public JsonObject extractMembers(HttpResponse<String> response) {
		JsonReader reader = Json.createReader(new StringReader(response.body()));
        JsonObject jsonTeamResponse = reader.readObject();
        JsonArray values = jsonTeamResponse.getJsonArray("value");
        
        JsonArrayBuilder members = Json.createArrayBuilder();        
        for (int i = 0; i < values.size(); i++) {
        	JsonObject value = values.getJsonObject(i);
        	JsonObjectBuilder member = Json.createObjectBuilder()
        			.add("fields", Json.createObjectBuilder()
        					.add("displayName", value.getString("displayName"))
//        					.add("visibleHistoryStartDataTime", value.getString("visibleHistoryStartDataTime"))
                			.add("userId", value.getString("userId"))
                			.add("email", (value.get("email")== JsonValue.NULL)? "": value.getString("email"))
                			.add("tenantId", value.getString("tenantId"))
                			.add("roles", (value.getJsonArray("roles").isEmpty())? "": value.getJsonArray("roles").getString(0))
                			);
        	members.add(member);
        }
        
        JsonObject jsonMembers = Json.createObjectBuilder()
        		.add("records", members)
        		.build();
        
        return jsonMembers;
	}
	
	public JsonObject extractChannels(HttpResponse<String> response) {
		JsonReader reader = Json.createReader(new StringReader(response.body()));
        JsonObject jsonTeamResponse = reader.readObject();
        JsonArray values = jsonTeamResponse.getJsonArray("value");
        
        JsonArrayBuilder channels = Json.createArrayBuilder();
        for (int i = 0; i < values.size(); i++) {
        	JsonObject value = values.getJsonObject(i);
        	JsonObjectBuilder channel = Json.createObjectBuilder()
        			.add("fields", Json.createObjectBuilder()
        					.add("displayName", value.getString("displayName"))
        					.add("createdDateTime", value.getString("createdDateTime"))
                			.add("id", value.getString("id"))
                			.add("description", (value.get("description")== JsonValue.NULL)? "":value.getString("description"))
                			);
        	channels.add(channel);
        }
        
        JsonObject jsonMembers = Json.createObjectBuilder()
        		.add("records", channels)
        		.build();
        
        return jsonMembers;
	}
}

