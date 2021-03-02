package edu.wpi.u.controllers.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.exceptions.FilePathNotFoundException;
import javafx.fxml.FXML;

import edu.wpi.u.App;

import java.io.File;
import java.io.IOException;

import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import static edu.wpi.u.users.StaffType.*;

public class SettingsPageController {


    @FXML
    public JFXButton pathMode1Button;
    @FXML
    public JFXButton pathMode2Button;
    @FXML
    public JFXButton loadCSVButton;
    @FXML
    public JFXRadioButton setTheme;
    @FXML
    public JFXTextField phoneNumTextField;
    @FXML
    public JFXTextField emailAddressTextField;
    @FXML
    public JFXButton contactChangeButton;
    @FXML
    public JFXTextField nodeLocation;
    @FXML
    public FileChooser fileDirect = new FileChooser();
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
    //public JFXButton  createTableButton;

    public void initialize() throws IOException, FilePathNotFoundException {

        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("File Required");
        filePathTextField.getValidators().add(validator);
        filePathTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                filePathTextField.validate();
            }
        });

        RequiredFieldValidator validator2 = new RequiredFieldValidator();
        validator.setMessage("Email Required");
        emailAddressTextField.getValidators().add(validator2);
        emailAddressTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
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
            pathMode1Button.setStyle("-fx-opacity: 0");
            pathMode2Button.setStyle("-fx-opacity: 0");

            pathfindingText.setDisable(true);
            filePathTextField.setDisable(true);
            createTableButton.setDisable(true);
            loadCSVButton.setDisable(true);
            pathMode1Button.setDisable(true);
            pathMode2Button.setDisable(true);

        }
    }

        //most of these functions are just place holders, so I dont
        //have anything to add for stubs / javadocs
        //will need help writing these.
        public void handlePathMode1 () {

        }

        public void handlePathMode2 () {

        }

        /**
         * This function will open the file explorer and update the textfield with the file name
         * selected in the explorer when the button is pressed
         *
         */
        public String handleLoadCSV () throws FilePathNotFoundException {
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
            return csv.getPath();

            // App.db.readCSV(csv.getPath(), tableNameTextFIeld.getText() );

        }

        /** This function intakes two text fields and makes a change to the user in the database one the button is handled.
         */
        public void handleContactChange () {
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

        public void handleCreateTable () {
            App.mapService.loadCSVFile(filePathTextField.getText(), tableNameTextFIeld.getText());
        }

    /*public void handleSetTheme1(ActionEvent actionEvent) {
        //Light Theme
    }*/

    /*public void handleSetTheme2(ActionEvent actionEvent) {
        //Dark Theme
    }*/
    }
