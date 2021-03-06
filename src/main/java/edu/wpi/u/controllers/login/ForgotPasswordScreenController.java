package edu.wpi.u.controllers.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.exceptions.AccountNameNotFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class ForgotPasswordScreenController {

    public JFXTextField resetPasswordTextField;
    public JFXButton resetButton;
    public JFXCheckBox employeeCheckBox;
    public JFXTextField usernameTextField;

    public void initialize() throws IOException {

        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Username Required");
        usernameTextField.getValidators().add(validator);
        usernameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                usernameTextField.validate();
            }
        });

        RequiredFieldValidator validator2 = new RequiredFieldValidator();
        validator.setMessage("Password Required");
        resetPasswordTextField.getValidators().add(validator2);
        resetPasswordTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                resetPasswordTextField.validate();
            }
        });
    }

    public void handleResetButton(ActionEvent actionEvent) throws IOException, AccountNameNotFoundException {
        String username = usernameTextField.getText();
        String userType;
        if (employeeCheckBox.isSelected()) {
            userType = "Employees";
        } else {
            userType = "Guests";
        }
        System.out.println("Username in controller: " + username);
        System.out.println(App.userService.checkUsername(username).equals(""));
        if (!App.userService.checkUsername(username).equals("")) {
            App.userService.changePassword(username, resetPasswordTextField.getText(), userType);
            //System.out.println(App.userService.getPassword(usernameTextField.getText(), userType));
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/UserLoginScreen.fxml"));
            App.getPrimaryStage().getScene().setRoot(root);
        } else {
            throw new AccountNameNotFoundException();
        }
    }

    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/UserLoginScreen.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }
}
