package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import edu.wpi.u.requests.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class NewEditRequestController {

    //Generic Request Fields
    @FXML TextField editTitleField;

    @FXML TextArea editDescriptionField;

    @FXML JFXDatePicker makeEditDate2BCompleteDatePicker;

    @FXML JFXChipView makeEditLocationChipView;

    @FXML JFXChipView makeEditStaffChipView;

    //@FXML StackPane laundryStack;

    //12 Specific Request Panes (3 currently)
    @FXML Pane makeEditLaundryPane;

    @FXML Pane makeEditSecurityPane;

    @FXML Pane makeEditMaintenancePane;

        @FXML JFXTextField madeEditMaintenanceField;
        @FXML JFXTextField madeEditMaintenanceField1;


    private Request currRequest;



  /*  @FXML
    JFXDrawer errorDrawer;
    ErrorMessageController controller;

    @FXML
    JFXDrawer errorDrawer2;
    ErrorMessageController controller2;*/

    public void initialize() throws IOException {
        currRequest = App.requestService.getRequests().get(App.lastClickedRequestNumber).getGenericRequest();

        editTitleField.setText(currRequest.getTitle());
        editDescriptionField.setText(currRequest.getDescription());
        //editTypeOfRequestField.setText(currRequest.getType()); //TODO: Type of request never changes, correct?
        //editCompDateField.setText(currRequest.getDateCompleted());
        //editCreatorField.setText("Admin");
        //For when creators switch
        //editCreatorField.setText(currRequest.getCreator());


        for (String l : currRequest.getLocation()) { //Locations
            makeEditLocationChipView.getChips().add(l);
        }

        for (String a : currRequest.getAssignee()) { //Assignees
            makeEditStaffChipView.getChips().add(a);
        }

        //TODO: What does this block do?
//        ArrayList<Node> L = App.mapService.getNodes();//This gets the list of all the nodes
//        ArrayList<String> nodeIDs = new ArrayList<String>(); //Instantiating a new ArrayList for the NodeID's
//        for(Node N: L){//This fills up the new ArrayList<String> with the node ID's so we can display those
//            nodeIDs.add(N.getNodeID());
//        }
//        ObservableList<String> oList = FXCollections.observableList(nodeIDs);
//        editLocField.setItems(oList);

        /*FXMLLoader errorMessageLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/ErrorMessage.fxml"));
        AnchorPane error = errorMessageLoader.load();
        controller = errorMessageLoader.getController();
        controller.errorMessage.setText("Invalid Location");
        errorDrawer.setSidePane(error);

        FXMLLoader errorMessageLoader2 = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/ErrorMessage.fxml"));
        AnchorPane error2 = errorMessageLoader2.load();
        controller2 = errorMessageLoader2.getController();
        controller2.errorMessage.setText("Invalid People");
        errorDrawer2.setSidePane(error2);*/
    }

    private void handleSaveNewEditRequest() {
        
    }

    /*private boolean isChecked() {
        if (isCompleteCheckBox.isSelected()) {
            return true;
        } return false;
    }*/

   /* private boolean doesLocationExist() {
        for(int i = 0; i < currRequest.getLocation().size(); i++) {
            if (editLocField.equals(currRequest.getLocation().get(i))) {
                return true;
            }
        } return false;
    }*/

    /*private boolean doesPersonExist() {
        for(int i = 0; i < currRequest.getAssignee().size(); i++) {
            if (editPeopleField.equals(currRequest.getAssignee().get(i))) {
                return true;
            }
        } return false;
    }*/

   /* public void handleAddLocation() {
        String newLoc = editLocField.getText();
        if(!doesLocationExist()) {
            currRequest.getLocation().add(newLoc);
        } editLocationErrorLabel.setText("Location Already Listed.");
    }*/

/*public void handleAddLocation(){
    if (editLocField.getValue() == null) {
        errorDrawer.open();
        } else {
            currRequest.getLocation().add(editLocField.getValue().toString());
            showCurrentLocListView.getItems().add(editLocField.getValue().toString());
            // clears combobox
            editLocField.setValue(null);
            errorDrawer.close();
        }

    }*/


  /*  public void handleDeleteLocation() {
        for(int i = 0; i < currRequest.getLocation().size(); i++) {
            if (editLocField.equals(currRequest.getLocation().get(i))) {
                currRequest.getLocation().remove(currRequest.getLocation().get(i));
                showCurrentLocListView.getItems().remove(currRequest.getLocation().get(i));
                return;
            }
        } errorDrawer.open();
    }

    public void handleAddPeople() {
        String newPer = editPeopleField.getText();
        if(!doesPersonExist() && editPeopleField.getText() != null && !newPer.equals("") && !newPer.equals("")) {
            currRequest.getAssignee().add(newPer);
            showCurrentPeopleListView.getItems().add(newPer);
            errorDrawer2.close();
        }
        else{

            errorDrawer2.open();
        }
    }

    public void handleDeletePeople() {
        for(String s : currRequest.getAssignee()){
            if (editPeopleField.getText().equals(s)) {
                currRequest.getAssignee().remove(s);
                showCurrentPeopleListView.getItems().remove(s);
                errorDrawer2.close();
                return;
            }
        } errorDrawer2.open();

*/

   // }

    public void handleCancel() { App.rightDrawerRoot.set("/edu/wpi/u/views/Oldfxml/ViewRequest.fxml"); }


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

       /* Date dateCompleted;
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
        App.rightDrawerRoot.set("/edu/wpi/u/views/Oldfxml/ViewRequest.fxml");
*/

        //needs node ID
        //assignments, give list name


    }
   // public void handleErrorMessageClear(){
       // errorDrawer.close();
    //}

   /* public void handleErrorMessageClear2(){
        errorDrawer2.close();
    }*/
}
