package edu.wpi.u.controllers.request;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import edu.wpi.u.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
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
    @FXML public JFXButton helpPageButtonPageButton;
    @FXML public StackPane newStackPane;

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
    public void handleMakeSecurityButton()throws IOException {
        App.newNodeType = "Security";

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/AddRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    @FXML
    public void handleMakeLaundryButton()throws IOException{
        App.newNodeType = "Laundry";

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/AddRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    @FXML
    public void handleMakeMaintenanceButton()throws IOException {
        App.newNodeType = "Maintenance";

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/AddRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }



    @FXML
    public void ButtonPageForNRCancelJFXButton(ActionEvent actionEvent) throws IOException{
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/ViewRequestList.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    public void handleLanguageButton() throws Exception{
        App.newNodeType = "Language";

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/AddRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    public void handleSanitationButton() throws Exception{
        App.newNodeType = "Sanitation";

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/AddRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    public void handleGiftButton() throws Exception{
        App.newNodeType = "Gift";

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/AddRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    public void handleFloralButton() throws Exception{
        App.newNodeType = "Floral";

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/AddRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    public void handleReligiousButton() throws Exception{
        App.newNodeType = "Religious";

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/AddRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    public void handleComputerButton() throws Exception{
        App.newNodeType = "Computer";

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/AddRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    public void handleAudioVisualButton() throws Exception{
        App.newNodeType = "AudioVisual";

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/AddRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    public void handleMedicineButton() throws Exception{
        App.newNodeType = "Medical";

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/AddRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    public void handleFoodButton() throws Exception{
    }

    public void handleHelpPageButton() throws IOException {
            JFXDialogLayout content = new JFXDialogLayout();
            Label header = new Label("Help Page");
            Text text = new Text("Please click button for the type Service Request you require. " +
                "It will bring you to the next page to fill all the information. ");
            header.getStyleClass().add("headline-2");
            content.setHeading(header);
            content.setBody(text);
            content.getStyleClass().add("dialogue");
            JFXDialog dialog = new JFXDialog(newStackPane, content, JFXDialog.DialogTransition.CENTER);
            JFXButton button1 = new JFXButton("CANCEL");
            //JFXButton button2 = new JFXButton("EXIT");
            button1.setOnAction(event -> dialog.close());
            /*button2.setOnAction(event -> {
                dialog.close();
                App.getInstance().end();
            });*/
            button1.getStyleClass().add("button-text");
            //button2.getStyleClass().add("button-contained");
            ArrayList<Node> actions = new ArrayList<>();
            actions.add(button1);
            //actions.add(button2);
            content.setActions(actions);
            dialog.show();

        }
}

