package edu.wpi.u.controllers.login;


import edu.wpi.u.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class GuestSignInScreenController {


    public void initialize() throws IOException {

    }

    public void handleSignInButton(ActionEvent actionEvent) throws IOException {
        //TODO: set active user to guest
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/NewMainPage.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }

    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/SelectUserScreen.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }
}




