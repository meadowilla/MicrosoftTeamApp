package controller;

import feature.JsonExtractor;
import entity.Owner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class TeamsController {
    @FXML
    private MenuItem ChannelsButton;

    @FXML
    private MenuItem CreateChannelButton;

    @FXML
    private MenuItem ToTeamFromCSVButton;


    @FXML
    private MenuItem MembersButton;

    @FXML
    private MenuItem TeamsButton;

    @FXML
    private MenuItem ToChannelButton;

    @FXML
    private Label lbl;

    private Owner owner = new Owner();
    public TeamsController(){

    }
    public TeamsController(String validTenantId, String validClientId, String validSecretId){
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
//			System.out.println("Response Code: " + response.statusCode());
//	        System.out.println("Response Body: " + response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        String tokenRequestBody = "client_id=" + URLEncoder.encode(validClientId, StandardCharsets.UTF_8) +
                "&scope=https://graph.microsoft.com/.default" +
                "&client_secret=" + URLEncoder.encode(validSecretId, StandardCharsets.UTF_8) +
                "&grant_type=client_credentials";

        HttpRequest tokenRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://login.microsoftonline.com/" + validTenantId + "/oauth2/v2.0/token"))
                .POST(HttpRequest.BodyPublishers.ofString(tokenRequestBody))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        String accessToken = "";
        try {
            HttpResponse<String> tokenResponse = client.send(tokenRequest, HttpResponse.BodyHandlers.ofString());
//			System.out.println("Response Code: " + tokenResponse.statusCode());
//	        System.out.println("Response Body: " + tokenResponse.body());

            accessToken = new JsonExtractor().extractToken(tokenResponse);
            //System.out.println("Access Token: " + accessToken);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        this.owner.setAccessToken(accessToken);
        System.out.println("Access_token: " + this.owner.getAccessToken());
    }
    @FXML
    void ChannelsButtonClicked(ActionEvent event) {
        // Add functionality for ChannelsButton here
    }

    @FXML
    void CreateChannelClicked(ActionEvent event) {
        try {
            // Load the To Channel scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateChannels.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ChannelsButton.getParentPopup().getOwnerWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("token channels: " + owner.getAccessToken());

    }

    @FXML
    void ToTeamFromCSVButtonClicked(ActionEvent event) {
        // Add functionality for FromCSVButton here
        Stage currentStage = (Stage) lbl.getScene().getWindow();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ToTeamFromCSV.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = currentStage;
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void MembersButtonClicked(ActionEvent event) {
        // Add functionality for MembersButton here
    }

    @FXML
    void TeamsButtonClicked(ActionEvent event) {
        // Add functionality for TeamsButton here
    }

    @FXML
    void ToChannelButtonClicked(ActionEvent event) {
        Stage currentStage = (Stage) lbl.getScene().getWindow();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ToChannel.fxml"));
            fxmlLoader.setController( new ToChannelController());
            Parent root = fxmlLoader.load();

            Stage stage = currentStage;
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
