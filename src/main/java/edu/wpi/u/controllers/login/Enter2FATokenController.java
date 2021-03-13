package edu.wpi.u.controllers.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.u.App;
import edu.wpi.u.users.Role;
import io.netty.handler.codec.http.HttpHeaders;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.asynchttpclient.*;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;

public class Enter2FATokenController {
    @FXML public JFXButton loginButton;
    @FXML public JFXTextField tokenTextFIeld;
    @FXML public JFXProgressBar progressBar;
    @FXML public JFXButton enterAppButton;

    SimpleBooleanProperty valid = new SimpleBooleanProperty(false);
    public void initialize(){

    }

    public void handleTokenSubmit() {
        String token = tokenTextFIeld.getText();
        String phoneNumber = App.userService.getActiveUser().getPhoneNumber();
        try {
            System.out.println("Token " + token);
            System.out.println("Phonenumber " + phoneNumber);
            URI uri2 = new URI("https://bw-webapp.herokuapp.com/" +"verify?phonenumber=" + "+1"+ phoneNumber + "&code=" + token);
            URL url2 = uri2.toURL(); // make GET request
            System.out.println("URL: " + url2.toString());
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
        Parent root = App.base;//FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }
}
