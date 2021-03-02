package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.u.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javax.xml.soap.Text;
import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ButtonPageForNewRequestController {
    @FXML public JFXButton ButtonPageForNRSecurityJFXButton;
    @FXML public JFXButton ButtonPageForNRLaundryJFXButton;
    @FXML public JFXButton ButtonPageForNRMaintenanceJFXButton;
    @FXML public JFXButton ButtonPageForNRType9JFXButton;
    @FXML public JFXButton ButtonPageForNRType7JFXButton;
    @FXML public JFXButton ButtonPageForNRType5JFXButton;
    @FXML public JFXButton ButtonPageForNRType4JFXButton;
    @FXML public JFXButton ButtonPageForNRType6JFXButton;
    @FXML public JFXButton ButtonPageForNRType8JFXButton;
    @FXML public JFXButton cancel;

    @FXML
    private void sendData(ActionEvent event) {
        // https://dev.to/devtony101/javafx-3-ways-of-passing-information-between-scenes-1bm8
        // Step 1
        String type = "";
        // Step 2
        Node node = (Node) event.getSource();
        // Step 3
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        try {
            // Step 4
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/ButtonPageForNewRequestController.fxml"));
            // Step 5
            stage.setUserData(type);
            // Step 6
            Scene scene = new Scene(root);
            stage.setScene(scene);
            // Step 7
            stage.show();
        } catch (IOException e) {
            System.err.println(String.format("Error: %s", e.getMessage()));
        }
    }

    @FXML
    public void handleMakeSecurityButton(ActionEvent actionEvent)throws IOException {
        sendData(actionEvent);
        FXMLLoader requestLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/MakeANewRequest.fxml"));
        requestLoader.load();
    }

    @FXML
    public void handleMakeLaundryButton(ActionEvent actionEvent)throws IOException{
        sendData(actionEvent);
        FXMLLoader requestLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/MakeANewRequest.fxml"));
        requestLoader.load();
    }

    @FXML
    public void handleMakeMaintenanceButton()throws IOException {
        App.newNodeType = "Maintenance";

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    //For more request service types
    @FXML
    public void handleType9Button(ActionEvent actionEvent) {

    }

    @FXML
    public void handleType7Button(ActionEvent actionEvent) {
    }

    @FXML
    public void handleType5Button(ActionEvent actionEvent) {
    }

    @FXML
    public void handleType4Button(ActionEvent actionEvent) {
    }

    @FXML
    public void handleType6Button(ActionEvent actionEvent) {
    }

    @FXML
    public void handleType8Button(ActionEvent actionEvent) {
    }


    @FXML
    public void ButtonPageForNRCancelJFXButton(ActionEvent actionEvent) throws IOException{
        FXMLLoader requestLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewViewRequest.fxml"));
        requestLoader.load();
    }
}
