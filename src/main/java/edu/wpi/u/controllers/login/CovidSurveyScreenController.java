package edu.wpi.u.controllers.login;


//import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import edu.wpi.u.App;
import edu.wpi.u.users.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

public class CovidSurveyScreenController {

    public JFXCheckBox Q1CheckBox;
    public JFXCheckBox Q2CheckBox;
    public JFXCheckBox Q3CheckBox;
    public JFXCheckBox Q4CheckBox;
    public JFXCheckBox Q5CheckBox;
    public JFXButton submitSurveyButton;
    public Label errorLabel;
    public StackPane newStackPane;


    //  Intakes a set of checkbox values and will allow the user to go to the next page if the proper requirements are met
    // if requirements are not met, the user should be sent to another page that directs them to seek help or go home.
    // Will throw an error if any of the check boxes are not completed.

    public void handleCovidSurveyFill() throws IOException {

        if (!Q1CheckBox.isSelected() &! Q2CheckBox.isSelected() &! Q3CheckBox.isSelected() &! Q4CheckBox.isSelected() & Q5CheckBox.isSelected()){
                Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/SelectUserScreen.fxml"));
                App.getPrimaryStage().getScene().setRoot(root);
                //App.covidData.insertData(false); // TODO: come back to this
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
            //App.covidData.insertData(true);
        }
    }

    public void handleDebugButton(ActionEvent actionEvent) {
        App.getPrimaryStage().setFullScreen(false);
        App.getPrimaryStage().setWidth(1900);
        App.getPrimaryStage().setHeight(1000);
    }

    public void handleSkipToGuestButton(ActionEvent actionEvent) throws IOException {
        App.userService.setGuest("debug");
        App.userService.getActiveUser().setType(Role.GUEST);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
        Object obj = fxmlLoader.load();
        Object myController = fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }

    public void handleSkipToPatientButton(ActionEvent actionEvent) throws IOException {
        App.userService.setPatient("debug");
        App.userService.getActiveUser().setType(Role.PATIENT);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
        Object obj = fxmlLoader.load();
        Object myController = fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }

    public void handleSkipToAdminButton(ActionEvent actionEvent) throws IOException {
        App.userService.setEmployee("debug");
        System.out.println(App.userService.getActiveUser().getName());
        App.userService.getActiveUser().setType(Role.ADMIN);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
        fxmlLoader.load();
        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }

    public void handleHelpPage(ActionEvent actionEvent)throws IOException  {
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("Help Page");
        Text text = new Text("If you have any questions, Please contact us 888-888-8888.If there is an emergency situation, Please call 911");
        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.setBody(text);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("Cancel");
        //JFXButton button2 = new JFXButton("Proceed to Help Page");
        button1.setOnAction(event -> dialog.close());
        /*button2.setOnAction(event -> {
            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/MainHelpPage.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            anchor.getChildren().clear();
            anchor.getChildren().add(root);
            dialog.close();

            });*/
        button1.getStyleClass().add("button-text");
        ArrayList<Node> actions = new ArrayList<>();
        actions.add(button1);
        content.setActions(actions);
        dialog.show();
    }
}