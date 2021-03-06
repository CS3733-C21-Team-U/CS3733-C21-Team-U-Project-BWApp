package edu.wpi.u.controllers.login;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.u.App;
import edu.wpi.u.users.StaffType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;

public class CovidSurveyScreenController {

    public JFXCheckBox Q1CheckBox;
    public JFXCheckBox Q2CheckBox;
    public JFXCheckBox Q3CheckBox;
    public JFXCheckBox Q4CheckBox;
    public JFXCheckBox Q5CheckBox;
    public JFXButton submitSurveyButton;
    public Label errorLabel;


    //  Intakes a set of checkbox values and will allow the user to go to the next page if the proper requirements are met
    // if requirements are not met, the user should be sent to another page that directs them to seek help or go home.
    // Will throw an error if any of the check boxes are not completed.
    /**

     */

    public void handleCovidSurveyFill() throws IOException {
        if (!Q1CheckBox.isSelected() &! Q2CheckBox.isSelected() &! Q3CheckBox.isSelected() &! Q4CheckBox.isSelected() & Q5CheckBox.isSelected()){
                Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/SelectUserScreen.fxml"));
                App.getPrimaryStage().getScene().setRoot(root);
        }else{
            errorLabel.setText("We are sorry you are not feeling well or have been in contact with a COVID positive person. \n" +
                    "Please call our office at 508-831-5520 between the hours of 9:00am and 4:30pm. \n" +
                    "\n" +
                    "Stay at home except for medical care. \n" +
                    "Do not go to work, school, or public areas until told to do so by a Medical Provider. \n" +
                    "\n" +
                    "For more information on how to take care of yourself: CDC COVID Care Resource. \n" +
                    "\n" +
                    "If you have an emergency, please call campus police/5555 or 9-1-1.\n");
        }
    }

    public void handleDebugButton(ActionEvent actionEvent) {
        App.getPrimaryStage().setFullScreen(false);
        App.getPrimaryStage().setWidth(1900);
        App.getPrimaryStage().setHeight(1000);
    }

    public void handleSkipToGuestButton(ActionEvent actionEvent) throws IOException {
        App.userService.setUser("patient", "patient", "Guests");
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }

    public void handleSkipToPatientButton(ActionEvent actionEvent) throws IOException {
        //TODO: the set user!
//        App.userService.setUser("patient", "patient", "Guests");
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }

    public void handleSkipToAdminButton(ActionEvent actionEvent) throws IOException {
        //TODO: this is broken!!!
        App.userService.setUser("admin", "admin", "Admin");
        App.userService.getActiveUser().setType(StaffType.ADMIN);
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }
}
