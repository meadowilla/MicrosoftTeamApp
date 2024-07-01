package controller;

import entity.Owner;
import feature.JsonExtractor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.charset.StandardCharsets;

public class Verify {

    @FXML
    private TextField ClientIDButton;

    @FXML
    private TextField SecreteIDButton;

    @FXML
    private TextField TenantIDButton;

    @FXML
    private Button VertifyButton;

    @FXML
    private Label clientIDError;

    @FXML
    private Label secreteIDError;

    @FXML
    private Label tenantIDError;
    
    public static Owner owner = new Owner();
    private String validTenantId = "";
    private String validClientId = "";
    private String validClientSecret = "";


    @FXML
    void VertifyButtonClicked(ActionEvent event) {
        boolean valid = validateClientID(ClientIDButton.getText()) &&
                validateSecreteID(SecreteIDButton.getText()) &&
                validateTenantID(TenantIDButton.getText());
        if (valid) {
            // if IDs are correct, then switch to new scene
            try {
                // Load the To Channel scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Team.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) VertifyButton.getScene().getWindow();

                // Set the new scene
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                
                HttpClient client = HttpClient.newHttpClient();
        		HttpRequest request = HttpRequest.newBuilder()
        				.uri(URI.create("https://login.microsoftonline.com/" + validTenantId + "/oauth2/v2.0/authorize?"
        						+ "client_id=" + validClientId
        						+ "&redirect_uri=https://login.microsoftonline.com/common/oauth2/nativeclient"
        						+ "&state=12345"))
        				.GET()
        				.build();
        	
        		try {
        			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        			System.out.println("Response Code: " + response.statusCode());
//        	        System.out.println("Response Body: " + response.body());
        		} catch (IOException | InterruptedException e) {
        			e.printStackTrace();
        		}
        		
        		String tokenRequestBody = "client_id=" + URLEncoder.encode(validClientId, StandardCharsets.UTF_8) +
                        "&scope=https://graph.microsoft.com/.default" +
                        "&client_secret=" + URLEncoder.encode(validClientSecret, StandardCharsets.UTF_8) +
                        "&grant_type=client_credentials";
        		
        		HttpRequest tokenRequest = HttpRequest.newBuilder()
        				.uri(URI.create("https://login.microsoftonline.com/" + validTenantId + "/oauth2/v2.0/token"))
        				.POST(BodyPublishers.ofString(tokenRequestBody))
        				.header("Content-Type", "application/x-www-form-urlencoded")
        				.build();
        		
        		String accessToken = "";
        		try {
        			HttpResponse<String> tokenResponse = client.send(tokenRequest, HttpResponse.BodyHandlers.ofString());
//        			System.out.println("Response Code: " + tokenResponse.statusCode());
//        	        System.out.println("Response Body: " + tokenResponse.body());
//        			
        			accessToken = new JsonExtractor().extractToken(tokenResponse);
//        			System.out.println("Access Token: " + accessToken);
        			
                    
                    owner.setAccessToken(accessToken);
                    
        		} catch (IOException | InterruptedException e) {
        			e.printStackTrace();
        		}

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        new TeamsController(validTenantId, validClientId, validClientSecret);

    }


    @FXML
    void validateClientIDTyped(KeyEvent event) {
        validateClientID(ClientIDButton.getText()); // check the valid of Text is entered
    }

    @FXML
    void validateSecreteIDTyped(KeyEvent event) {
        validateSecreteID(SecreteIDButton.getText());
    }

    @FXML
    void validateTenantIDTyped(KeyEvent event) {
        validateTenantID(TenantIDButton.getText());
    }

    private boolean validateClientID(String clientID) {
        if (clientID == null || clientID.trim().isEmpty()) { // how to check validation
            clientIDError.setText("Please enter your clientID.");
            return false;
        }
        if (clientID.equals(validClientId)) {
            return true;
        }
        clientIDError.setText("Client ID is not correct.");
        return false;
    }

    private boolean validateSecreteID(String secreteID) {
        if (secreteID == null || secreteID.trim().isEmpty()) {
            secreteIDError.setText("Please enter your Secrete ID.");
            return false;
        }
        if (secreteID.equals(validClientSecret)) {
            return true;
        }
        secreteIDError.setText("Secrete ID is not correct.");
        return false;
    }

    private boolean validateTenantID(String tenantID) {
        if (tenantID == null || tenantID.trim().isEmpty()) {
            tenantIDError.setText("Please enter your tenant ID.");
            return false;
        }
        if (tenantID.equals(validTenantId)) {
            return true;
        }
        tenantIDError.setText("Tenant ID is not correct.");
        return false;
    }

}
