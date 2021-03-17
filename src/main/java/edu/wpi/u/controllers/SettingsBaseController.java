package edu.wpi.u.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.database.Database;
import edu.wpi.u.exceptions.FilePathNotFoundException;
import edu.wpi.u.users.Role;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import edu.wpi.u.App;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import lombok.SneakyThrows;

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
    @FXML public ToggleGroup themeGroup;
    public JFXRadioButton purrpleRadio;
    public JFXRadioButton darkRadio;
    public JFXRadioButton yellowRadio;
    public JFXRadioButton blueRadio;
    public JFXToggleButton emailNotifications;
    public JFXToggleButton textNotifications;
    public HBox settingsPageHbox;
    public VBox basicAccountSettings;
    public VBox adminSettings;
    public Group notificationChangeGroup;
    public VBox otherSettings;
    private boolean enableChangeThemePopup = false;

    public void initialize() throws IOException, FilePathNotFoundException {
        passwordsDontMatchLabel.setVisible(false);
        wrongPasswordLable.setVisible(false);
        succsessfulLabel.setVisible(false);
        contactInfoLabel.setVisible(false);
        errorUpdateContactLabel.setVisible(false);

        App.isLoggedIn.addListener((observable, oldValue, newValue) -> {
            if (App.userService.getActiveUser().getType() == PATIENT){
                otherSettings.getChildren().remove(notificationChangeGroup);
            }
            else if (!otherSettings.getChildren().contains(notificationChangeGroup)){
                otherSettings.getChildren().add(0,notificationChangeGroup);
            }
            if (!settingsPageHbox.getChildren().contains(basicAccountSettings)){
                settingsPageHbox.getChildren().add(basicAccountSettings);
            }
            if (!settingsPageHbox.getChildren().contains(adminSettings)){
                settingsPageHbox.getChildren().add(adminSettings);
            }
            if (App.userService.getActiveUser().getType() == ADMIN){
                settingsPageHbox.getChildren().remove(basicAccountSettings);
            }
            if(!(App.userService.getActiveUser().getType() ==  Role.ADMIN)){
                settingsPageHbox.getChildren().remove(adminSettings);
//                onlyAdmin.setStyle("-fx-opacity: 0");
//                onlyAdmin.setDisable(true);
            }
            switch (App.userService.getPreferredContactMethod(App.userService.getActiveUser().getUserName())) {
                case "Both":
                    emailNotifications.setSelected(true);
                    textNotifications.setSelected(true);
                    break;
                case "Email":
                    emailNotifications.setSelected(true);
                    break;
                case "SMS":
                    textNotifications.setSelected(true);
                    break;
                default:
                    emailNotifications.setSelected(false);
                    textNotifications.setSelected(false);
                    break;
            }
            phoneNumTextField.setText(App.userService.getActiveUser().getPhoneNumber());
            emailAddressTextField.setText(App.userService.getActiveUser().getEmail());
        });

        Platform.runLater(()->{
            switch (App.themeString){
                case "PURPLE":
                    purrpleRadio.setSelected(true);
                    break;
                case "DARK":
                    darkRadio.setSelected(true);
                    break;
                case "YELLOW":
                    yellowRadio.setSelected(true);
                    break;
                case "BLUE":
                    blueRadio.setSelected(true);
                    break;
            }
            enableChangeThemePopup = true;
        });

        themeGroup.selectedToggleProperty().addListener((o, oldVal, newVal) -> {
            JFXRadioButton target = ((JFXRadioButton)themeGroup.getSelectedToggle());

            if(target != null && enableChangeThemePopup){
                JFXDialogLayout content = new JFXDialogLayout();
                Label header = new Label("Restart Now?");
                header.getStyleClass().add("headline-2");
                Label body = new Label("Applying the new theme requires a restart. If you decide to restart later, the selected theme will be applied when you launch the app next.");
                content.setHeading(header);
                content.setBody(body);
                content.getStyleClass().add("dialogue");
                JFXDialog dialog = new JFXDialog(App.throwDialogHerePane, content, JFXDialog.DialogTransition.CENTER);
                JFXButton button1 = new JFXButton("RESTART LATER");
                JFXButton button2 = new JFXButton("CLOSE APP");
                button1.setOnAction(event -> dialog.close());
                button2.setOnAction(new EventHandler<ActionEvent>() {
                    @SneakyThrows
                    @Override
                    public void handle(ActionEvent event) {
                        dialog.close();
                        App.getInstance().exitApp();
                    }
                });
                button1.getStyleClass().add("button-text");
                button2.getStyleClass().add("button-contained");
                ArrayList<Node> actions = new ArrayList<>();
                actions.add(button1);
                actions.add(button2);
                content.setActions(actions);
                dialog.show();
                System.out.println(((JFXRadioButton)(themeGroup.getSelectedToggle())).getText());
                App.userService.changeTheme(((JFXRadioButton)(themeGroup.getSelectedToggle())).getText());

            }
        });

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
                } else if (App.userService.getActiveUser().getType() == PATIENT){
                    userType = "Patients";
                }
                else {
                    userType = "Guests";
                }
                App.userService.changePhoneNumber(App.userService.getActiveUser().getUserID(), phoneNumTextField.getText(), userType);
                App.updatePhoneNumber.set(!App.updatePhoneNumber.get());
            }
            if(!emailAddressTextField.getText().equals("")){
                String userType = "";
                if (App.userService.getActiveUser().getType() == DOCTOR || App.userService.getActiveUser().getType() == ADMIN ||
                        App.userService.getActiveUser().getType() == MAINTENANCE || App.userService.getActiveUser().getType() == NURSE ||
                        App.userService.getActiveUser().getType() == SECURITY_GUARD || App.userService.getActiveUser().getType() == TECHNICAL_SUPPORT ||
                        App.userService.getActiveUser().getType() == TRANSLATORS) {
                    userType = "Employees";
                } else if (App.userService.getActiveUser().getType() == PATIENT){
                    userType = "Patients";
                }
                else {
                    userType = "Guests";
                }
                App.userService.changeEmail(App.userService.getActiveUser().getUserID(), emailAddressTextField.getText(), userType);
                App.updateEmail.set(!App.updateEmail.get());
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
        if (emailNotifications.isSelected() && textNotifications.isSelected()){
            App.userService.setPreferredContactMethod(App.userService.getActiveUser().getUserName(), "Both");
        }
        else if (emailNotifications.isSelected()) {
            App.userService.setPreferredContactMethod(App.userService.getActiveUser().getUserName(), "Email");
        }
        else if (textNotifications.isSelected()){
            App.userService.setPreferredContactMethod(App.userService.getActiveUser().getUserName(), "SMS");
        }
        else {
            App.userService.setPreferredContactMethod(App.userService.getActiveUser().getUserName(), "Nothing");
        }
    }
}
