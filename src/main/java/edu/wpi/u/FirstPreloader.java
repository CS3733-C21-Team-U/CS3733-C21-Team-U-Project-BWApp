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

public class FirstPreloader extends Preloader {
    ProgressBar bar;
    Stage stage;

    private Scene createPreloaderScene() {
        bar = new ProgressBar();
        BorderPane p = new BorderPane();
        p.setCenter(bar);
        return new Scene(p, 300, 150);
    }

    public void start(Stage stage) throws Exception {
//        Task<Parent> loadTask = new Task<Parent>() {
//            @Override
//            public Parent call() throws IOException, InterruptedException {
//                // simulate long-loading process:
//                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/edu/wpi/u/views/NewMainPage.fxml"));
//                fxmlLoader.load();
//                return fxmlLoader.getRoot();
//            }
//        };
//        loadTask.setOnSucceeded(event -> {
//            App.base = loadTask.getValue();
//        });
//        loadTask.setOnRunning(event -> {
//            this.stage = stage;
//            stage.setScene(createPreloaderScene());
//            stage.show();
//        });
//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/edu/wpi/u/views/NewMainPage.fxml"));
//        fxmlLoader.load();
//        fxmlLoader.getController();
//        App.base = fxmlLoader.getRoot();

    }

//    @Override
//    public void handleProgressNotification(ProgressNotification pn) {
//        bar.setProgress(pn.getProgress());
//    }
//
//    @Override
//    public void handleStateChangeNotification(StateChangeNotification evt) {
//        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
//            stage.hide();
//        }
//    }
}