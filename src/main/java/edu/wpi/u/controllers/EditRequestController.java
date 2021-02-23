package edu.wpi.u.controllers;

import edu.wpi.u.App;
import edu.wpi.u.models.Request;
import edu.wpi.u.models.RequestService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class EditRequestController {

    @FXML TextField editTitleField;

    @FXML TextArea editDescripArea;

    @FXML ListView showCurrentLocListView;

    @FXML TextField editLocField;

    @FXML Label editLocationErrorLabel;

    @FXML TextField editTypeOfRequestField;

    @FXML TextField editCompDateField;

    @FXML CheckBox isCompleteCheckBox;

    @FXML TextField editCreatorField;

    @FXML ListView showCurrentPeopleListView;

    @FXML TextField editPeopleField;

    @FXML Label editPeopleErrorLabel;

    public void initialize(){
        Request request = makeDummyRequest();

        editTitleField.setText(request.getTitle());
        editDescripArea.setText(request.getDescription());
        editTypeOfRequestField.setText(request.getType());
        editCompDateField.setText(request.getDateCompleted().toString());
        editCreatorField.setText("Admin");
        //For when creators switch
        //editCreatorField.setText(request.getCreator());

        for (int i = 0; i < request.getLocation().size(); i++) {
            showCurrentLocListView.getItems().add(request.getLocation().get(i));
        }

        for (int i = 0; i < request.getStaff().size(); i++) {
            showCurrentPeopleListView.getItems().add(request.getStaff().get(i));
        }
    }

    private boolean isChecked() {
        if (isCompleteCheckBox.isSelected()) {
            return true;
        } return false;
    }

    private boolean doesLocationExist() {
        for(int i = 0; i < request.getLocation().size(); i++) {
            if (editLocField.equals(request.getLocation().get(i))) {
                return true;
            } return false;
        }
    }

    private boolean doesPersonExist() {
        for(int i = 0; i < request.getStaff().size(); i++) {
            if (editPeopleField.equals(request.getStaff().get(i))) {
                return true;
            } return false;
        }
    }

    private Request makeDummyRequest(){
        Request request = new Request("Bobby", "Bobby wants a good steak.", "King of the Hill");
        request.setDateCompleted(new Date());
        ArrayList<String> staff = new ArrayList<String>();
        staff.add("Hank");
        staff.add("Peggy");
        request.setStaff(staff);
        return request;
    }

    public void handleAddLocation() {
        String newLoc = editLocField.getText();
        if(!doesLocationExist()) {
            location.add(newLoc);
        } editLocationErrorLabel.setText("Location Already Listed.");
    }

    public void handleDeleteLocation() {
        for(int i = 0; i < request.getLocation().size(); i++) {
            if (editLocField.equals(request.getLocation().get(i))) {
                request.getLocation().remove(request.getLocation().get(i));
                return;
            }
        } editLocationErrorLabel.setText("Location Does Not Exist");
    }

    public void handleAddPeople() {
        String newPer = editPeopleField.getText();
        if(!doesPersonExist()) {
            staff.add(newPer);
        } editPeopleErrorLabel.setText("Person Already Listed.");
    }

    public void handleDeletePeople() {
        for(int i = 0; i < request.getStaff().size(); i++) {
            if (editPeopleField.equals(request.getStaff().get(i))) {
                request.getStaff().remove(request.getStaff().get(i));
                return;
            }
        } editPeopleErrorLabel.setText("Person Does Not Exist");
    }

    public void handleCancel() { App.rightDrawerRoot.set("../views/ViewRequest.fxml"); }

    public void handleSaveRequest() {
        if(!(checkCreaDate())){
            return;
        }
        //Replace with getter of request or set request as class level variable.
        Request request = makeDummyRequest();

        request.setTitle(editTitleField.getText());
        request.setDescription(editDescripArea.getText());
        request.setLocation(editLocField.getText());
        //save added people

        Date dateCompleted;
        if (isChecked()) {
            dateCompleted = new Date();
        } else {
            dateCompleted = null;
        }

        updateRequest(request.getrequestID(), editTitleField.getText(), editDescripArea.getText(), dateCompleted,
                location, editTypeOfRequestField.getText(), people, editCreatorField.getText());
    }
}
