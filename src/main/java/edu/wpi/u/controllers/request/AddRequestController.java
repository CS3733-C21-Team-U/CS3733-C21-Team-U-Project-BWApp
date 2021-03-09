package edu.wpi.u.controllers.request;

import com.jfoenix.controls.*;
import edu.wpi.u.App;
import edu.wpi.u.requests.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class AddRequestController {


    public JFXTextField makeTitleField;
    public JFXTextArea makeDescriptionField;
    public JFXDatePicker makeDate2BCompleteDatePicker;
    public JFXChipView makeLocationChipView;
    public JFXChipView makeStaffChipView;
    public VBox VBoxToAddTo;
    public Label specificTitle;
    public Label errorMsg;

    private SpecificRequest currSpecificRequest;
    private JFXTextField[] specificTextFields;

    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public JFXTextField[] generateSpecificFields() {

        specificTitle.setText(currSpecificRequest.getType() + " Fields");
        JFXTextField[] ans = new JFXTextField[currSpecificRequest.getSpecificFields().length];
        for(int i = 0; i < currSpecificRequest.getSpecificFields().length; i++) {
            HBox h = new HBox();

            JFXTextField j = new JFXTextField();
            j.setPromptText(currSpecificRequest.getSpecificFields()[i]);
            j.setLabelFloat(true);
            j.setStyle("-fx-pref-width: 400px");
            j.setStyle("-fx-pref-height: 50px");
            j.setStyle("-fx-font-size: 16px");

            ans[i] = j;

            VBoxToAddTo.getChildren().add(j);
        }
        return ans;
    }

    @FXML
    public void initialize() throws IOException {
        String type = App.newNodeType;
        currSpecificRequest = new RequestFactory().makeRequest(type);

        //TODO: redo so it does not use a switch statement
        specificTextFields = generateSpecificFields();

//        RequiredFieldValidator validator = new RequiredFieldValidator();
//        validator.setMessage("Integer Required");
//        madeMaintenanceFieldMachineUsed.getValidators().add(validator);
//        madeMaintenanceFieldPriority.focusedProperty().addListener((o, oldVal, newVal) -> {
//            if (!(isInteger(newVal.toString()))) {
//                madeMaintenanceFieldPriority.validate();
//            }
//        });

    }

    /**
     * This returns the needed array list.
     * @return
     */
    public ArrayList<String> requestSpecificItems() {
        ArrayList<String> specifics = new ArrayList<>();
        for(JFXTextField j : specificTextFields) {
            specifics.add(j.getText());
        }
        return specifics;
    }


    /**
     * This is the primary function of this page.
     * Calls requestService to add them.
     * @throws IOException
     */
    @FXML
    public void handleSaveNewRequest() throws IOException {
        try {
            ArrayList<String> staff = new ArrayList<String>(makeStaffChipView.getChips());
            ArrayList<String> locations = new ArrayList<String>(makeLocationChipView.getChips());
            ArrayList<String> specifics = requestSpecificItems();

            Random rand = new Random();
            int requestID = rand.nextInt();
            String ID = Integer.toString(requestID);//make a random id

            //make components of specifc request,  then set them
            Comment primaryComment = new Comment(makeTitleField.getText(), makeDescriptionField.getText(),
                    App.userService.getActiveUser().getName(), CommentType.PRIMARY, Timestamp.valueOf(makeDate2BCompleteDatePicker.getValue().atStartOfDay()));
            Request newRequest = new Request(ID, new Timestamp(System.currentTimeMillis()), locations, staff, primaryComment);
            App.requestService.addRequest(currSpecificRequest.setRequest(newRequest).setSpecificData(specifics));

            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/ViewRequestList.fxml"));
            anchor.getChildren().clear();
            anchor.getChildren().add(root);
        } catch (Exception e) {
            errorMsg.setVisible(true);
        }

    }

    @FXML
    public void HandleMakeCancelButton() throws IOException {
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/ViewRequestList.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);

    }

    public void HandleMakeEditCancelButton(ActionEvent actionEvent) {
    }

    public void handleSaveNewEditRequest(ActionEvent actionEvent) {
    }
}
