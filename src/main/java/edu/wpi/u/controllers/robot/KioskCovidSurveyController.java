package edu.wpi.u.controllers.robot;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.u.App;
import edu.wpi.u.requests.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

public class KioskCovidSurveyController {

    public JFXCheckBox Q1CheckBox;
    public JFXCheckBox Q2CheckBox;
    public JFXCheckBox Q3CheckBox;
    public JFXCheckBox Q4CheckBox;
    public JFXCheckBox Q5CheckBox;
    public JFXButton submitSurveyButton;
    public Label errorLabel;
    public String covidRisk;



    public void handleCovidSurveyFill() {
        if (!Q1CheckBox.isSelected() && !Q2CheckBox.isSelected() && !Q3CheckBox.isSelected() && !Q4CheckBox.isSelected() && Q5CheckBox.isSelected()){
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

        ArrayList<String> Locations = new ArrayList<>();
        Locations.add("UEXIT0010G");
        Locations.add("UEXIT0020G");

        Request newRequest = new Request(ID, t,
                Locations, new ArrayList<String>(), primaryComment);

        ArrayList<String>specificData = new ArrayList<>();
        specificData.add(covidRisk);
        specificData.add("Temperature Not Taken");
        SpecificRequest send = new RequestFactory().makeRequest("CovidSurvey").setRequest(newRequest).setSpecificData(specificData);
        if(!App.requestService.getRequests().contains(send)){
            App.requestService.addRequest(send);
        }
        else{
            System.out.println("Request already sent, processing");
        }
        App.requestService.curCovidRequest = send;

        String fxmlLocation = "/edu/wpi/u/views/robot/KioskTemperatureScanner.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlLocation));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }
}
