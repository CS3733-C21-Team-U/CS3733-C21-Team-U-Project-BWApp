package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import edu.wpi.u.App;
import edu.wpi.u.users.Role;
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

import static edu.wpi.u.users.Role.ADMIN;

public class MainHelpPageController {


    private static final Role ADMIN = Role.ADMIN;
    @FXML public StackPane newMainPageStackPane;
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
    @FXML public JFXButton GuestListHelpButton;
    @FXML public JFXButton MyRequestHelpButton;


    public void handleContactUsButton(ActionEvent actionEvent) throws IOException{
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


    public void handleLogInButton(ActionEvent actionEvent) throws IOException {

            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/generaluserhelp/LogInHelpPage.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            anchor.getChildren().clear();
            anchor.getChildren().add(root);
    }

    public void handleTwoFactorAButton(ActionEvent actionEvent) throws IOException {
            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/generaluserhelp/TwoFactorAuthorizationHelpPage.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            anchor.getChildren().clear();
            anchor.getChildren().add(root);

    }

    public void handleForgotPasswordButton(ActionEvent actionEvent)throws IOException  {

            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/generaluserhelp/ForgotPasswordHelpPage.fxml"));
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


    public void handleGuestListPageButton(ActionEvent actionEvent) {
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/generaluserhelp/GuestListHelpPage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    public void handleMyRequestPageButton(ActionEvent actionEvent) {
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/generaluserhelp/MyRequestHelpPage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }
}
