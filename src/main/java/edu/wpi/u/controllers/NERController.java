package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.sun.javafx.scene.control.skin.LabelSkin;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
        //FOR KOHMEI -------------------------------------
        //HERE is the IREQUEST that you will use to get the label, and the fields you need
        //get them like so:
        //        currIRequest.getSpecificFields() - string array of FXML LABELS
        //        currIRequest.getSpecificData() - LinkedList of INFORMATION, corresponding to labels
        //        currIRequest.getSpecificDataCode() - string of chars describing what datatype they are if you can do error checking (see RequestData line 177 -190)

        currIRequest = App.requestService.getRequests().get(App.lastClickedRequestNumber);
        currRequest = currIRequest.getGenericRequest();

        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Integer Required");
        madeEditMaintenanceField1.getValidators().add(validator);
        madeEditMaintenanceField1.focusedProperty().addListener((o, oldVal, newVal) -> {
                    if (!(isInteger(newVal.toString()))) {
                        madeEditMaintenanceField1.validate();
                    }
                });

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

    /**
     * Take the get values from unique fields, put it in a linkedList
     * @param type
     * @return
     */
    public LinkedList<Serializable> requestSpecificItems(String type) {
        LinkedList<Serializable> specifics = new LinkedList<>();

        //ToDO: KOHMEI - Get rid of switch statement
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

        //for loop(values stored in TextFields)
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

        //NEW
        currRequest.setDescription(makeEditDescriptionField.getText());
        currRequest.setAssignee(assigneesToAdd);
        currRequest.setLocation(locationsToAdd);
        LocalDate localDate = makeEditDate2BCompleteDatePicker.getValue();
        Date date = Date.from(Instant.from(localDate.atStartOfDay(ZoneId.systemDefault())));
        currRequest.setDateNeeded(date);
        currIRequest.setSpecificData(requestSpecificItems(currRequest.getType()));
        App.requestService.updateRequest(currIRequest);


        //SCENE Switch
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
