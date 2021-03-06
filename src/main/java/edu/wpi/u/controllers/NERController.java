package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import edu.wpi.u.requests.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class NERController {

    public AnchorPane SpecificRequestAPane;
    public HBox HBoxToClone;
    public VBox VBoxToAddTo;
    public Label specificTitle;
    public Label errorMsg;
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


    private SpecificRequest currSpecificRequest;
    private Request currRequest;
    private JFXTextField[] specificTextFields;


    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

/*    RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Integer Required");
        madeEditMaintenanceField1.getValidators().add(validator);
        madeEditMaintenanceField1.focusedProperty().addListener((o, oldVal, newVal) -> {
        if (!(isInteger(newVal.toString()))) {
            madeEditMaintenanceField1.validate();
        }
    });*/

    public JFXTextField[] generateSpecificFields() {

        specificTitle.setText(currSpecificRequest.getType());
        JFXTextField[] ans = new JFXTextField[currSpecificRequest.getSpecificFields().length];
        for(int i = 0; i < currSpecificRequest.getSpecificFields().length; i++) {
            HBox h = new HBox();

            JFXTextField j = new JFXTextField();
            j.setPromptText(currSpecificRequest.getSpecificFields()[i]);
            j.setLabelFloat(true);
            j.setStyle("-fx-pref-width: 400px");
            j.setStyle("-fx-pref-height: 50px");
            j.setStyle("-fx-font-size: 16px");
            j.setText(currSpecificRequest.getSpecificData().get(i).toString());

            ans[i] = j;

//            h.setAlignment(HBoxToClone.getAlignment());
//            h.setSpacing(HBoxToClone.getSpacing());
//            h.getChildren().add(j);
//            h.setId(Integer.toString(i));
            VBoxToAddTo.getChildren().add(j);
        }
        return ans;
    }

    public void initialize() throws IOException {
//FOR KOHMEI -------------------------------------
        //HERE is the IREQUEST that you will use to get the label, and the fields you need
        //get them like so:
        //        currIRequest.getSpecificFields() - string array of FXML LABELS
        //        currIRequest.getSpecificData() - LinkedList of INFORMATION, corresponding to labels
        //        currIRequest.getSpecificDataCode() - string of chars describing what datatype they are if you can do error checking (see RequestData line 177 -190)

        currSpecificRequest = App.requestService.getRequests().get(App.lastClickedRequestNumber);
        currRequest = currSpecificRequest.getGenericRequest();

        System.out.println(currRequest.getTitle());

        specificTextFields = generateSpecificFields();

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
     * @return
     */
    public ArrayList<String> requestSpecificItems() {
        ArrayList<String> specifics = new ArrayList<>();
        for(JFXTextField j : specificTextFields) {
            specifics.add(j.getText());
        }
        return specifics;
    }

    @FXML public void handleSaveNewEditRequest() throws IOException { //TODO: visibility?
        try {
            LinkedList<String> locationsToAdd = new LinkedList<>();
            for(Object l : makeEditLocationChipView.getChips()) { //may break
                locationsToAdd.add(l.toString());
            }

            LinkedList<String> assigneesToAdd = new LinkedList<>();
            for(Object l : makeEditLocationChipView.getChips()) { //may break
                assigneesToAdd.add(l.toString());
            }

            //NEW
            currRequest.setTitle(makeEditTitleField.getText());
            currRequest.setDescription(makeEditDescriptionField.getText());
            currRequest.setAssignee(assigneesToAdd);
            currRequest.setLocation(locationsToAdd);
            LocalDate localDate = makeEditDate2BCompleteDatePicker.getValue();
            //Date date = Date.from(Instant.from(localDate.atStartOfDay(ZoneId.systemDefault())));
            Date date= new Date();
            currRequest.setDateNeeded(date);
            currSpecificRequest.setSpecificData(requestSpecificItems());
            App.requestService.updateRequest(currSpecificRequest);


            //SCENE Switch
            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/ViewRequestList.fxml"));
            anchor.getChildren().clear();
            anchor.getChildren().add(root);
        } catch (Exception e) {
            errorMsg.setVisible(true);
        }



    }

    @FXML public void HandleMakeEditCancelButton() throws IOException { //TODO: visibility?
        //exits off of fxml without doing shit
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/ViewRequestList.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }
}
