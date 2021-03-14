package edu.wpi.u;

import edu.wpi.u.users.Role;
import javafx.application.Preloader;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

import static edu.wpi.u.App.classLoader;

public class FirstPreloader extends Preloader {
    public void start(Stage stage) throws Exception {
//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/edu/wpi/u/views/NewMainPage.fxml"));
//        fxmlLoader.load();
//        fxmlLoader.getController();
//        App.base = fxmlLoader.getRoot();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
        System.out.println("Location1: " +fxmlLoader.getLocation());
        fxmlLoader.setClassLoader(App.classLoader);
        fxmlLoader.load();
//        fxmlLoader.setLocation(getClass().getResource("/edu/wpi/u/views/mobile/MobileContainer.fxml"));
//        System.out.println("Location2: " +fxmlLoader.getLocation());
//        fxmlLoader.load();
//        fxmlLoader.setLocation(getClass().getResource("/edu/wpi/u/views/robot/KioskContainer.fxml"));
//        fxmlLoader.load();
//        fxmlLoader.setLocation(getClass().getResource("/edu/wpi/u/views/login/UserSignupScreen.fxml"));
//        fxmlLoader.load();
//        fxmlLoader.setLocation(getClass().getResource("/edu/wpi/u/views/login/CovidSurveyScreen.fxml"));
//        fxmlLoader.load();
//        fxmlLoader.setLocation(getClass().getResource("/edu/wpi/u/views/login/GuestSigninScreen.fxml"));
//        fxmlLoader.load();
//        System.out.println("Loading login screen");
//        fxmlLoader.setLocation(getClass().getResource("/edu/wpi/u/views/login/UserLoginScreen.fxml"));
//        fxmlLoader.load();
    }
}