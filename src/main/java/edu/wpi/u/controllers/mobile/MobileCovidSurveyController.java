package edu.wpi.u.controllers.mobile;


//import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import edu.wpi.u.App;
import edu.wpi.u.requests.*;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MobileCovidSurveyController {

    public JFXCheckBox Q1CheckBox;
    public JFXCheckBox Q2CheckBox;
    public JFXCheckBox Q3CheckBox;
    public JFXCheckBox Q4CheckBox;
    public JFXCheckBox Q5CheckBox;
    public JFXButton submitSurveyButton;
    public Label errorLabel;
    public String covidRisk;
    //public StackPane newStackPane;


    //  Intakes a set of checkbox values and will allow the user to go to the next page if the proper requirements are met
    // if requirements are not met, the user should be sent to another page that directs them to seek help or go home.
    // Will throw an error if any of the check boxes are not completed.
    /**

     */

    public void handleCovidSurveyFill() throws IOException {

        if (!Q1CheckBox.isSelected() &! Q2CheckBox.isSelected() &! Q3CheckBox.isSelected() &! Q4CheckBox.isSelected() & Q5CheckBox.isSelected()){
            covidRisk = "None";
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
            covidRisk = "High";
        }

        //Add request here
        Random rand = new Random();
        int requestID = rand.nextInt();
        String ID = Integer.toString(requestID);//make a random id

        //make components of specifc request,  then set them



        Comment primaryComment = new Comment("COVID Survey Incoming", (App.userService.getActiveUser().getName() + " is making a request to enter the hospital. Their covid risk is " + covidRisk),
                App.userService.getActiveUser().getUserName(), CommentType.PRIMARY);
        Timestamp t = new Timestamp(System.currentTimeMillis());

        ArrayList<String>sdofijds = new ArrayList<>();
        sdofijds.add("UEXIT0010G");
        sdofijds.add("UEXIT0020G");

        Request newRequest = new Request(ID, t,
                sdofijds, new ArrayList<String>(), primaryComment);

        ArrayList<String>sdofijds3 = new ArrayList<>();
        sdofijds3.add(covidRisk);
        SpecificRequest send = new RequestFactory().makeRequest("CovidSurvey").setRequest(newRequest).setSpecificData(sdofijds3);
        if(!App.requestService.getRequests().contains(send)){
            App.requestService.addRequest(send);
        }
        else{
            System.out.println("Request already sent, processing");
        }


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/mobile/MobileWaitPage.fxml"));
        fxmlLoader.load();
        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }

//    public void handleDebugButton(ActionEvent actionEvent) {
//        App.getPrimaryStage().setFullScreen(false);
//        App.getPrimaryStage().setWidth(1900);
//        App.getPrimaryStage().setHeight(1000);
//    }

//    public void handleSkipToGuestButton(ActionEvent actionEvent) throws IOException {
//        App.userService.setGuest("debug");
//        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
//        App.getPrimaryStage().getScene().setRoot(root);
//    }

//    public void handleSkipToPatientButton(ActionEvent actionEvent) throws IOException {
//        App.userService.setPatient("debug", "debug");
//        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
//        App.getPrimaryStage().getScene().setRoot(root);
//    }

//    public void handleSkipToAdminButton(ActionEvent actionEvent) throws IOException {
//        App.userService.setEmployee("debug", "debug");
//        App.userService.getActiveUser().setType(Role.ADMIN);
//        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
//        App.getPrimaryStage().getScene().setRoot(root);
//    }

//    public void handleHelpPage(ActionEvent actionEvent)throws IOException  {
//        JFXDialogLayout content = new JFXDialogLayout();
//        Label header = new Label("Help Page");
//        Text text = new Text("If you have any questions, Please contact us 888-888-8888.If there is an emergency situation, Please call 911");
//        header.getStyleClass().add("headline-2");
//        content.setHeading(header);
//        content.setBody(text);
//        content.getStyleClass().add("dialogue");
//        JFXDialog dialog = new JFXDialog(newStackPane, content, JFXDialog.DialogTransition.CENTER);
//        JFXButton button1 = new JFXButton("Cancel");
//        //JFXButton button2 = new JFXButton("Proceed to Help Page");
//        button1.setOnAction(event -> dialog.close());
//        /*button2.setOnAction(event -> {
//            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
//            Parent root = null;
//            try {
//                root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/MainHelpPage.fxml"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            anchor.getChildren().clear();
//            anchor.getChildren().add(root);
//            dialog.close();
//
//            });*/
//        button1.getStyleClass().add("button-text");
//        ArrayList<Node> actions = new ArrayList<>();
//        actions.add(button1);
//        content.setActions(actions);
//        dialog.show();
//    }
}