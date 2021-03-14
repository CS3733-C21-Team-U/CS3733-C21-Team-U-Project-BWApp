package edu.wpi.u.controllers.login;


import com.jfoenix.controls.JFXButton;
import edu.wpi.u.App;
import edu.wpi.u.CachingClassLoader;
import edu.wpi.u.users.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class SelectUserScreenController {
    public JFXButton skipToAdminButton;

    private FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));

    public void initialize() throws IOException {
        fxmlLoader.setClassLoader(App.classLoader);
        System.out.println("Loading in selectUserScreen");
        fxmlLoader.load();
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
        App.isLoggedIn.set(true);
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }

    public void handleSkipToPatientButton(ActionEvent actionEvent) throws IOException {
        App.userService.setPatient("debug");
        App.userService.getActiveUser().setType(Role.PATIENT);
        App.isLoggedIn.set(true);
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
//        fxmlLoader.load();
//        fxmlLoader.getController();
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
//        fxmlLoader.setClassLoader(App.classLoader);
//        System.out.println("Loading in selectUserScreen");
//        fxmlLoader.load();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }

    public void handleSkipToAdminButton(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        App.userService.setEmployee("debug");
        App.userService.getActiveUser().setType(Role.ADMIN);
        App.isLoggedIn.set(true);
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
//        fxmlLoader.setClassLoader(App.classLoader);
//        System.out.println("Loading in selectUserScreen");
//        fxmlLoader.load();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }

    public void handleMobile() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/mobile/MobileContainer.fxml"));
        fxmlLoader.load();
        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }

    public void handleKiosk() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/robot/KioskContainer.fxml"));
        fxmlLoader.load();
        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }

    public void handleExitButton(){
        App.getInstance().exitApp();
    }

    public void handleExitApp(){
        App.getInstance().exitApp();
    }
}




