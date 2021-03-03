package edu.wpi.u.controllers.login;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.u.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class COVIDController {

    public JFXCheckBox Q1CheckBox;
    public JFXCheckBox Q2CheckBox;
    public JFXCheckBox Q3CheckBox;
    public JFXCheckBox Q4CheckBox;
    public JFXCheckBox Q5CheckBox;
    public JFXButton submitSurveyButton;


    //  Intakes a set of checkbox values and will allow the user to go to the next page if the proper requirements are met
    // if requirements are not met, the user should be sent to another page that directs them to seek help or go home.
    // Will throw an error if any of the check boxes are not completed.
    /**

     */

    public void handleCovidSurveyFill() throws IOException {
            if (!Q1CheckBox.isSelected() &! Q2CheckBox.isSelected() &! Q3CheckBox.isSelected() &! Q4CheckBox.isSelected() & Q5CheckBox.isSelected()){
                Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/UserLogin.fxml"));
                App.getPrimaryStage().getScene().setRoot(root);
        }
    }
}
