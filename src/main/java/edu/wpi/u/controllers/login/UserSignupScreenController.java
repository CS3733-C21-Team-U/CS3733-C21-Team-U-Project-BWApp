package edu.wpi.u.controllers.login;


import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.u.App;
import edu.wpi.u.users.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class UserSignupScreenController {


    public JFXTextField fullNameTextField;
    public JFXTextField usernameTextField;
    public JFXTextField phonenumberTextField;
    public JFXTextField emailTextField;
    public JFXPasswordField passwordTextField;
    public JFXPasswordField confirmTextField;


    public void initialize() throws IOException {

    }

    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/SelectUserScreen.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }

    public void handleSignupButton(ActionEvent actionEvent) throws IOException {
        //TODO: Create patient!
        App.userService.addPatient(fullNameTextField.getText(), usernameTextField.getText(), passwordTextField.getText(), emailTextField.getText(), Role.PATIENT, phonenumberTextField.getText(), null, false,null,null,null,null);
    }

    public void handleSignUpButton(ActionEvent actionEvent) {
    }
}




