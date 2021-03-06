package edu.wpi.u.controllers.login;


import edu.wpi.u.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class UserSignupScreenController {




    public void initialize() throws IOException {

    }

    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/SelectUserScreen.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }

    public void handleSignupButton(ActionEvent actionEvent) throws IOException {
        //TODO: Create user!
    }
}




