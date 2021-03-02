package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import edu.wpi.u.requests.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

public class NERController {

    //Generic Request Fields
    @FXML TextField makeEditTitleField;

    @FXML TextArea makeEditDescriptionField;

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

    @FXML
    JFXCheckBox makeEditDateCheckBox;


    private IRequest currIRequest;
    private Request currRequest;


    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void initialize() throws IOException {

        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Integer Required");
        madeEditMaintenanceField1.getValidators().add(validator);
        madeEditMaintenanceField1.focusedProperty().addListener((o, oldVal, newVal) -> {
                    if (!(isInteger(newVal.toString()))) {
                        madeEditMaintenanceField1.validate();
                    }
                });

        currIRequest = App.requestService.getRequests().get(App.lastClickedRequestNumber);
        currRequest = currIRequest.getGenericRequest();

        System.out.println(currRequest.getTitle());

        makeEditTitleField.setText(currRequest.getTitle());
        makeEditDescriptionField.setText(currRequest.getDescription());

        //TODO: Probably broken
        for (String l : currRequest.getLocation()) { //Locations
            makeEditLocationChipView.getChips().add(l);
        }

        for (String a : currRequest.getAssignee()) { //Assignees
            makeEditStaffChipView.getChips().add(a);
        }
    }

    public LinkedList<Serializable> requestSpecificItems(String type) {
        LinkedList<Serializable> specifics = new LinkedList<>();
        switch(type) {
            case("Maintenance") :
                specifics.add(madeEditMaintenanceField.getText());
                specifics.add(madeEditMaintenanceField1.getText());
                break;
            case("Laundry") :
                //add stuff
                break;
            case("Security"):
                //add stuff
                break;
            default:
                System.out.println("lmao you screwed up");
        }
        return specifics;
    }

    @FXML public void handleSaveNewEditRequest() throws IOException { //TODO: visibility?

        LinkedList<String> locationsToAdd = new LinkedList<>();
        for(Object l : makeEditLocationChipView.getChips()) { //may break
            locationsToAdd.add(l.toString());
        }

        LinkedList<String> assigneesToAdd = new LinkedList<>();
        for(Object l : makeEditLocationChipView.getChips()) { //may break
            assigneesToAdd.add(l.toString());
        }

        currRequest = App.requestService.getRequests().get(App.lastClickedRequestNumber).getGenericRequest();
        App.requestService.updateRequest(currRequest.getRequestID(),
                makeEditDescriptionField.getText(),
                assigneesToAdd,
                makeEditTitleField.getText(),
                locationsToAdd,
                null, //TODO: fill this in
                currRequest.getType(),
                currRequest.getCreator(),
                requestSpecificItems(currRequest.getType()));

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewViewRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);

    }

    @FXML public void HandleMakeEditCancelButton() throws IOException { //TODO: visibility?
        //exits off of fxml without doing shit
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewViewRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }
}
