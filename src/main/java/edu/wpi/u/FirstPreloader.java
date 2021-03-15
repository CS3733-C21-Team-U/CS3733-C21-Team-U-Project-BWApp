package edu.wpi.u;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class FirstPreloader extends Preloader {
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/edu/wpi/u/views/NewMainPage.fxml"));
        fxmlLoader.setClassLoader(App.classLoader);
        fxmlLoader.load();
        fxmlLoader.getController();
        App.base = fxmlLoader.getRoot();
        FXMLLoader fxmlLoader2 = new FXMLLoader(App.class.getResource("/edu/wpi/u/views/login/UserLoginScreen.fxml"));
        fxmlLoader2.setClassLoader(App.classLoader);
        fxmlLoader2.load();
        fxmlLoader2.getController();
        App.loginBase = fxmlLoader2.getRoot();
    }
}