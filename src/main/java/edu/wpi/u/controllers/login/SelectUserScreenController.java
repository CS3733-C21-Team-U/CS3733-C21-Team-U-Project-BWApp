package edu.wpi.u.controllers.login;


import edu.wpi.u.App;
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

    public void handleSignUpScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/UserSignupScreen.fxml"));
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
}




