package edu.wpi.u.controllers.login;

import edu.wpi.u.App;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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

    private UserData userData;


    // This function, upon handling login button, will check the accountName and passWord
    // against the database, if this works, the user will be taken to the application, if not
    //the user will recieve an error
    /**
     *
     * @param accountName Text field for username
     * @param passWord Text field for Password
     */
    @FXML
    public void handleLogin(){
        String username = accountName.getText();
        String password = passWord.getText();

        if(username.equals() && password.equals(userData.getPassword())){

        }

    }

}
