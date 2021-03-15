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
        if (App.useCache.get()){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
            fxmlLoader.setClassLoader(App.classLoader);
            fxmlLoader.load();
        }
        else {
            System.out.println("Preloading without cache");
        }
    }
}