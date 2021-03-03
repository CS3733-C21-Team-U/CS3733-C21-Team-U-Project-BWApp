package edu.wpi.u.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.requests.IRequest;
import edu.wpi.u.requests.Request;
import edu.wpi.u.requests.RequestFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

public class NewRequestController {


    public JFXTextField makeTitleField;
    public JFXTextArea makeDescriptionField;
    public JFXDatePicker makeDate2BCompleteDatePicker;
    public JFXChipView makeLocationChipView;
    public JFXChipView makeStaffChipView;
    public StackPane laundryStack;
    public Pane makeLaundryPane;
    public JFXTextField madeLaundryField;
    public Pane makeSecurityPane;
    public JFXTextField madeSecurityField;
    public Pane makeMaintenancePane;
    public JFXTextField madeMaintenanceFieldMachineUsed;
    public JFXTextField madeMaintenanceFieldPriority;
    public AnchorPane SpecificRequestAPane;
    public VBox VBoxToAddTo;
    public Label specificTitle;
    public HBox HBoxToClone;

    private IRequest currIRequest;
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

        specificTitle.setText(currIRequest.getType() + " Fields");
        JFXTextField[] ans = new JFXTextField[currIRequest.getSpecificFields().length];
        for(int i = 0; i < currIRequest.getSpecificFields().length; i++) {
            HBox h = new HBox();

            JFXTextField j = new JFXTextField();
            j.setPromptText(currIRequest.getSpecificFields()[i]);
            j.setLabelFloat(true);
            j.setStyle("-fx-pref-width: 400px");
            j.setStyle("-fx-pref-height: 50px");
            j.setStyle("-fx-font-size: 16px");

            ans[i] = j;

//            h.setAlignment(HBoxToClone.getAlignment());
//            h.setSpacing(HBoxToClone.getSpacing());
//            h.getChildren().add(j);
//            h.setId(Integer.toString(i));
            VBoxToAddTo.getChildren().add(j);
        }
        return ans;
    }

    @FXML
    public void initialize() throws IOException {
        /*javafx.scene.Node node = (javafx.scene.Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();*/
        // receiveData Step 2
        String type = App.newNodeType;
        currIRequest = new RequestFactory().makeRequest(type);

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

    public LinkedList<Serializable> requestSpecificItems() {
        LinkedList<Serializable> specifics = new LinkedList<>();
        for(JFXTextField j : specificTextFields) {
            specifics.add(j.getText());
        }

        //for loop(values stored in TextFields)
        return specifics;
    }


    @FXML
    public void handleSaveNewRequest() throws IOException {
        LinkedList<String> staff = new LinkedList<String>(makeStaffChipView.getChips());
        LinkedList<String> locations = new LinkedList<String>(makeLocationChipView.getChips());
        LinkedList<Serializable> specifics = requestSpecificItems();

        IRequest result = new RequestFactory().makeRequest(App.newNodeType);
        Random rand = new Random();
        int requestID = rand.nextInt();
        String ID = Integer.toString(requestID);//make a random id
        //TODO : fix date bug
        Date needed = Date.from(makeDate2BCompleteDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        // String requestID,LinkedList<String> assignee, Date dateCreated, Date dateCompleted, String description, String title, LinkedList<String> location, String type, String creator) {
        Request newRequest = new Request(ID, staff, new Date(), new Date(), makeDescriptionField.getText() ,makeTitleField.getText(),locations, App.newNodeType, "Creator_here");

        result.setRequest(newRequest);
        result.setSpecificData(specifics);
        App.requestService.addRequest(result);

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewViewRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    @FXML
    public void HandleMakeCancelButton() throws IOException {
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewViewRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);

    }
}
