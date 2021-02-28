package edu.wpi.u.controllers.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;

import java.io.IOException;
import java.util.LinkedList;

import javafx.scene.control.*;

import java.util.ArrayList;

public class SettingsPageController {

    public  JFXButton pathMode1Button;
    public  JFXButton pathMode2Button;
    public  JFXButton loadCSVButton;
    public JFXRadioButton setTheme;
    public  JFXTextField phoneNumTextField;
    public  JFXTextField emailAddressTextField;
    public  JFXButton contactChangeButton;
    public JFXTextField nodeLocation;


    //most of these functions are just place holders, so I dont
    //have anything to add for stubs / javadocs
    //will need help writing these.
    public void handlePathMode1(){

    }

    public void handlePathMode2(){

    }

    public void handleSetTheme() {
    }

    /**
     * This function will open the file explorer and update the textfield with the file name
     * selected in the explorer when the button is pressed
     * @param nodeLocation
     */
    public void handleLoadCSV(JFXTextField nodeLocation){

    }

    /** This function intakes two text fields and makes a change to the user in the database one the button is handled.
     *
     * @param phoneNumTextField phone number of user
     * @param emailAddressTextField email address of user
     */
    public void handleContactChange(JFXTextField phoneNumTextField, JFXTextField emailAddressTextField){

    }
}
