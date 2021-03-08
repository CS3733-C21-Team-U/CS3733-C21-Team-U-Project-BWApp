package edu.wpi.u.controllers.login;


import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.u.App;
import edu.wpi.u.users.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.sql.Timestamp;

public class GuestSignInScreenController {


    public JFXTextField nameGuestTextField;
    public JFXTextArea visitReasonTextField;

    public void initialize() throws IOException {

    }

    public void handleSignInButton(ActionEvent actionEvent) throws IOException {
        //TODO: set active user to guest
        Timestamp t = new Timestamp(System.currentTimeMillis());
        App.userService.addGuest(nameGuestTextField.getText(), t, visitReasonTextField.getText(), false);
        App.userService.setGuest(nameGuestTextField.getText());
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/NewMainPage.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }

    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/SelectUserScreen.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }
}




