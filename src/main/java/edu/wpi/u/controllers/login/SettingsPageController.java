package edu.wpi.u.controllers.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.u.models.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.util.ArrayList;

import static edu.wpi.u.users.StaffType.*;

public class SettingsPageController {


    @FXML public  JFXButton pathMode1Button;
    @FXML public  JFXButton pathMode2Button;
    @FXML public  JFXButton loadCSVButton;
    @FXML public JFXRadioButton setTheme;
    @FXML  public  JFXTextField phoneNumTextField;
    @FXML public  JFXTextField emailAddressTextField;
    @FXML public  JFXButton contactChangeButton;
    @FXML public JFXTextField nodeLocation;
    @FXML public FileChooser fileDirect = new FileChooser();
    @FXML public JFXButton createTableButton;
    @FXML public JFXTextField tableNameTextFIeld;
    public JFXTextField filePathTextField;
    //public JFXButton  createTableButton;


    //most of these functions are just place holders, so I dont
    //have anything to add for stubs / javadocs
    //will need help writing these.
    public void handlePathMode1(){

    }

    public void handlePathMode2(){

    }

    /**
     * This function will open the file explorer and update the textfield with the file name
     * selected in the explorer when the button is pressed
     *
     */
    public String handleLoadCSV(){
      File csv = fileDirect.showOpenDialog(null);
        fileDirect.setTitle("Open Resource File");
       String filePath = "";
       filePathTextField.setText(csv.getPath());
       return csv.getPath();

       // App.db.readCSV(csv.getPath(), tableNameTextFIeld.getText() );

    }

    /** This function intakes two text fields and makes a change to the user in the database one the button is handled.
     */
    public void handleContactChange(){
        String userType = "";
        if (App.userService.getActiveUser().getType() ==  DOCTOR || App.userService.getActiveUser().getType() ==  ADMIN ||
                App.userService.getActiveUser().getType() ==  MAINTENANCE || App.userService.getActiveUser().getType() ==  NURSE ||
                App.userService.getActiveUser().getType() ==  SECURITY_GUARD || App.userService.getActiveUser().getType() ==  TECHNICAL_SUPPORT ||
                App.userService.getActiveUser().getType() ==  TRANSLATORS){
            userType = "Employees";
        } else {
            userType = "Guests";
        }
        App.userService.changeEmail(App.userService.getActiveUser().getUserID(), emailAddressTextField.getText(), userType);


    }

    public void handleCreateTable() {
        App.mapService.loadCSVFile(filePathTextField.getText(), tableNameTextFIeld.getText() );

    }

    public void handleSetTheme1(ActionEvent actionEvent) {
        //Light Theme
    }

    public void handleSetTheme2(ActionEvent actionEvent) {
        //Dark Theme
    }
}
