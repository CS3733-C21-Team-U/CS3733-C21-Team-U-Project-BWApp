package edu.wpi.u.controllers.login;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXProgressBar;
import edu.wpi.u.App;
import edu.wpi.u.CachingClassLoader;
import edu.wpi.u.users.Role;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class SelectUserScreenController {
    public JFXButton skipToAdminButton;
    public ImageView loadingImage;
    public VBox loadingFrame;
    public StackPane loadingStackPane;
    public JFXProgressBar progressBar;

    private FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));

    public void initialize() throws IOException {
        progressBar.setVisible(false);
        if (App.useCache.get()) {
            App.loadingSpinnerHerePane = loadingStackPane;
            fxmlLoader.setClassLoader(App.classLoader);
            fxmlLoader.load();
        }
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

    private void load() {
        App.isLoggedIn.set(true);
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("Logging you in...");
        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(App.loadingSpinnerHerePane, content, JFXDialog.DialogTransition.RIGHT);
        dialog.show();
        Platform.runLater(() -> {
            Task<Parent> loadTask = new Task<Parent>() {
                @Override
                protected Parent call() {
                    return fxmlLoader.getRoot();
                }
            };
            loadTask.setOnSucceeded(event -> {
                App.getPrimaryStage().getScene().setRoot(loadTask.getValue());
            });
            loadTask.setOnRunning(event -> {
            });
            Thread t = new Thread(loadTask);
            Thread thread = new Thread(() ->{
                try {
                    Thread.sleep(500);
                    Platform.runLater(t::start);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        });
    }

    public void handleSkipToGuestButton(ActionEvent actionEvent) throws IOException {
        App.userService.setGuest("debug");
        App.userService.getActiveUser().setType(Role.GUEST);
        if (App.useCache.get()){
            load();
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
            fxmlLoader.load();
            fxmlLoader.getController();
            App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
        }
    }

    public void handleSkipToPatientButton(ActionEvent actionEvent) throws IOException {
        App.userService.setPatient("debug");
        App.userService.getActiveUser().setType(Role.PATIENT);
        if (App.useCache.get()){
            load();
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
            fxmlLoader.load();
            fxmlLoader.getController();
            App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
        }
    }

    public void handleSkipToAdminButton(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        App.userService.setEmployee("debug");
        App.userService.getActiveUser().setType(Role.ADMIN);
        if (App.useCache.get()){
            load();
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
            fxmlLoader.load();
            fxmlLoader.getController();
            App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
        }
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




