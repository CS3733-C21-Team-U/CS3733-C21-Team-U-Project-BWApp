package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.u.App;
import edu.wpi.u.users.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class AdminMainHelpController {
    private static final Role ADMIN = Role.ADMIN;
    @FXML
    public StackPane newMainPageStackPane;
    @FXML public Text weAreHereToHelpTitle;
    @FXML public JFXButton LogInHelpButton;
    @FXML public JFXButton TwoFactorAHelpButton;
    @FXML public JFXButton ForgotPassHelpButton;
    @FXML public JFXButton ContactUsHelpButton;
    @FXML public JFXButton SettingHelpButton;
    @FXML public JFXButton AddUserHelpButton;
    @FXML public JFXButton EditUserHelpButton;
    @FXML public JFXButton ViewUserHelpButton;
    @FXML public JFXButton LoadAndSaveCSVButton;
    @FXML public JFXButton PathFindingHelpButton;
    @FXML public JFXButton CovidHelpButton;
    @FXML public JFXButton AboutHelpButton;




    public void handleContactUsButton(ActionEvent actionEvent) throws IOException {
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/generaluserhelp/ContactUsHelpPage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        anchor.getChildren().clear();
        anchor.getChildren().add(root);

    }



    public void handlePathFindingButton(ActionEvent actionEvent) throws IOException {
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/generaluserhelp/PathFindingHelpPage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    public void handleSettingPageButton(ActionEvent actionEvent) throws IOException {
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/generaluserhelp/SettingHelpPage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        anchor.getChildren().clear();
        anchor.getChildren().add(root);

    }

    public void handleAddUserButton(ActionEvent actionEvent) throws IOException {
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/adminhelp/AddUserHelpPage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    public void handleEditUserButton(ActionEvent actionEvent) throws IOException {

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/adminhelp/EditUserHelpPage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        anchor.getChildren().clear();
        anchor.getChildren().add(root);

    }

    public void handleViewUserButton(ActionEvent actionEvent) throws IOException {

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/adminhelp/ViewUserHelpPage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    public void handleLoadAndSaveCSVButton(ActionEvent actionEvent)throws IOException  {

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/adminhelp/LoadAndSaveCSVHelpPage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        anchor.getChildren().clear();
        anchor.getChildren().add(root);

    }

    public void handleMapBuilderButton(ActionEvent actionEvent) {
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/adminhelp/MapBuilderHelpPage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    public void handleUserManagerButton(ActionEvent actionEvent) {
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/adminhelp/UserManagerHelpPage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }
}
