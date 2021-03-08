package edu.wpi.u.controllers.login;


import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.u.App;
import edu.wpi.u.users.Appointment;
import edu.wpi.u.users.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.ArrayList;

public class UserSignupScreenController {


    public JFXTextField fullNameTextField;
    public JFXTextField usernameTextField;
    public JFXTextField phonenumberTextField;
    public JFXTextField emailTextField;
    public JFXPasswordField passwordTextField;
    public JFXPasswordField confirmTextField;
    public JFXTextField providerNameTextField;


    public void initialize() throws IOException {

    }

    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/SelectUserScreen.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }

    public void handleSignUpButton(ActionEvent actionEvent) throws IOException {
        System.out.println(fullNameTextField.getText());
        System.out.println(usernameTextField.getText());
        System.out.println(passwordTextField.getText());
        System.out.println(emailTextField.getText());
        System.out.println(phonenumberTextField.getText());
        System.out.println(providerNameTextField.getText());

        App.userService.addPatient(fullNameTextField.getText(), usernameTextField.getText(), passwordTextField.getText(), emailTextField.getText(), Role.PATIENT, phonenumberTextField.getText(), "UDEPT00101", false,new ArrayList<Appointment>(),providerNameTextField.getText(),"UHALL02701",null);
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/UserLoginScreen.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }
}




