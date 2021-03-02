package edu.wpi.u.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.requests.IRequest;
import edu.wpi.u.requests.Request;
import edu.wpi.u.requests.RequestFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

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

    private IRequest currIRequest;

    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @FXML
    public void initialize(ActionEvent event) throws IOException {
        javafx.scene.Node node = (javafx.scene.Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        // receiveData Step 2
        String type = App.newNodeType;
        currIRequest = new RequestFactory().makeRequest(type);

        //TODO: redo so it does not use a switch statement
        generateSpecificFields(type);

        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Integer Required");
        madeMaintenanceFieldMachineUsed.getValidators().add(validator);
        madeMaintenanceFieldPriority.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!(isInteger(newVal.toString()))) {
                madeMaintenanceFieldPriority.validate();
            }
        });

    }

    //TODO : Replace with function written in NER Controller, based on current IREQUEST
    public void generateSpecificFields(String type){
        switch (type) {
            case "Laundry":
                makeLaundryPane.setVisible(true);
                break;
            case "Security":
                makeSecurityPane.setVisible(true);
                break;
            case "Maintenance":
                makeMaintenancePane.setVisible(true);
                break;
        }
    }

    //TODO : Replace with function written in NER Controller
    public LinkedList<Serializable> requestSpecificItems(String type) {
        LinkedList<Serializable> specifics = new LinkedList<>();
        switch (type) {
            case ("Maintenance"):
                specifics.add(madeMaintenanceFieldMachineUsed.getText());
                specifics.add(Integer.parseInt(madeMaintenanceFieldPriority.getText()));
                break;
            case ("Laundry"):
                //add stuff
                break;
            case ("Security"):
                //add stuff
                break;
            default:
                System.out.println("lmao you screwed up");
        }
        return specifics;
    }


    @FXML
    public void handleSaveNewRequest() throws IOException {
        LinkedList<String> staff = new LinkedList<String>(makeStaffChipView.getChips());
        LinkedList<String> locations = new LinkedList<String>(makeLocationChipView.getChips());
        LinkedList<Serializable> specifics = requestSpecificItems(App.newNodeType);

        IRequest result = new RequestFactory().makeRequest(App.newNodeType);
        Random rand = new Random();
        int requestID = rand.nextInt();
        String ID = Integer.toString(requestID);//make a random id
        // String requestID,LinkedList<String> assignee, Date dateCreated, Date dateCompleted, String description, String title, LinkedList<String> location, String type, String creator) {
        Request newRequest = new Request(ID, staff, new Date(), null, makeDescriptionField.getText() ,makeTitleField.getText(),locations, App.newNodeType, "Creator_here");

        result.setRequest(newRequest);
        result.setSpecificData(specifics);
        App.requestService.addRequest(result);

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewViewRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    @FXML
    public void HandleMakeCancelButton() throws IOException {
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewViewRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);

    }
}
