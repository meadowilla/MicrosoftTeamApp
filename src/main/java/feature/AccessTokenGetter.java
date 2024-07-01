package feature;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class AccessTokenGetter {
	
	private String tenantId; 
	private String clientId; 
	private String clientSecret; 
	private String accessToken;

//	public static void main(String [] args) {
	public String get() {
		AccessTokenGetter getter = new AccessTokenGetter();
		
		getter.getInput();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://login.microsoftonline.com/" + getter.tenantId + "/oauth2/v2.0/authorize?"
						+ "client_id=" + getter.clientId
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
		
		String tokenRequestBody = "client_id=" + URLEncoder.encode(getter.clientId, StandardCharsets.UTF_8) +
                "&scope=https://graph.microsoft.com/.default" +
                "&client_secret=" + URLEncoder.encode(getter.clientSecret, StandardCharsets.UTF_8) +
                "&grant_type=client_credentials";
		
		HttpRequest tokenRequest = HttpRequest.newBuilder()
				.uri(URI.create("https://login.microsoftonline.com/" + getter.tenantId + "/oauth2/v2.0/token"))
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
		
		getter.accessToken = accessToken;
		return accessToken;
	}

	private void getInput() {
		Scanner inp = new Scanner(System.in);
		System.out.print("Please enter tenantId: ");
		setTenantId(inp.nextLine());
		
		System.out.print("Please enter clientId: ");
		setClientId(inp.nextLine());
		
		System.out.print("Please enter clientSecret: ");
		setClientSecret(inp.nextLine());

	}

	private void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	private void setClientId(String clientId) {
		this.clientId = clientId;
	}

	private void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}	
	
}
// Cần một buổi để cùng hoàn thiện UI (mai 30/9) ở thư viện!!!
