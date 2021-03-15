package edu.wpi.u;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

        FXMLLoader fxmlLoader3 = new FXMLLoader(App.class.getResource("/edu/wpi/u/views/login/GuestSigninScreen.fxml"));
        fxmlLoader3.setClassLoader(App.classLoader);
        fxmlLoader3.load();
        fxmlLoader3.getController();
        App.guestBase = fxmlLoader3.getRoot();
    }

    public void readTheme() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("themes.txt")));
            String[] columns = content.split("\n", 1);
            App.themeString = columns[0];
        } catch (IOException e) {
            App.themeString = "PURPLE";
            e.printStackTrace();
        }
    }
}