package edu.wpi.u.controllers.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.database.Database;
import edu.wpi.u.exceptions.FilePathNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import edu.wpi.u.App;

import java.io.File;
import java.io.IOException;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;

import static edu.wpi.u.users.StaffType.*;

public class SettingsPageController {


    @FXML
    public JFXButton loadCSVButton;
    @FXML
    public JFXTextField phoneNumTextField;
    @FXML
    public JFXTextField emailAddressTextField;
    @FXML
    public JFXButton contactChangeButton;
    @FXML
    public JFXButton createTableButton;
    @FXML
    public JFXTextField tableNameTextFIeld;
    @FXML
    public JFXTextField filePathTextField;
    @FXML
    public Label subtitleText;
    @FXML
    public Label pathfindingText;
    @FXML
    public Label adminText;

    public FileChooser fileDirect = new FileChooser();
    @FXML public JFXRadioButton aStarRadioButton;
    public ToggleGroup pathFindingMode;
    @FXML public JFXRadioButton dFSRadioButton;
    @FXML public JFXRadioButton bFSRadioButton;
    public JFXTextField tableNameTextFIeld1;
    @FXML public JFXComboBox<String> tableNameOptions;
    @FXML public Group onlyAdmin;

    public void initialize() throws IOException, FilePathNotFoundException {

        if(App.userService.getActiveUser().getType() ==  ADMIN){
            onlyAdmin.setStyle("-fx-opacity: 1");
            onlyAdmin.setDisable(false);
        }
        else if(!(App.userService.getActiveUser().getType() ==  ADMIN)){
            onlyAdmin.setStyle("-fx-opacity: 0");
            onlyAdmin.setDisable(true);
        }


        tableNameOptions.setItems(FXCollections.observableArrayList("Nodes","Edges","Requests","Employees","Guests","Assignments","Locations","Permissions","Maintenance","Laundry","Sanitation","AudioVisual","Floral","Medical","Religious","Computer","Security","Language","Gift"));

        // TODO: ADD REGEX FUNCTIONALITY TO THIS
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("File Required");
        filePathTextField.getValidators().add(validator);
        filePathTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                filePathTextField.validate();
            }
        });
        // TODO: ADD REGEX FUNCTIONALITY TO THIS
        RequiredFieldValidator validator2 = new RequiredFieldValidator();
        validator.setMessage("Email Required");
        emailAddressTextField.getValidators().add(validator2);
        emailAddressTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                emailAddressTextField.validate();
            }
        });
        // TODO: ADD REGEX FUNCTIONALITY TO THIS
        RequiredFieldValidator validator3 = new RequiredFieldValidator();
        validator.setMessage("Phone # Required");
        phoneNumTextField.getValidators().add(validator2);
        phoneNumTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                emailAddressTextField.validate();
            }
        });


        if (!(App.userService.getActiveUser().getType() == ADMIN)) {
            adminText.setStyle("-fx-opacity: 0");
            subtitleText.setStyle("-fx-opacity: 0");
            pathfindingText.setStyle("-fx-opacity: 0");
            filePathTextField.setStyle("-fx-opacity: 0");
            loadCSVButton.setStyle("-fx-opacity: 0");
            tableNameTextFIeld.setStyle("-fx-opacity: 0");
            createTableButton.setStyle("-fx-opacity: 0");

            pathfindingText.setDisable(true);
            filePathTextField.setDisable(true);
            createTableButton.setDisable(true);
            loadCSVButton.setDisable(true);

        }
    }

    //most of these functions are just place holders, so I dont
    //have anything to add for stubs / javadocs
    //will need help writing these.
    public void handlePathMode1() {

    }

    public void handlePathMode2() {

    }

    /**
     * This function will open the file explorer and update the textfield with the file name selected in the explorer
     * when the button is pressed
     */
    public String handleLoadCSV() throws FilePathNotFoundException {
        //creating file
        File csv = null;
        //checking if path is valid

        try {
            csv = fileDirect.showOpenDialog(null);
        } catch (Exception e) {
            throw new FilePathNotFoundException();
        }
        fileDirect.setTitle("Open Resource File");
        String filePath = "";
        filePathTextField.setText(csv.getPath());
        App.mapInteractionModel.pathFlag.set(String.valueOf(Math.random()));

        return csv.getPath();

        // App.db.readCSV(csv.getPath(), tableNameTextFIeld.getText() );

    }

    public void handleContactChange() {
        String userType = "";
        if (App.userService.getActiveUser().getType() == DOCTOR || App.userService.getActiveUser().getType() == ADMIN ||
                App.userService.getActiveUser().getType() == MAINTENANCE || App.userService.getActiveUser().getType() == NURSE ||
                App.userService.getActiveUser().getType() == SECURITY_GUARD || App.userService.getActiveUser().getType() == TECHNICAL_SUPPORT ||
                App.userService.getActiveUser().getType() == TRANSLATORS) {
            userType = "Employees";
        } else {
            userType = "Guests";
        }
        System.out.println(App.userService.getActiveUser().getUserID());
        System.out.println(emailAddressTextField.getText());
        System.out.println(userType);
        App.userService.changeEmail(App.userService.getActiveUser().getUserID(), emailAddressTextField.getText(), userType);
    }

    public void handleCreateTable() {
        App.mapService.loadCSVFile(filePathTextField.getText(), tableNameTextFIeld.getText());
    }

    /*public void handleSetTheme1(ActionEvent actionEvent) {
        //Light Theme
    }*/

    /*public void handleSetTheme2(ActionEvent actionEvent) {
        //Dark Theme
    }*/


    public void handleAStar(ActionEvent actionEvent) {
        App.pathfindingAlgorithm = "ASTAR";
    }

    public void handleDepthFirst(ActionEvent actionEvent) {
        App.pathfindingAlgorithm = "DFS";
    }

    public void handleBreadthFirst(ActionEvent actionEvent) {
        App.pathfindingAlgorithm = "BFS";
    }


    public void handleSaveCSV(ActionEvent actionEvent) {
        Database.getDB().saveCSV(tableNameTextFIeld1.getText(),filePathTextField.getText(),"Anything I want");
    }
}

    /*public void handleSetTheme1(ActionEvent actionEvent) {
        //Light Theme
    }*/

    /*public void handleSetTheme2(ActionEvent actionEvent) {
        //Dark Theme
    }*/

