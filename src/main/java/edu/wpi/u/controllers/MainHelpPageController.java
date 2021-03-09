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
        Label header = new Label("Contact Us Help Page");
        Text text1 = new Text("Click Detail to see contact us information. Otherwise, Click Cancel");

        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.setBody(text1);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newMainPageStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("CLOSE");
        JFXButton button2 = new JFXButton("Detail");
        button1.setOnAction(event -> dialog.close());
        button2.setOnAction(event -> {
            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/generaluserhelp/ContactUsHelpPage.fxml"));
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


    public void handleLogInButton(ActionEvent actionEvent) throws IOException {
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("Log in Help Page");
        Text text1 = new Text("Click Detail to see Log in information. Otherwise, Click Cancel");

        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.setBody(text1);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newMainPageStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("CLOSE");
        JFXButton button2 = new JFXButton("Detail");
        button1.setOnAction(event -> dialog.close());
        button2.setOnAction(event -> {
            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/generaluserhelp/LogInHelpPage.fxml"));
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

    public void handleTwoFactorAButton(ActionEvent actionEvent) throws IOException {
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("Two Factor Authorization Help Page");
        Text text1 = new Text("Click Detail to see Two Factor Authorization information. Otherwise, Click Cancel");

        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.setBody(text1);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newMainPageStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("CLOSE");
        JFXButton button2 = new JFXButton("Detail");
        button1.setOnAction(event -> dialog.close());
        button2.setOnAction(event -> {
            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/generaluserhelp/TwoFactorAuthorizationHelpPage.fxml"));
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

    public void handleForgotPasswordButton(ActionEvent actionEvent)throws IOException  {
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("Forgot Password Help Page");
        Text text1 = new Text("Click Detail to see forgot password information. Otherwise, Click Cancel");

        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.setBody(text1);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newMainPageStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("CLOSE");
        JFXButton button2 = new JFXButton("Detail");
        button1.setOnAction(event -> dialog.close());
        button2.setOnAction(event -> {
            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/generaluserhelp/ForgotPasswordHelpPage.fxml"));
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

    public void handlePathFindingButton(ActionEvent actionEvent) throws IOException {
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("PathFinding Help Page");
        Text text1 = new Text("Click Detail to see pathfinding information. Otherwise, Click Cancel");

        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.setBody(text1);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newMainPageStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("CLOSE");
        JFXButton button2 = new JFXButton("Detail");
        button1.setOnAction(event -> dialog.close());
        button2.setOnAction(event -> {
            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/generaluserhelp/PathFindingHelpPage.fxml"));
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

    public void handleSettingPageButton(ActionEvent actionEvent) throws IOException {
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("Setting Help Page");
        Text text1 = new Text("Click Detail to see setting information. Otherwise, Click Cancel");

        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.setBody(text1);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newMainPageStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("CLOSE");
        JFXButton button2 = new JFXButton("Detail");
        button1.setOnAction(event -> dialog.close());
        button2.setOnAction(event -> {
            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/generaluserhelp/SettingHelpPage.fxml"));
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

    public void handleAddUserButton(ActionEvent actionEvent) throws IOException {
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("Add User Help Page");
        Text text1 = new Text("Click Detail to see Add User information. Otherwise, Click Cancel");

        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.setBody(text1);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newMainPageStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("CLOSE");
        JFXButton button2 = new JFXButton("Detail");
        button1.setOnAction(event -> dialog.close());
        button2.setOnAction(event -> {
            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/adminhelp/AddUserHelpPage.fxml"));
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

    public void handleEditUserButton(ActionEvent actionEvent) throws IOException {
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("Edit User Help Page");
        Text text1 = new Text("Click Detail to see Edit User information. Otherwise, Click Cancel");

        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.setBody(text1);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newMainPageStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("CLOSE");
        JFXButton button2 = new JFXButton("Detail");
        button1.setOnAction(event -> dialog.close());
        button2.setOnAction(event -> {
            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/adminhelp/EditUserHelpPage.fxml"));
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

    public void handleViewUserButton(ActionEvent actionEvent) throws IOException {
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("View User Help Page");
        Text text1 = new Text("Click Detail to see View User information. Otherwise, Click Cancel");

        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.setBody(text1);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newMainPageStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("CLOSE");
        JFXButton button2 = new JFXButton("Detail");
        button1.setOnAction(event -> dialog.close());
        button2.setOnAction(event -> {
            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/adminhelp/ViewUserHelpPage.fxml"));
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

    public void handleLoadAndSaveCSVButton(ActionEvent actionEvent)throws IOException  {
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("Load and Save CSV Help Page");
        Text text1 = new Text("Click Detail to see Load and Save CSV information. Otherwise, Click Cancel");

        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.setBody(text1);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newMainPageStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("CLOSE");
        JFXButton button2 = new JFXButton("Detail");
        button1.setOnAction(event -> dialog.close());
        button2.setOnAction(event -> {
            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/adminhelp/LoadAndSaveCSVHelpPage.fxml"));
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

    /**
     * Takes users from the help page to the about us page
     * @throws Exception
     */
    @FXML public void handleAboutPage() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/BaseAboutPage.fxml"));
        fxmlLoader.load();
        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }
}
