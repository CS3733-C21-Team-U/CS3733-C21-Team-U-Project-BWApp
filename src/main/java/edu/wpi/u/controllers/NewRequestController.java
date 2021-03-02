package edu.wpi.u.controllers;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.*;
import java.util.stream.*;

import java.util.Date;

import java.util.ArrayList;

public class NewRequestController {


//    public Button newRequestCancelButton;
//    public TextField newRequestTitleTextField;
//    public TextArea newRequestDescriptionTextField;
//    public Label newRequestErrorMessage2;
//    public TextField newRequestAssigneeTextField;
//    public Button assigneeButton;
//    public ListView newRequestAssigneeList;
//    public Label newRequestErrorMessage3;
//    public ChoiceBox newRequestLocationDropField;
//    public Button newRequestLocationButton;
//    public ListView newRequestLocationList;
//    public TextField newRequestServiceTypeTextField;
//    public Label newRequestErrorMessage;
//    public Button newRequestSubmitRequestButton;

    @FXML public StackPane laundryStack;
    @FXML public Pane makeEditLaundryPane;
    @FXML public JFXTextField madeEditLaundryField;
    @FXML public Pane makeEditSecurityPane;
    @FXML public JFXTextField madeEditSecurityField;
    @FXML public Pane makeEditMaintenancePane;
    @FXML public JFXTextField madeEditMaintenanceField;
    @FXML public JFXTextArea makeEditDescriptionField;
    @FXML public JFXChipView makeEditLocationChipView;
    @FXML public JFXChipView makeEditStaffChipView;
    @FXML public JFXDatePicker makeEditDate2BCompleteDatePicker;
    @FXML public JFXTextField makeEditTitleField;


    //newRequestTitleTextField.setText(request.getTitle());
    //newRequestDescriptionTextField.setText(request.getDescription());


 /*
    ObservableList<Node> oList;
    //string placeholder for USDERID
    public String userID = "ADMIN";

    public ArrayList<String> assigneeArrayList = new ArrayList<String>();

    public ArrayList<String> locationArrayList = new ArrayList<String>();
//
    public void handleAssigneeList() {
        if (newRequestAssigneeTextField.getText().equals("")) {
            newRequestErrorMessage2.setText("Please enter an assignee!");
        } else {
            assigneeArrayList.add(newRequestAssigneeTextField.getText());
            newRequestAssigneeList.getItems().add(newRequestAssigneeTextField.getText());
            newRequestAssigneeTextField.setText("");
            newRequestErrorMessage2.setText("");
            //System.out.println("call");}
        }
    }

    //This initialize function mostly fills in the correct nodes to the drop-down menu
    public void initialize() throws IOException {

        ArrayList<Node> L = App.mapService.getNodes();//This gets the list of all the nodes
        ArrayList<String> nodeIDs = new ArrayList<String>(); //Instantiating a new ArrayList for the NodeID's
        for(Node N: L){//This fills up the new ArrayList<String> with the node ID's so we can display those
            nodeIDs.add(N.getNodeID());
        }
        ObservableList<String> oList = FXCollections.observableList(nodeIDs);
        newRequestLocationDropField.setItems(oList); //This sets the observablelist that just got created to the stuff thats in the dropdown
    }

    public void handleAddLocation(){
        if (newRequestLocationDropField.getValue() == null) {
            newRequestErrorMessage3.setText("Please enter a node!");
        } else {
            locationArrayList.add(newRequestLocationDropField.getValue().toString());
            newRequestLocationList.getItems().add(newRequestLocationDropField.getValue().toString());
            // clears combobox
            newRequestLocationDropField.setValue(null);
            newRequestErrorMessage3.setText("");
        }

    }

    // Array list to linkedlist converter
    public static LinkedList<String> lLConverter(ArrayList<String> arrayList)
    {
        LinkedList<String> newLL = new LinkedList<>(arrayList);

        return newLL;
    }

    /*
    public void handleSubmitRequestButton() {

        if (newRequestTitleTextField.getText().equals("")) {
            newRequestErrorMessage.setText("Please enter a title!");}
            else{
                App.requestService.addRequest(
                        newRequestDescriptionTextField.getText(),
                        lLConverter(assigneeArrayList),
                        newRequestTitleTextField.getText(),
                        lLConverter(locationArrayList),
                        newRequestServiceTypeTextField.getText(),
                        userID );
                App.rightDrawerRoot.set("/edu/wpi/u/views/ViewRequest.fxml");

            }

    }
     */

    public void handleLeaveAdd(){
        App.rightDrawerRoot.set( "/edu/wpi/u/views/ViewRequest.fxml");
    }


    public void HandleMakeEditCancelButton(ActionEvent actionEvent) {
    }

    public void handleSaveNewEditRequest(ActionEvent actionEvent) {
    }
    */

}
