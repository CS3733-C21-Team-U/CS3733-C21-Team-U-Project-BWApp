package edu.wpi.u.controllers;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import java.util.*;
import java.util.stream.*;

import java.util.Date;

import java.util.ArrayList;

public class NewRequestController {


    public Button newRequestCancelButton;
    public TextField newRequestTitleTextField;
    public TextArea newRequestDescriptionTextField;
    public Label newRequestErrorMessage2;
    public TextField newRequestAssigneeTextField;
    public Button assigneeButton;
    public ListView newRequestAssigneeList;
    public Label newRequestErrorMessage3;
    public ChoiceBox newRequestLocationDropField;
    public Button newRequestLocationButton;
    public ListView newRequestLocationList;
    public TextField newRequestServiceTypeTextField;
    public Label newRequestErrorMessage;
    public Button newRequestSubmitRequestButton;
    //    @FXML public Button cancelButton;
//    @FXML public TextField titleTextField;
//    @FXML public TextArea descriptionTextField;
//    @FXML public Label errorMessage2;
//    @FXML public TextField assigneeTextField;
//    @FXML public Button assigneeButton;
//    @FXML public ListView assigneeList;
//    @FXML public Label errorMessage3;
//    @FXML public ChoiceBox locationDropField;
//    @FXML public Button locationButton;
//    @FXML public ListView locationList;
//    @FXML public TextField serviceTypeTextField;
//    @FXML public Label errorMessage;
//    @FXML public Button submitRequestButton;

    //newRequestTitleTextField.setText(request.getTitle());
    //newRequestDescriptionTextField.setText(request.getDescription());



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

    public void handleSubmitRequestButton() {

//TESTING
        //TODO: get rid of this and take in from UI
        LinkedList<Serializable> l = new LinkedList<Serializable>();
        l.addLast("Please");
        l.addLast(3);
        //END TESTING
        if (newRequestTitleTextField.getText().equals("")) {
            newRequestErrorMessage.setText("Please enter a title!");}
        else{
            App.requestService.addRequest(
                    newRequestDescriptionTextField.getText(),
                    lLConverter(assigneeArrayList),
                    newRequestTitleTextField.getText(),
                    lLConverter(locationArrayList),
                    newRequestServiceTypeTextField.getText(),
                    userID, l);
            App.rightDrawerRoot.set("/edu/wpi/u/views/ViewRequest.fxml");

        }
    }

    public void handleLeaveAdd(){
        App.rightDrawerRoot.set( "/edu/wpi/u/views/ViewRequest.fxml");
    }



}
