package edu.wpi.u.controllers.login;


import com.jfoenix.controls.JFXButton;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;

public class SelectUserScreenController {
    public JFXButton skipToAdminButton;
    public ImageView loadingImage;
    public VBox loadingFrame;

    private FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));

    public void initialize() throws IOException {
        fxmlLoader.setClassLoader(App.classLoader);
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
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Task<Parent> loadTask = new Task<Parent>() {
                    @Override
                    protected Parent call() {
                        return fxmlLoader.getRoot();
                    }
                };
                loadTask.setOnSucceeded(event -> {
                    App.getPrimaryStage().getScene().getStylesheets().add(getClass().getResource("/edu/wpi/u/views/css/BaseStyle.css").toExternalForm());
                    App.getPrimaryStage().getScene().getStylesheets().add(getClass().getResource("/edu/wpi/u/views/css/LightTheme.css").toExternalForm());
                    App.getPrimaryStage().setFullScreen(true);
                    App.getPrimaryStage().setFullScreenExitHint("");
                    App.getPrimaryStage().setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                    App.getPrimaryStage().getScene().setRoot(loadTask.getValue());
                });

                loadTask.setOnRunning(event -> {
                    VBox box = new VBox();
                    ImageView imageView = new ImageView();
                    box.getChildren().add(imageView);
                    box.setAlignment(Pos.CENTER);
                    imageView.setImage(new Image(getClass().getResource("/edu/wpi/u/views/Images/spinner.gif").toExternalForm()));
                    App.getPrimaryStage().setScene(new Scene(box, 1920, 1080));// todo : put this on top of existing elements
                    App.getPrimaryStage().setFullScreen(true);
                    App.getPrimaryStage().show();
                });
                Thread t = new Thread(loadTask);
                t.start();
            }
        });
        //App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }

    public void handleSkipToPatientButton(ActionEvent actionEvent) throws IOException {
        App.userService.setPatient("debug");
        App.userService.getActiveUser().setType(Role.PATIENT);
        App.isLoggedIn.set(true);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Task<Parent> loadTask = new Task<Parent>() {
                    @Override
                    protected Parent call() {
                        return fxmlLoader.getRoot();
                    }
                };
                loadTask.setOnSucceeded(event -> {
                    App.getPrimaryStage().getScene().getStylesheets().add(getClass().getResource("/edu/wpi/u/views/css/BaseStyle.css").toExternalForm());
                    App.getPrimaryStage().getScene().getStylesheets().add(getClass().getResource("/edu/wpi/u/views/css/LightTheme.css").toExternalForm());
                    App.getPrimaryStage().setFullScreen(true);
                    App.getPrimaryStage().setFullScreenExitHint("");
                    App.getPrimaryStage().setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                    App.getPrimaryStage().getScene().setRoot(loadTask.getValue());
                });

                loadTask.setOnRunning(event -> {
                    VBox box = new VBox();
                    ImageView imageView = new ImageView();
                    box.getChildren().add(imageView);
                    box.setAlignment(Pos.CENTER);
                    imageView.setImage(new Image(getClass().getResource("/edu/wpi/u/views/Images/spinner.gif").toExternalForm()));
                    App.getPrimaryStage().setScene(new Scene(box, 1920, 1080));// todo : put this on top of existing elements
                    App.getPrimaryStage().setFullScreen(true);
                    App.getPrimaryStage().show();
                });
                Thread t = new Thread(loadTask);
                t.start();
            }
        });
        //App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }

    public void handleSkipToAdminButton(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        App.userService.setEmployee("debug");
        App.userService.getActiveUser().setType(Role.ADMIN);
        App.isLoggedIn.set(true);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Task<Parent> loadTask = new Task<Parent>() {
                    @Override
                    protected Parent call() {
                        return fxmlLoader.getRoot();
                    }
                };
                loadTask.setOnSucceeded(event -> {
//                    App.getPrimaryStage().getScene().getStylesheets().add(getClass().getResource("/edu/wpi/u/views/css/BaseStyle.css").toExternalForm());
//                    App.getPrimaryStage().getScene().getStylesheets().add(getClass().getResource("/edu/wpi/u/views/css/LightTheme.css").toExternalForm());
//                    App.getPrimaryStage().setFullScreen(true);
//                    App.getPrimaryStage().setFullScreenExitHint("");
//                    App.getPrimaryStage().setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                    App.getPrimaryStage().getScene().setRoot(loadTask.getValue());
                });

                loadTask.setOnRunning(event -> {
                    VBox box = new VBox();
                    ImageView imageView = new ImageView();
                    box.getChildren().add(imageView);
                    box.setAlignment(Pos.CENTER);
                    imageView.setImage(new Image(getClass().getResource("/edu/wpi/u/views/Images/spinner.gif").toExternalForm()));
                    App.getPrimaryStage().setScene(new Scene(box, 1920, 1080));// todo : put this on top of existing elements
                    App.getPrimaryStage().setFullScreen(true);
                    App.getPrimaryStage().show();
                });
                Thread t = new Thread(loadTask);
                t.start();
            }
        });
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




