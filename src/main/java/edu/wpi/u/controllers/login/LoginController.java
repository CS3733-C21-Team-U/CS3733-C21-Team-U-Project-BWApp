package edu.wpi.u.controllers.login;

import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.u.database.UserData;
import edu.wpi.u.exceptions.AccountNameNotFoundException;
import edu.wpi.u.exceptions.PasswordNotFoundException;
import edu.wpi.u.users.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.sql.SQLOutput;

import static edu.wpi.u.users.StaffType.*;

public class LoginController {


    // TODO: Properly rename JFX artifacts
    @FXML
    public JFXTextField userNameTextField;
    @FXML
    public JFXPasswordField passWordField;
    @FXML
    public JFXButton loginButton;
    @FXML
    public JFXButton forgotPasswordButton;


    public void initialize() throws IOException {

        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Username Required");
        userNameTextField.getValidators().add(validator);
        userNameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                userNameTextField.validate();
            }
        });

        RequiredFieldValidator validator2 = new RequiredFieldValidator();
        validator.setMessage("Password Required");
        passWordField.getValidators().add(validator2);
        passWordField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                passWordField.validate();
            }
        });
    }

    // This function, upon handling login button, will check the accountName and passWord
    // against the database, if this works, the user will be taken to the application, if not
    //the user will recieve an error

    /**
     *
     */

    @FXML
    public void handleLogin() throws IOException, Exception {
        String username = userNameTextField.getText();
        String password = passWordField.getText();

        AccountNameNotFoundException accountException = new AccountNameNotFoundException();
        PasswordNotFoundException passwordException = new PasswordNotFoundException();
        //TODO: Stop from switching windows
        if (!App.userService.checkUsername(username).equals("") || !App.userService.checkPhoneNumber(username).equals("")) {
            if (!App.userService.checkPassword(password).equals("")) {
                App.userService.setUser(username, password, App.userService.checkPassword(password));

                    //switch scene
                    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
//                    Scene scene = new Scene(root);
                App.getPrimaryStage().getScene().setRoot(root);


            } else {
                throw passwordException;
            }
        } else {
            throw accountException;
        }
    }
//Throws exceptions if username or password not found

    public void handleLogin(JFXTextField accountName, JFXPasswordField passWord) {

    }

    public void handleForgotPassword() throws IOException {
        //switch scene
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/ForgotPassword.fxml"));
//                    Scene scene = new Scene(root);
        App.getPrimaryStage().getScene().setRoot(root);


    }

}


