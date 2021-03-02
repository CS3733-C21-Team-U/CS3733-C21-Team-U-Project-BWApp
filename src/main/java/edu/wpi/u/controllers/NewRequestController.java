package edu.wpi.u.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.requests.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

public class NewRequestController {


    public JFXTextField makeTitleField;
    public JFXTextArea makeDescriptionField;
    public JFXDatePicker makeDate2BCompleteDatePicker;
    public JFXChipView makeLocationChipView;
    public JFXChipView makeStaffChipView;
    public StackPane laundryStack;
    public Pane makeLaundryPane;
    public JFXTextField madeLaundryField;
    public Pane makeSecurityPane;
    public JFXTextField madeSecurityField;
    public Pane makeMaintenancePane;
    public JFXTextField madeMaintenanceFieldMachineUsed;
    public JFXTextField madeMaintenanceFieldPriority;

    private Request currRequest;

    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @FXML
    public void initialize(ActionEvent event) throws IOException{
        //TODO: Implement commented code below in order to send request type info to show appropriate pane
//        // receive data: https://dev.to/devtony101/javafx-3-ways-of-passing-information-between-scenes-1bm8
//        // receiveData Step 1
//        javafx.scene.Node node = (javafx.scene.Node) event.getSource();
//        Stage stage = (Stage) node.getScene().getWindow();
//        // receiveData Step 2
//        String type = (String) stage.getUserData();
//        switch (type){
//            case "Laundry":
//                makeLaundryPane.setVisible(true);
//                break;
//            case "Security":
//                makeSecurityPane.setVisible(true);
//                break;
//            case "Maintenance":
//                makeMaintenancePane.setVisible(true);
//                break;
//        }

        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Integer Required");
        madeMaintenanceFieldMachineUsed.getValidators().add(validator);
        madeMaintenanceFieldPriority.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!(isInteger(newVal.toString()))) {
                madeMaintenanceFieldPriority.validate();
            }
        });

    }

    public LinkedList<Serializable> requestSpecificItems(String type) {
        LinkedList<Serializable> specifics = new LinkedList<>();
        switch(type) {
            case("Maintenance") :
                specifics.add(madeMaintenanceFieldMachineUsed.getText());
                specifics.add(madeMaintenanceFieldPriority.getText());
                break;
            case("Laundry") :
                //add stuff
                break;
            case("Security"):
                //add stuff
                break;
            default:
                System.out.println("lmao you screwed up");
        }
        return specifics;
    }


    @FXML
    public void handleSubmitRequestButton() throws IOException {
        FXMLLoader requestLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/ButtonPageForNewRequestController.fxml"));
        requestLoader.load();
    }

    @FXML
    public void HandleMakeEditCancelButton()throws IOException {
        FXMLLoader requestLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/ButtonPageForNewRequestController.fxml"));
        requestLoader.load();

    }

    public void handleSaveNewRequest() {
    }

    public void HandleMakeCancelButton() {
    }
}
