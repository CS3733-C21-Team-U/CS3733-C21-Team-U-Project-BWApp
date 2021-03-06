package edu.wpi.u.controllers.login;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import edu.wpi.u.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

public class COVIDController {

    public JFXCheckBox Q1CheckBox;
    public JFXCheckBox Q2CheckBox;
    public JFXCheckBox Q3CheckBox;
    public JFXCheckBox Q4CheckBox;
    public JFXCheckBox Q5CheckBox;
    public JFXButton submitSurveyButton;
    public Label errorLabel;
    @FXML public StackPane newStackPane;


    //  Intakes a set of checkbox values and will allow the user to go to the next page if the proper requirements are met
    // if requirements are not met, the user should be sent to another page that directs them to seek help or go home.
    // Will throw an error if any of the check boxes are not completed.
    /**

     */

    public void handleCovidSurveyFill() throws IOException {
        if (!Q1CheckBox.isSelected() &! Q2CheckBox.isSelected() &! Q3CheckBox.isSelected() &! Q4CheckBox.isSelected() & Q5CheckBox.isSelected()){
                Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/UserLogin.fxml"));
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

    public void handleHelpPageButton() throws IOException{
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("Help Page");
        Text text = new Text("Please be responsible with your selection. If you have any questions, Please contact 888-888-8888.");
        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.setBody(text);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("CANCEL");
        //JFXButton button2 = new JFXButton("EXIT");
        button1.setOnAction(event -> dialog.close());
            /*button2.setOnAction(event -> {
                dialog.close();
                App.getInstance().end();
            });*/
        button1.getStyleClass().add("button-text");
        //button2.getStyleClass().add("button-contained");
        ArrayList<Node> actions = new ArrayList<>();
        actions.add(button1);
        //actions.add(button2);
        content.setActions(actions);
        dialog.show();

    }
}

