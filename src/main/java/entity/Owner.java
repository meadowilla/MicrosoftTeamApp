package entity;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.charset.StandardCharsets;
import feature.JsonExtractor;

public class Owner{
	private String accessToken;
	public Owner() {}

    public Owner(String tenantId, String clientId, String clientSecret) {
    	HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://login.microsoftonline.com/" + tenantId + "/oauth2/v2.0/authorize?"
						+ "client_id=" + clientId
						+ "&redirect_uri=https://login.microsoftonline.com/common/oauth2/nativeclient"
						+ "&state=12345"))
				.GET()
				.build();
	
		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//			System.out.println("Response Code: " + response.statusCode());
//	        System.out.println("Response Body: " + response.body());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		String tokenRequestBody = "client_id=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8) +
                "&scope=https://graph.microsoft.com/.default" +
                "&client_secret=" + URLEncoder.encode(clientSecret, StandardCharsets.UTF_8) +
                "&grant_type=client_credentials";
		
		HttpRequest tokenRequest = HttpRequest.newBuilder()
				.uri(URI.create("https://login.microsoftonline.com/" + tenantId + "/oauth2/v2.0/token"))
				.POST(BodyPublishers.ofString(tokenRequestBody))
				.header("Content-Type", "application/x-www-form-urlencoded")
				.build();
		
		String accessToken = "";
		try {
			HttpResponse<String> tokenResponse = client.send(tokenRequest, HttpResponse.BodyHandlers.ofString());
//			System.out.println("Response Code: " + tokenResponse.statusCode());
//	        System.out.println("Response Body: " + tokenResponse.body());
//			
			accessToken = new JsonExtractor().extractToken(tokenResponse);
//			System.out.println("Access Token: " + accessToken);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		this.accessToken = accessToken;
    }
    
    public String getAccessToken() {
    	return this.accessToken;
    }
    
    public void setAccessToken(String accessToken) {
    	this.accessToken = accessToken;
    }

}
