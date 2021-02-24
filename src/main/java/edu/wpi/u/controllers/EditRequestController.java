package edu.wpi.u.controllers;

import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import edu.wpi.u.requests.*;

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
                return;
            }
        } editLocationErrorLabel.setText("Location Does Not Exist");
    }

    public void handleAddPeople() {
        String newPer = editPeopleField.getText();
        if(!doesPersonExist()) {
            currRequest.getAssignee().add(newPer);
        } editPeopleErrorLabel.setText("Person Already Listed.");
    }

    public void handleDeletePeople() {
        for(int i = 0; i < currRequest.getAssignee().size(); i++) {
            if (editPeopleField.equals(currRequest.getAssignee().get(i))) {
                currRequest.getAssignee().remove(currRequest.getAssignee().get(i));
                return;
            }
        } editPeopleErrorLabel.setText("Person Does Not Exist");
    }

    public void handleCancel() { App.rightDrawerRoot.set("/edu/wpi/u/views/ViewRequest.fxml"); }

    public void handleSaveRequest() {

        Date dateCompleted;
        if (isChecked()) {
            dateCompleted = new Date();
        } else {
            dateCompleted = null;
        }

        App.requestService.updateRequest(
                currRequest.getRequestID(),
                editTitleField.getText(),
                editDescripArea.getText(),
                dateCompleted,
                (LinkedList<String>) showCurrentLocListView.getItems(),
                editTypeOfRequestField.getText(),
                (LinkedList<String>) showCurrentPeopleListView.getItems(),
                editCreatorField.getText());
    }
}
