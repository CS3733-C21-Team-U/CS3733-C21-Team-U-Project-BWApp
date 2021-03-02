package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import edu.wpi.u.requests.*;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class NERController {

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


    public void initialize() throws IOException {
        currRequest = App.requestService.getRequests().get(App.lastClickedRequestNumber).getGenericRequest();

        editTitleField.setText(currRequest.getTitle());
        editDescriptionField.setText(currRequest.getDescription());

        for (String l : currRequest.getLocation()) { //Locations
            makeEditLocationChipView.getChips().add(l);
        }

        for (String a : currRequest.getAssignee()) { //Assignees
            makeEditStaffChipView.getChips().add(a);
        }
    }

    @FXML public void handleSaveNewEditRequest() { //TODO: visibility?

    }

    @FXML public void makeEditDateCheckBox() { //TODO: visibility?

    }

    @FXML public void HandleMakeEditCancelButton() { //TODO: visibility?

    }
}
