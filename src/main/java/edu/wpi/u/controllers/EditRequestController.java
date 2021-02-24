package edu.wpi.u.controllers;

import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import edu.wpi.u.requests.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class EditRequestController {

    @FXML TextField editTitleField;

    @FXML TextArea editDescripArea;

    @FXML ListView showCurrentLocListView;

    @FXML ChoiceBox editLocField;

    @FXML Label editLocationErrorLabel;

    @FXML TextField editTypeOfRequestField;

    @FXML TextField editCompDateField;

    @FXML CheckBox isCompleteCheckBox;

    @FXML TextField editCreatorField;

    @FXML ListView showCurrentPeopleListView;

    @FXML TextField editPeopleField;

    @FXML Label editPeopleErrorLabel;

    private Request currRequest;

    public void initialize(){
        currRequest = App.requestService.getRequests().get(App.getInstance().requestClicked);

        editTitleField.setText(currRequest.getTitle());
        editDescripArea.setText(currRequest.getDescription());
        editTypeOfRequestField.setText(currRequest.getType());
        //editCompDateField.setText(currRequest.getDateCompleted());
        editCreatorField.setText("Admin");
        //For when creators switch
        //editCreatorField.setText(currRequest.getCreator());

        for (int i = 0; i < currRequest.getLocation().size(); i++) {
            showCurrentLocListView.getItems().add(currRequest.getLocation().get(i));
        }

        for (int i = 0; i < currRequest.getAssignee().size(); i++) {
            showCurrentPeopleListView.getItems().add(currRequest.getAssignee().get(i));
        }

        ArrayList<Node> L = App.graphService.getNodes();//This gets the list of all the nodes
        ArrayList<String> nodeIDs = new ArrayList<String>(); //Instantiating a new ArrayList for the NodeID's
        for(Node N: L){//This fills up the new ArrayList<String> with the node ID's so we can display those
            nodeIDs.add(N.getNodeID());
        }
        ObservableList<String> oList = FXCollections.observableList(nodeIDs);
        editLocField.setItems(oList);
    }

    private boolean isChecked() {
        if (isCompleteCheckBox.isSelected()) {
            return true;
        } return false;
    }

    private boolean doesLocationExist() {
        for(int i = 0; i < currRequest.getLocation().size(); i++) {
            if (editLocField.equals(currRequest.getLocation().get(i))) {
                return true;
            }
        } return false;
    }

    private boolean doesPersonExist() {
        for(int i = 0; i < currRequest.getAssignee().size(); i++) {
            if (editPeopleField.equals(currRequest.getAssignee().get(i))) {
                return true;
            }
        } return false;
    }

   /* public void handleAddLocation() {
        String newLoc = editLocField.getText();
        if(!doesLocationExist()) {
            currRequest.getLocation().add(newLoc);
        } editLocationErrorLabel.setText("Location Already Listed.");
    }*/

    public void handleAddLocation(){
        if (editLocField.getValue() == null) {
            editLocationErrorLabel.setText("Please enter a node!");
        } else {
            currRequest.getLocation().add(editLocField.getValue().toString());
            showCurrentLocListView.getItems().add(editLocField.getValue().toString());
            // clears combobox
            editLocField.setValue(null);
            editLocationErrorLabel.setText("");
        }

    }


    public void handleDeleteLocation() {
        for(int i = 0; i < currRequest.getLocation().size(); i++) {
            if (editLocField.equals(currRequest.getLocation().get(i))) {
                currRequest.getLocation().remove(currRequest.getLocation().get(i));
                showCurrentLocListView.getItems().remove(currRequest.getLocation().get(i));
                return;
            }
        } editLocationErrorLabel.setText("Location Does Not Exist");
    }

    public void handleAddPeople() {
        String newPer = editPeopleField.getText();
        if(!doesPersonExist()) {
            currRequest.getAssignee().add(newPer);
            showCurrentPeopleListView.getItems().add(newPer);
            editPeopleErrorLabel.setText("");
        }
        else{
            editPeopleErrorLabel.setText("Person Already Listed.");
        }
    }

    public void handleDeletePeople() {
        for(String s : currRequest.getAssignee()){
            if (editPeopleField.getText().equals(s)) {
                currRequest.getAssignee().remove(s);
                showCurrentPeopleListView.getItems().remove(s);
                return;
            }
        } //editPeopleErrorLabel.setText("Person Does Not Exist");



    }

    public void handleCancel() { App.rightDrawerRoot.set("/edu/wpi/u/views/ViewRequest.fxml"); }


    // Array list to linkedlist converter
    public static LinkedList<String> OConverter(ObservableList<String> oList)
    {
        LinkedList<String> newLL = new LinkedList<String>();
        for(String s : oList){
            newLL.add(s);
        }


        return newLL;
    }


    public void handleSaveRequest() {

        Date dateCompleted;
        if (isChecked()) {
            dateCompleted = new Date();
        } else {
            dateCompleted = null;
        }

        System.out.println(currRequest.getRequestID());


        App.requestService.updateRequest(
                currRequest.getRequestID(),
                editTitleField.getText(),
                editDescripArea.getText(),
                dateCompleted,
                OConverter(showCurrentLocListView.getItems()),
                editTypeOfRequestField.getText(),
                OConverter(showCurrentPeopleListView.getItems()),
                editCreatorField.getText());
        App.rightDrawerRoot.set("/edu/wpi/u/views/ViewRequest.fxml");


        //needs node ID
        //assignments, give list name


    }
    public void handleErrorMessageClear(){

    }
}
