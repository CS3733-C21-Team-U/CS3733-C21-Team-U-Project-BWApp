package edu.wpi.u.controllers.login;

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

public class LoginController {

    @FXML
    public JFXTextField accountName;
    public JFXPasswordField passWord;
    public JFXButton login;
    public JFXButton forgotPasswordButton;


    // This function, upon handling login button, will check the accountName and passWord
    // against the database, if this works, the user will be taken to the application, if not
    //the user will recieve an error

    /**
     *
     */

    @FXML
    public void handleLogin() throws IOException {
        String username = accountName.getText();
        String password = passWord.getText();
        //TODO: Stop from switching windows
        try {
            if (!App.userService.checkUsername(username).equals("")) {
                if (!App.userService.checkPassword(password).equals("")) {
                    App.userService.setUser(username, password, App.userService.checkPassword(password));
                    //switch scene
                    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
//                    Scene scene = new Scene(root);
                    App.getPrimaryStage().getScene().setRoot(root);
                } else {
                    throw new PasswordNotFoundException();
                }
            } else {
                throw new AccountNameNotFoundException();
            }
        } catch (Exception e) {
            AccountNameNotFoundException accountException = new AccountNameNotFoundException();
            accountException.description = username + " not found in system.";
            PasswordNotFoundException passwordException = new PasswordNotFoundException();
            passwordException.description = password + " not associated with account. Check username or click Forgot Password.";
        }
    }
//Throws exceptions if username or password not found

    public void handleLogin(JFXTextField accountName, JFXPasswordField passWord){

    }

    public  void handleForgotPassword() throws IOException {
        //switch scene
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/ForgotPassword.fxml"));
//                    Scene scene = new Scene(root);
        App.getPrimaryStage().getScene().setRoot(root);


    }

}


