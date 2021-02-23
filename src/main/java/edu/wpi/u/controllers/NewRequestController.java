package edu.wpi.u.controllers;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.models.GraphService;

import java.io.IOException;
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

    @FXML
    public TextField titleTextField;
    @FXML
    public TextField descriptionTextField;
    @FXML
    public TextField nodeTextField;
    @FXML
    public TextField assigneeTextField;
    @FXML
    public TextField serviceTypeTextField;
    @FXML
    public Button submitRequestButton;
    @FXML
    public Button assigneeButton;
    @FXML
    public Button locationButton;
    @FXML
    public Button cancelButton;
    @FXML
    public Label errorMessage;
    @FXML
    public Label errorMessage2;
    @FXML
    public Label errorMessage3;
    @FXML
    public ListView locationList;
    @FXML
    public ListView assigneeList;
    @FXML
    ChoiceBox locationDropField;


    GraphService gs = new GraphService();

    ObservableList<Node> oList;

    //string placeholder for USDERID
    public String userID = "ADMIN";

    public ArrayList<String> assignee = new ArrayList<String>();

    public ArrayList<String> location = new ArrayList<String>();

    public void handleAssigneeList() {
        if (titleTextField.getText().equals("")) {
            errorMessage2.setText("Please enter an assignee!");
        } else {
            assignee.add(assigneeTextField.getText());
            assigneeList.getItems().add(assigneeTextField.getText());
            assigneeTextField.setText("");
            //System.out.println("call");}
        }
    }

    //This initialize function mostly fills in the correct nodes to the drop-down menu
    public void initialize() throws IOException {
        ArrayList<Node> L = gs.getNodes();//This gets the list of all the nodes
        ArrayList<String> nodeIDs = new ArrayList<String>(); //Instantiating a new ArrayList for the NodeID's
        for(Node N: L){//This fills up the new ArrayList<String> with the node ID's so we can display those
            nodeIDs.add(N.getNodeID());
        }
        ObservableList<String> oList = FXCollections.observableList(nodeIDs);
        locationDropField.setItems(oList); //This sets the observablelist that just got created to the stuff thats in the dropdown
    }

    public void handleAddLocation(){
        if (locationDropField.getValue().toString().equals("")) {
            errorMessage3.setText("Please enter a node!");
        } else {
            location.add(locationDropField.getValue().toString());
            locationList.getItems().add(locationDropField.getValue().toString());
            locationDropField.setItems(null);
            //System.out.println("call");}
        }

    }

    // Array list to linkedlist converter
    public static LinkedList<String> lLConverter(ArrayList<String> arrayList)
    {
        LinkedList<String> newLL = new LinkedList<>(arrayList);

        return newLL;
    }

    public void handleSubmitRequestButton() {

        if (titleTextField.getText().equals("")) {
            errorMessage.setText("Please enter a title!");}
            else{
                App.requestService.addRequest(descriptionTextField.getText(), lLConverter(assignee),  titleTextField.getText(), lLConverter(location), serviceTypeTextField.getText(), userID );
                App.rightDrawerRoot.set("../views/ViewRequest.fxml");

            }

    }

    public void handleLeaveAdd(){
        App.rightDrawerRoot.set( "../views/ViewRequest.fxml");
    }

}
