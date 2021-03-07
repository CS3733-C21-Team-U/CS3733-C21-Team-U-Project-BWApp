package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import edu.wpi.u.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

public class MainHelpPageController {


    @FXML public StackPane newMainPageStackPane;
    @FXML public Text weAreHereToHelpTitle;


    public void handleServicesButton() throws IOException{
    }

    public void handleContactUsButton(ActionEvent actionEvent) throws IOException{
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("Contact Information");
        Text text1 = new Text("75 Francis Street \n" +
                "Boston, MA 02115 USA \n (617) 732-5500\n" +
                "(617) 732-6458 TTY/TTD");

        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.setBody(text1);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newMainPageStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("CLOSE");
        //JFXButton button2 = new JFXButton("Help Page");
        button1.setOnAction(event -> dialog.close());
        /*button2.setOnAction(event -> {
            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/MainHelpPage.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            anchor.getChildren().clear();
            anchor.getChildren().add(root);
            dialog.close();
        });*/
        button1.getStyleClass().add("button-text");
        ArrayList<Node> actions = new ArrayList<>();
        actions.add(button1);
        content.setActions(actions);
        dialog.show();
    }

    public void handleBackToMainPageButton()  throws IOException{
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/PathfindingBase.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    public void handleLogInButton(ActionEvent actionEvent) {
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("Log in Help Page");
        Text text1 = new Text("How to use log in:");

        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.setBody(text1);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newMainPageStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("CLOSE");
        //JFXButton button2 = new JFXButton("Help Page");
        button1.setOnAction(event -> dialog.close());
        /*button2.setOnAction(event -> {
            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/MainHelpPage.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            anchor.getChildren().clear();
            anchor.getChildren().add(root);
            dialog.close();
        });*/
        button1.getStyleClass().add("button-text");
        ArrayList<Node> actions = new ArrayList<>();
        actions.add(button1);
        content.setActions(actions);
        dialog.show();

    }

    public void handleTwoFactorAButton(ActionEvent actionEvent) {
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("Two Factor Authorization Help Page");
        Text text1 = new Text("How to use Two Factor Authorization:");

        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.setBody(text1);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newMainPageStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("CLOSE");
        //JFXButton button2 = new JFXButton("Help Page");
        button1.setOnAction(event -> dialog.close());
        /*button2.setOnAction(event -> {
            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/MainHelpPage.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            anchor.getChildren().clear();
            anchor.getChildren().add(root);
            dialog.close();
        });*/
        button1.getStyleClass().add("button-text");
        ArrayList<Node> actions = new ArrayList<>();
        actions.add(button1);
        content.setActions(actions);
        dialog.show();
    }

    public void handleForgotPasswordButton(ActionEvent actionEvent) {
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("Forgot Password Help Page");
        Text text1 = new Text("How to use forgot password:");

        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.setBody(text1);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newMainPageStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("CLOSE");
        //JFXButton button2 = new JFXButton("Help Page");
        button1.setOnAction(event -> dialog.close());
        /*button2.setOnAction(event -> {
            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/MainHelpPage.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            anchor.getChildren().clear();
            anchor.getChildren().add(root);
            dialog.close();
        });*/
        button1.getStyleClass().add("button-text");
        ArrayList<Node> actions = new ArrayList<>();
        actions.add(button1);
        content.setActions(actions);
        dialog.show();
    }

    public void handlePathFindingButton(ActionEvent actionEvent) {
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("PathFinding Help Page");
        Text text1 = new Text("How to use PathFinding: ");

        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.setBody(text1);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newMainPageStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("CLOSE");
        JFXButton button2 = new JFXButton("Processed to PathFinding Page");
        button1.setOnAction(event -> dialog.close());
        button2.setOnAction(event -> {
            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/PathfindingBase.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            anchor.getChildren().clear();
            anchor.getChildren().add(root);
            dialog.close();
        });
        button1.getStyleClass().add("button-text");
        button2.getStyleClass().add("button-contained");
        ArrayList<Node> actions = new ArrayList<>();
        actions.add(button1);
        actions.add(button2);
        content.setActions(actions);
        dialog.show();
    }

    public void handleSettingPageButton(ActionEvent actionEvent) {
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("Setting Help Page");
        Text text1 = new Text("How to use Setting Page:");

        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.setBody(text1);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newMainPageStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("CLOSE");
        JFXButton button2 = new JFXButton("Processed to Setting Page");
        button1.setOnAction(event -> dialog.close());
        button2.setOnAction(event -> {
            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/SettingsPageSprint2.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            anchor.getChildren().clear();
            anchor.getChildren().add(root);
            dialog.close();
        });
        button1.getStyleClass().add("button-text");
        button2.getStyleClass().add("button-contained");
        ArrayList<Node> actions = new ArrayList<>();
        actions.add(button1);
        actions.add(button2);
        content.setActions(actions);
        dialog.show();
    }

    public void handleAddUserButton(ActionEvent actionEvent) {
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("Add User Help Page");
        Text text1 = new Text("How to add user:");

        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.setBody(text1);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newMainPageStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("CLOSE");
        //JFXButton button2 = new JFXButton("Help Page");
        button1.setOnAction(event -> dialog.close());
        /*button2.setOnAction(event -> {
            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/MainHelpPage.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            anchor.getChildren().clear();
            anchor.getChildren().add(root);
            dialog.close();
        });*/
        button1.getStyleClass().add("button-text");
        ArrayList<Node> actions = new ArrayList<>();
        actions.add(button1);
        content.setActions(actions);
        dialog.show();
    }

    public void handleEditUserButton(ActionEvent actionEvent) {
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("Edit User Help Page");
        Text text1 = new Text("How to Edit User");

        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.setBody(text1);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newMainPageStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("CLOSE");
        //JFXButton button2 = new JFXButton("Help Page");
        button1.setOnAction(event -> dialog.close());
        /*button2.setOnAction(event -> {
            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/MainHelpPage.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            anchor.getChildren().clear();
            anchor.getChildren().add(root);
            dialog.close();
        });*/
        button1.getStyleClass().add("button-text");
        ArrayList<Node> actions = new ArrayList<>();
        actions.add(button1);
        content.setActions(actions);
        dialog.show();
    }

    public void handleViewUserButton(ActionEvent actionEvent) {
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("View User Help Page");
        Text text1 = new Text("How to View User");

        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.setBody(text1);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newMainPageStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("CLOSE");
        //JFXButton button2 = new JFXButton("Help Page");
        button1.setOnAction(event -> dialog.close());
        /*button2.setOnAction(event -> {
            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/MainHelpPage.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            anchor.getChildren().clear();
            anchor.getChildren().add(root);
            dialog.close();
        });*/
        button1.getStyleClass().add("button-text");
        ArrayList<Node> actions = new ArrayList<>();
        actions.add(button1);
        content.setActions(actions);
        dialog.show();
    }

    public void handleLoadAndSaveCSVButton(ActionEvent actionEvent) {
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("Load And Save CSV Help Page");
        Text text1 = new Text("How to Load and Save CSV:");

        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.setBody(text1);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newMainPageStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("CLOSE");
        //JFXButton button2 = new JFXButton("Help Page");
        button1.setOnAction(event -> dialog.close());
        /*button2.setOnAction(event -> {
            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/MainHelpPage.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            anchor.getChildren().clear();
            anchor.getChildren().add(root);
            dialog.close();
        });*/
        button1.getStyleClass().add("button-text");
        ArrayList<Node> actions = new ArrayList<>();
        actions.add(button1);
        content.setActions(actions);
        dialog.show();
    }
}
