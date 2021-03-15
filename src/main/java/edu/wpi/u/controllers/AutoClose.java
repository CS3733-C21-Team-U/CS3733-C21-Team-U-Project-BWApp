package edu.wpi.u.controllers;

import edu.wpi.u.App;
import edu.wpi.u.controllers.login.UserLoginScreenController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class AutoClose {
    private Timeline timer;

    public AutoClose(Stage stage) {

        timer = new Timeline(new KeyFrame(Duration.seconds(180), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent evt) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Inactivity");
                alert.setHeaderText("Connection closed due to inactivity.");
                alert.show();

                try {
                    Stage mainWindowStage = App.getPrimaryStage();

                    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/SelectUserScreen.fxml"));

                    Scene scene = new Scene(root);
                    mainWindowStage.setScene(scene);
                    mainWindowStage.show();

                    timer.stop();
                } catch (IOException ex) {
                }
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

        stage.addEventFilter(MouseEvent.ANY, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                timer.playFromStart();
            }
        });
    }
}