package edu.wpi.u.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.database.Database;
import edu.wpi.u.exceptions.FilePathNotFoundException;
import edu.wpi.u.users.Role;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import edu.wpi.u.App;

import java.io.File;
import java.io.IOException;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;

import static edu.wpi.u.users.Role.*;

public class SettingsBaseController {


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
    @FXML public JFXComboBox<String> tableNameOptions;
    @FXML public Group onlyAdmin;
    @FXML public Label passwordsDontMatchLabel, wrongPasswordLable,succsessfulLabel,contactInfoLabel,errorUpdateContactLabel;
    @FXML public JFXTextField oldPasswordFeild,newPasswordFeild1,newPasswordFeild2;
    public JFXToggleButton emailNotifications;
    public JFXToggleButton textNotifications;

    public void initialize() throws IOException, FilePathNotFoundException {
        passwordsDontMatchLabel.setVisible(false);
        wrongPasswordLable.setVisible(false);
        succsessfulLabel.setVisible(false);
        contactInfoLabel.setVisible(false);
        errorUpdateContactLabel.setVisible(false);

        phoneNumTextField.focusedProperty().addListener(e->{
            contactInfoLabel.setVisible(false);
            errorUpdateContactLabel.setVisible(false);
        });

        emailAddressTextField.focusedProperty().addListener(e->{
            contactInfoLabel.setVisible(false);
            errorUpdateContactLabel.setVisible(false);
        });

        oldPasswordFeild.focusedProperty().addListener(e->{
            wrongPasswordLable.setVisible(false);
            succsessfulLabel.setVisible(false);
        });

        newPasswordFeild1.focusedProperty().addListener(e->{
            passwordsDontMatchLabel.setVisible(false);
            succsessfulLabel.setVisible(false);
        });

        newPasswordFeild2.focusedProperty().addListener(e->{
            passwordsDontMatchLabel.setVisible(false);
            succsessfulLabel.setVisible(false);
        });
        App.isLoggedIn.addListener((observable, oldValue, newValue) -> {
            if(App.userService.getActiveUser().getType() ==  Role.ADMIN){
                onlyAdmin.setStyle("-fx-opacity: 1");
                onlyAdmin.setDisable(false);
            }
            else if(!(App.userService.getActiveUser().getType() ==  Role.ADMIN)){
                onlyAdmin.setStyle("-fx-opacity: 0");
                onlyAdmin.setDisable(true);
            }

            if (!(App.userService.getActiveUser().getType() == ADMIN)) {
                adminText.setStyle("-fx-opacity: 0");
                subtitleText.setStyle("-fx-opacity: 0");
                pathfindingText.setStyle("-fx-opacity: 0");
                filePathTextField.setStyle("-fx-opacity: 0");
                loadCSVButton.setStyle("-fx-opacity: 0");
                tableNameOptions.setStyle("-fx-opacity: 0");
                createTableButton.setStyle("-fx-opacity: 0");

                pathfindingText.setDisable(true);
                filePathTextField.setDisable(true);
                createTableButton.setDisable(true);
                loadCSVButton.setDisable(true);

            }
        });


        tableNameOptions.setItems(FXCollections.observableArrayList("Nodes","Edges"));

        // TODO: ADD REGEX FUNCTIONALITY TO THIS
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("File Required");
        filePathTextField.getValidators().add(validator);
        filePathTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                filePathTextField.validate();
            }
        });



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

    }

    public void handleContactChange() {
        if(phoneNumTextField.getText().equals("") && emailAddressTextField.getText().equals("")){
            errorUpdateContactLabel.setVisible(true);
        }else {
            if(!phoneNumTextField.getText().equals("")){
                String userType = "";
                if (App.userService.getActiveUser().getType() == DOCTOR || App.userService.getActiveUser().getType() == ADMIN ||
                        App.userService.getActiveUser().getType() == MAINTENANCE || App.userService.getActiveUser().getType() == NURSE ||
                        App.userService.getActiveUser().getType() == SECURITY_GUARD || App.userService.getActiveUser().getType() == TECHNICAL_SUPPORT ||
                        App.userService.getActiveUser().getType() == TRANSLATORS) {
                    userType = "Employees";
                } else {
                    userType = "Guests";
                }
                App.userService.changePhoneNumber(App.userService.getActiveUser().getUserID(), phoneNumTextField.getText(), userType);
            }
            if(!emailAddressTextField.getText().equals("")){
                String userType = "";
                if (App.userService.getActiveUser().getType() == DOCTOR || App.userService.getActiveUser().getType() == ADMIN ||
                        App.userService.getActiveUser().getType() == MAINTENANCE || App.userService.getActiveUser().getType() == NURSE ||
                        App.userService.getActiveUser().getType() == SECURITY_GUARD || App.userService.getActiveUser().getType() == TECHNICAL_SUPPORT ||
                        App.userService.getActiveUser().getType() == TRANSLATORS) {
                    userType = "Employees";
                } else {
                    userType = "Guests";
                }
                App.userService.changeEmail(App.userService.getActiveUser().getUserID(), emailAddressTextField.getText(), userType);
            }
        }

    }

    public void handleCreateTable() {
        App.mapService.loadStuff();
        App.mapService.loadCSVFile(filePathTextField.getText(), tableNameOptions.getValue());
    }


    public void handleAStar(ActionEvent actionEvent) {
        App.pathfindingAlgorithm = "ASTAR";
    }

    public void handleDepthFirst(ActionEvent actionEvent) {
        App.pathfindingAlgorithm = "DFS";
    }

    public void handleBreadthFirst(ActionEvent actionEvent) { App.pathfindingAlgorithm = "BFS"; }

    public void handleDijkstra(ActionEvent actionEvent) { App.pathfindingAlgorithm = "DIJKSTRA"; }


    public void handleSaveCSV(ActionEvent actionEvent) {
        Database.getDB().saveCSV(filePathTextField.getText(),tableNameOptions.getValue(),"Anything I want");
    }

    public void handleUpdatePassword() {
            if (!App.userService.checkPassword(oldPasswordFeild.getText(),App.userService.getActiveUser().getUserName()).equals("")) {
                if(!newPasswordFeild1.getText().equals(newPasswordFeild2.getText())){
                    passwordsDontMatchLabel.setVisible(true);
                }else {
                    App.userService.changePassword(App.userService.getActiveUser().getUserName(),newPasswordFeild1.getText(),App.userService.checkPassword(oldPasswordFeild.getText(),App.userService.getActiveUser().getUserName()));
                    oldPasswordFeild.setText("");
                    newPasswordFeild1.setText("");
                    newPasswordFeild2.setText("");

                    succsessfulLabel.setVisible(true);
                }
            }else{
                wrongPasswordLable.setVisible(true);
            }
    }

    public void handleApplyNotificationChange(ActionEvent actionEvent) {
    }
}
