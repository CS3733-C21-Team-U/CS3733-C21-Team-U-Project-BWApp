package edu.wpi.u;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FirstPreloader extends Preloader {
    public void start(Stage stage) throws Exception {

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