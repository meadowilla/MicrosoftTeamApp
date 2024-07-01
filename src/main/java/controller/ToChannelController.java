package controller;

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

public class ToChannelController {

    @FXML
    private Button SubmitButton;

    @FXML
    private TextField roleField;

    @FXML
    private TextField userIdField;
    @FXML
    private Label userIDError;
    @FXML
    private Button backButton;
    @FXML
    void backButtonClicked(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Team.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) backButton.getScene().getWindow();
            // Set new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();

        }
    }


    @FXML
    void SubmitButtonClicked(ActionEvent event) {
        String role = roleField.getText();
        String userId = userIdField.getText();

        System.out.println(role);
        System.out.println(userId);
    }

    @FXML
    void validateUserIDTyped(KeyEvent event) {


    }

}
