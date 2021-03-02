package edu.wpi.u.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import java.io.IOException;
import java.util.LinkedList;
import javafx.scene.control.*;
import java.util.ArrayList;
public class NewRequestController {


    @FXML public Button cancelButton;
    @FXML public TextField titleTextField;
    @FXML public TextArea descriptionTextField;
    @FXML public Label errorMessage2;
    @FXML public TextField assigneeTextField;
    @FXML public Button assigneeButton;
    @FXML public ListView assigneeList;
    @FXML public ChoiceBox locationDropField;
    @FXML public Button locationButton;
    @FXML public ListView locationList;
    @FXML public Label errorMessage3;
    @FXML public TextField serviceTypeTextField;
    @FXML public TextField nodeTextField;
    @FXML public Button submitRequestButton;
    @FXML public Label errorMessage;


    ObservableList<Node> oList;

    //string placeholder for USDERID
    public String userID = "ADMIN";

    public ArrayList<String> assigneeArrayList = new ArrayList<String>();

    public ArrayList<String> locationArrayList = new ArrayList<String>();
//
    public void handleAssigneeList() {
        if (assigneeTextField.getText().equals("")) {
            errorMessage2.setText("Please enter an assignee!");
        } else {
            assigneeArrayList.add(assigneeTextField.getText());
            assigneeList.getItems().add(assigneeTextField.getText());
            assigneeTextField.setText("");
            errorMessage2.setText("");
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
        locationDropField.setItems(oList); //This sets the observablelist that just got created to the stuff thats in the dropdown
    }

    public void handleAddLocation(){
        if (locationDropField.getValue() == null) {
            errorMessage3.setText("Please enter a node!");
        } else {
            locationArrayList.add(locationDropField.getValue().toString());
            locationList.getItems().add(locationDropField.getValue().toString());
            // clears combobox
            locationDropField.setValue(null);
            errorMessage3.setText("");
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
                App.requestService.addRequest(
                        descriptionTextField.getText(),
                        lLConverter(assigneeArrayList),
                        titleTextField.getText(),
                        lLConverter(locationArrayList),
                        serviceTypeTextField.getText(),
                        userID );
                App.rightDrawerRoot.set("/edu/wpi/u/views/Oldfxml/ViewRequest.fxml");

            }

    }

    public void handleLeaveAdd(){
        App.rightDrawerRoot.set("/edu/wpi/u/views/Oldfxml/ViewRequest.fxml");
    }



}
