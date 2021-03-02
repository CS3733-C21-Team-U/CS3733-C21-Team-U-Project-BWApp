package edu.wpi.u.controllers;

import com.jfoenix.controls.*;
import edu.wpi.u.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class NewRequestController {
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
    @FXML public JFXButton cancelButton;
    @FXML public JFXButton saveButton;

    @FXML
    public void initialize(ActionEvent event) throws IOException{
        // receive data: https://dev.to/devtony101/javafx-3-ways-of-passing-information-between-scenes-1bm8
        // receiveData Step 1
        javafx.scene.Node node = (javafx.scene.Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        // receiveData Step 2
        String type = (String) stage.getUserData();
        switch (type){
            case "Laundry":
                makeEditLaundryPane.setVisible(true);
                break;
            case "Security":
                makeEditSecurityPane.setVisible(true);
                break;
            case "Maintenance":
                makeEditMaintenancePane.setVisible(true);
                break;
        }

    }


    @FXML
    public void handleSubmitRequestButton(ActionEvent actionEvent) {
    }

    @FXML
    public void HandleMakeEditCancelButton(ActionEvent actionEvent)throws IOException {
        FXMLLoader requestLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/ButtonPageForNewRequestController.fxml"));
        requestLoader.load();

    }
//String description, LinkedList<String> assignee, String title, LinkedList<String> location, String type, String creator, LinkedList<Serializable> specifics
    @FXML
    public void handleSaveNewEditRequest(ActionEvent actionEvent) {
        App.requestService.addRequest(makeEditDescriptionField.getText(),makeEditTitleField.getText());
    }


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


}
