package edu.wpi.u.controllers.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.u.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javax.sound.midi.Soundbank;

import java.io.IOException;

import static edu.wpi.u.users.StaffType.*;

public class ResetPasswordPageController {
    public JFXTextField resetPasswordTextField;
    public JFXButton resetButton;
    public JFXRadioButton employeeRadioButton;
    public JFXTextField usernameTextField;

    public void handleResetButton(ActionEvent actionEvent) throws IOException {
        String userType;
        if (employeeRadioButton.isSelected()){
            userType = "Employees";
        } else {
            userType = "Guests";
        }
        System.out.println(userType);
        App.userService.changePassword(usernameTextField.getText(), resetPasswordTextField.getText(), userType);
        //System.out.println(App.userService.getPassword(usernameTextField.getText(), userType));
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/UserLogin.fxml"));
//                    Scene scene = new Scene(root);
        App.getPrimaryStage().getScene().setRoot(root);
    }
}
