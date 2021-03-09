package edu.wpi.u.controllers.login;


import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.u.App;
import edu.wpi.u.users.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class SelectUserScreenController {


    public void initialize() throws IOException {

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
//        App.userService.setUser("patient", "patient", "Guests");
        System.out.println(App.userService.getActiveUser().getName());
        App.userService.getActiveUser().setType(Role.GUEST);
        // todo : fixes the loading issue but won't go to new main page
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
//        fxmlLoader.load();
//        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(App.base);
    }

    public void handleSkipToPatientButton(ActionEvent actionEvent) throws IOException {
        App.userService.setPatient("debug");
//        App.userService.setUser("patient", "patient", "Guests");
        System.out.println(App.userService.getActiveUser().getName());
        App.userService.getActiveUser().setType(Role.PATIENT);
        // todo : fixes the loading issue but won't go to new main page
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
//        fxmlLoader.load();
//        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(App.base);
    }

    public void handleSkipToAdminButton(ActionEvent actionEvent) throws IOException {
        App.userService.setEmployee("debug");
        System.out.println(App.userService.getActiveUser().getName());
        App.userService.getActiveUser().setType(Role.ADMIN);
        // todo : fixes the loading issue but won't go to new main page
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
//        fxmlLoader.load();
//        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(App.base);
    }
}




