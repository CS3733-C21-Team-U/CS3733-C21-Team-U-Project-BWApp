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

public class LoginController {

    @FXML
    public JFXTextField accountName;
    @FXML
    public JFXPasswordField passWord;
    @FXML
    public JFXButton login;
    @FXML
    public JFXButton forgotPassword;
    @FXML
    public JFXButton navButton;

    // This function, upon handling login button, will check the accountName and passWord
    // against the database, if this works, the user will be taken to the application, if not
    //the user will recieve an error

    /**
     *
     */
    @FXML
    public void handleLogin() {
        String username = accountName.getText();
        String password = passWord.getText();
        try {
            if (!App.userService.checkUsername(username).equals("")) {
                if (!App.userService.checkPassword(password).equals("")) {
                    App.userService.setUser(username, password, App.userService.checkPassword(password));
                }
                else {
                    throw new PasswordNotFoundException();
                }
            }
            else {
                throw new AccountNameNotFoundException();
            }
        } catch(Exception e){
            AccountNameNotFoundException accountException = new AccountNameNotFoundException();
            accountException.description = username + " not found in system.";
            PasswordNotFoundException passwordException = new PasswordNotFoundException();
            passwordException.description = password + " not associated with account. Check username or click Forgot Password.";
        }
//Throws exceptions if username or password not found
    }
}


