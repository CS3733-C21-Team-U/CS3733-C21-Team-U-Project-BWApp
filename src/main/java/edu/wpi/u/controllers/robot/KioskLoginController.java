package edu.wpi.u.controllers.robot;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.users.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.Timestamp;

public class KioskLoginController {

    @FXML public JFXTextField userNameTextField, reasonForVisitLabel;
    @FXML public JFXPasswordField passWordField;
    @FXML public JFXButton submitButton;
    @FXML public Label errorLabel;
    @FXML public JFXButton guestLoginButton;

    public void initialize() throws IOException {

        errorLabel.setVisible(false);
        reasonForVisitLabel.setVisible(false);
        reasonForVisitLabel.setDisable(true);

        userNameTextField.focusedProperty().addListener(e -> errorLabel.setVisible(false));

        passWordField.focusedProperty().addListener(e -> errorLabel.setVisible(false));

        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Username Required");
        userNameTextField.getValidators().add(validator);
        userNameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                userNameTextField.validate();
            }
        });

        RequiredFieldValidator validator4 = new RequiredFieldValidator();
        validator4.setMessage("Username Invalid");
        userNameTextField.getValidators().add(validator4);
        userNameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal){
                if (App.userService.checkUsername(passWordField.getText()).equals("")) {
                    userNameTextField.validate();
                }
            }
        });

        RequiredFieldValidator validator2 = new RequiredFieldValidator();
        validator2.setMessage("Password Required");
        passWordField.getValidators().add(validator2);
        passWordField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                passWordField.validate();
            }
        });
    }

    public void handleReturn() throws IOException {
        App.getPrimaryStage().setFullScreen(true);
        App.getPrimaryStage().setWidth(1920);
        App.getPrimaryStage().setHeight(1080);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/login/SelectUserScreen.fxml"));
        fxmlLoader.load();
        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }

    public void handleLonginWithNo2FA() {
        if(guestLoginButton.getText().equals("Login as Guest")) {
            if (!App.userService.checkUsername(userNameTextField.getText()).equals("")) {
                if (!App.userService.checkPassword(passWordField.getText(), userNameTextField.getText()).equals("")) {
                    App.userService.setUser(userNameTextField.getText(), passWordField.getText(), App.userService.checkUsername(userNameTextField.getText()));
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/robot/KioskCovidSurvey.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    KioskContainerController.getInstance().getMobileRoot().getChildren().clear();
                    KioskContainerController.getInstance().getMobileRoot().getChildren().add(root);
                } else {
                    errorLabel.setVisible(true);
                }
            } else {
                errorLabel.setVisible(true);
            }
        } else{
            App.userService.addGuest(userNameTextField.getText(), new Timestamp(System.currentTimeMillis()), passWordField.getText(), false);
            App.userService.setGuest(userNameTextField.getText());
            App.userService.getActiveUser().setType(Role.GUEST);
            App.userService.getActiveUser().setUserName(userNameTextField.getText());
            App.isLoggedIn.set(!App.isLoggedIn.get());
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/robot/KioskCovidSurvey.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            KioskContainerController.getInstance().getMobileRoot().getChildren().clear();
            KioskContainerController.getInstance().getMobileRoot().getChildren().add(root);
        }
    }

    public void handleToggleGuestLogin() {
        if (guestLoginButton.getText().equals("Login as Guest")){
            guestLoginButton.setText("Login with Account");
            userNameTextField.setPromptText("Full Name");
            reasonForVisitLabel.setVisible(true);
            reasonForVisitLabel.setDisable(false);
            passWordField.setVisible(false);
            passWordField.setDisable(true);
        }else{
            guestLoginButton.setText("Login as Guest");
            userNameTextField.setPromptText("Enter Username");
            reasonForVisitLabel.setVisible(false);
            reasonForVisitLabel.setDisable(true);
            passWordField.setVisible(true);
            passWordField.setDisable(false);
        }
    }
}
