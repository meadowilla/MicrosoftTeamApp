package controller;

import feature.createChannel.*;
import entity.Owner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateChannelsController {
    
    @FXML
    private TextField displayNameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField membershipTypeField;

    @FXML
    private Button createButton;

    @FXML
    private Button backButton;

    @FXML
    void backButtonClicked(ActionEvent event) {
        try {
            // Load the To Channel scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Team.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) backButton.getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void handleCreateChannel(ActionEvent event) {
        String displayName = displayNameField.getText();
        String description = descriptionField.getText();
        String membershipType = membershipTypeField.getText();

        new CreateNewChannels("3ea72d5f-4eb1-49d7-9695-f3b8e6f17bb1", displayName, description, membershipType, Verify.owner);
        System.out.println("finish!");







    }
}
