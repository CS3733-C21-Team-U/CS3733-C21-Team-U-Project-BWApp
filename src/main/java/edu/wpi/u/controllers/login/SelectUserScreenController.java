package edu.wpi.u.controllers.login;


import com.jfoenix.controls.JFXButton;
import edu.wpi.u.App;
import edu.wpi.u.users.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tooltip;

import java.io.IOException;

public class SelectUserScreenController {

    public JFXButton loginBtn;
    public JFXButton guestBtn;
    public JFXButton mobileBtn;
    public JFXButton signupBtn;


    public void initialize() throws IOException {
        loginBtn.setTooltip(new Tooltip("Login"));
        guestBtn.setTooltip(new Tooltip("Login as Guest"));
        mobileBtn.setTooltip(new Tooltip("Mobile View"));
        signupBtn.setTooltip(new Tooltip("Sign Up"));

    }

    public void handleLoginButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/UserLoginScreen.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }

    public void handleGuestButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/GuestSigninScreen.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }


    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/CovidSurveyScreen.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }

    public void handleSignUpButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/UserSignupScreen.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }

    public void handleDebugButton(ActionEvent actionEvent) {
        App.getPrimaryStage().setFullScreen(false);
        App.getPrimaryStage().setWidth(1900);
        App.getPrimaryStage().setHeight(1000);
    }

    public void handleSkipToGuestButton(ActionEvent actionEvent) throws IOException {
        App.userService.setGuest("debug");
        App.userService.getActiveUser().setType(Role.GUEST);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
        fxmlLoader.load();
        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }

    public void handleSkipToPatientButton(ActionEvent actionEvent) throws IOException {
        App.userService.setPatient("debug");
        App.userService.getActiveUser().setType(Role.PATIENT);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
        fxmlLoader.load();
        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }

    public void handleSkipToAdminButton(ActionEvent actionEvent) throws IOException {
        App.userService.setEmployee("debug");
        App.userService.getActiveUser().setType(Role.ADMIN);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
        fxmlLoader.load();
        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }

    public void handleMobile() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/mobile/MobileContainer.fxml"));
        fxmlLoader.load();
        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }

    public void handleKiosk() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/kiosk/KioskContainer.fxml"));
        fxmlLoader.load();
        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }
}




