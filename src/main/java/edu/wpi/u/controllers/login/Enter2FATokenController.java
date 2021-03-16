package edu.wpi.u.controllers.login;

import com.jfoenix.controls.*;
import edu.wpi.u.App;
import edu.wpi.u.users.Role;
import io.netty.handler.codec.http.HttpHeaders;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import org.asynchttpclient.*;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.Future;

public class Enter2FATokenController {
    @FXML public JFXButton loginButton;
    @FXML public JFXTextField tokenTextFIeld;
    @FXML public JFXProgressBar progressBar;
    @FXML public JFXButton enterAppButton;

    SimpleBooleanProperty valid = new SimpleBooleanProperty(false);

    private FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));

    public void initialize() throws IOException {
        fxmlLoader.setClassLoader(App.classLoader);
        fxmlLoader.load();
    }

    public void handleTokenSubmit() {
        String token = tokenTextFIeld.getText();
        String phoneNumber = App.userService.getActiveUser().getPhoneNumber();
        try {
            URI uri2 = new URI("https://bw-webapp.herokuapp.com/" +"verify?phonenumber=" + "+1"+ phoneNumber + "&code=" + token);
            URL url2 = uri2.toURL(); // make GET request
            AsyncHttpClient client = Dsl.asyncHttpClient();
            Future<Response> whenResponse = client.prepareGet(url2.toString()).execute();
            Response response = whenResponse.get();
            String resString = response.getResponseBody();
            if(resString.contains("approved")){
                handleAppEntry();
            }
    } catch (Exception e) {
        e.printStackTrace();
    }

    }

    public void handleForgotPassword(ActionEvent actionEvent) {
    }

    public void handleGoBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/UserLoginScreen.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }

    public void handleAppEntry() throws IOException {
        if (App.useCache.get()){
            loadingNewMainPage();
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(500);
                    Platform.runLater(() -> {
//                        App.isLoggedIn.set(true);
//                        App.getPrimaryStage().getScene().setRoot(App.base);
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
                        try {
                            fxmlLoader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        fxmlLoader.getController();
                        App.isLoggedIn.set(true);
                        App.tabPaneRoot.getSelectionModel().selectFirst();
                        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Error("Unexpected interruption");
                }
            });
            thread.start();
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
            fxmlLoader.load();
            fxmlLoader.getController();
            App.isLoggedIn.set(true);
            App.tabPaneRoot.getSelectionModel().selectFirst();
            App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
        }

        //Parent root = App.base;//FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
        //App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }
    private void loadingNewMainPage() {
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("Logging you in...");
        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(App.loadingSpinnerHerePane, content, JFXDialog.DialogTransition.RIGHT);
        dialog.show();
    }
}
