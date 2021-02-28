package edu.wpi.u.controllers.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

public class LoginController {

    public JFXTextField accountName;
    public JFXTextField passWord;
    public JFXButton login;


    // This function, upon handling login button, will check the accountName and passWord
    // against the database, if this works, the user will be taken to the application, if not
    //the user will recieve an error
    /**
     *
     * @param accountName Text field for username
     * @param passWord Text field for Password
     */
    public void handleLogin(JFXTextField accountName, JFXTextField passWord){

    }

}
