package edu.wpi.u.controllers.login;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

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
     * @param submitSurveyButton Button to submit the survey
     * @param Q1 Question asking if user has had a positive COVID test result
     * @param Q2 Question asking if user is experiencing COVID symptoms
     * @param Q3 Question asking if user has had direct contact with a COVID infectee
     * @param Q4 Question asking if user has been asked to self isolate
     * @param Q5 Question asking if user feels good / asymptomatic
     */

    public void handleCovidSurveyFill(JFXButton submitSurveyButton, JFXCheckBox Q1, JFXCheckBox Q2, JFXCheckBox Q3, JFXCheckBox Q4, JFXCheckBox Q5){



    }

}
