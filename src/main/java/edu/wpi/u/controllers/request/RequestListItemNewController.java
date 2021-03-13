package edu.wpi.u.controllers.request;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.requests.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class RequestListItemNewController extends AnchorPane implements Initializable{

    public JFXTextField editTitleField;
    public JFXTextArea editDescriptionField;
    public JFXDatePicker editDateNeededField;
    public JFXTimePicker editTimeNeededField;

    public JFXTextField editAssigneesField;

    @FXML
    public JFXListView<String> editAssigneesListView;// = new JFXListView<String>();
    public JFXTextField editLocationsField;

    @FXML
    public JFXListView<String> editLocationsListView;// = new JFXListView<String>();

    public VBox extraFieldsVBox;
    private JFXTextField[] specificTextFields;
    HashMap<String, String> longNamestoID;
    Set<String> existingAssignee;

    SpecificRequest currSpecificRequest;


    public RequestListItemNewController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/request/RequestListItemNew.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String type = App.newNodeType;
        currSpecificRequest = new RequestFactory().makeRequest(type);
        specificTextFields = generateSpecificFields();

        RequiredFieldValidator assigneeValidator = new RequiredFieldValidator();
        assigneeValidator.setMessage("Valid Assignee Required");
        RequiredFieldValidator locationValidator = new RequiredFieldValidator();
        locationValidator.setMessage("Valid Location Required");
        editAssigneesField.getValidators().add(assigneeValidator);//Assignee and location validator setup here
        editLocationsField.getValidators().add(locationValidator);
        existingAssignee = App.userService.getEmployeeIDByType(currSpecificRequest.getRelevantRole()).keySet();
        AutoCompletionBinding<String> autoFillAssignees = TextFields.bindAutoCompletion(editAssigneesField , existingAssignee);
        longNamestoID  = App.mapService.getLongNames();
        AutoCompletionBinding<String> autoFillStart = TextFields.bindAutoCompletion(editLocationsField , longNamestoID.keySet());

        RequiredFieldValidator validator1 = new RequiredFieldValidator();
        validator1.setMessage("Title Required");
        editTitleField.getValidators().add(validator1);
        editTitleField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                editTitleField.validate();
            }
        });

        RequiredFieldValidator validator2 = new RequiredFieldValidator();
        validator2.setMessage("Description Required");
        editDescriptionField.getValidators().add(validator2);
        editDescriptionField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                editDescriptionField.validate();
            }
        });

        RequiredFieldValidator validator3 = new RequiredFieldValidator();
        validator3.setMessage("Required Field");
        editDateNeededField.getValidators().add(validator3);
        editDateNeededField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                editDateNeededField.validate();
            }
        });

        RequiredFieldValidator validator4 = new RequiredFieldValidator();
        validator4.setMessage("Required Field");
        editTimeNeededField.getValidators().add(validator4);
        editTimeNeededField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                editTimeNeededField.validate();
            }
        });

        editAssigneesListView.setOnMouseClicked(event -> editAssigneesField.setText(editAssigneesListView.getItems().get(editAssigneesListView.getSelectionModel().getSelectedIndex())));
        editLocationsListView.setOnMouseClicked(event -> editLocationsField.setText(editLocationsListView.getItems().get(editLocationsListView.getSelectionModel().getSelectedIndex())));

    }

    /**
     * Pull from fields, and add request
     */
    public void handleSaveButton(){
        if(!editTitleField.getText().equals("") &&
            !editDescriptionField.getText().equals("") &&
            editDateNeededField.getValue() != null &&
            editTimeNeededField.getValue() != null &&
            checkSpecialFields(requestSpecificItems())){
            ArrayList<String> locationsToAdd = new ArrayList<String>();
            for(String s :editLocationsListView.getItems()){
                locationsToAdd.add(longNamestoID.get(s));
            }
            ArrayList<String> assigneesToAdd = new ArrayList<>(editAssigneesListView.getItems());


            Random rand = new Random();
            int requestID = rand.nextInt();
            String ID = Integer.toString(requestID);//make a random id

            //make components of specifc request,  then set them
        Comment primaryComment = new Comment(editTitleField.getText(), editDescriptionField.getText(),
            App.userService.getActiveUser().getUserName(), CommentType.PRIMARY);

        Request newRequest = new Request(ID, Timestamp.valueOf(LocalDateTime.of(editDateNeededField.getValue(), editTimeNeededField.getValue())),
            locationsToAdd, assigneesToAdd, primaryComment);
            App.requestService.addRequest(currSpecificRequest.setRequest(newRequest).setSpecificData(requestSpecificItems()));

            App.newReqVBox.getChildren().clear();
            App.VBoxChanged.set(!App.VBoxChanged.get());

            App.newReqVBox.getChildren().clear();
            App.VBoxChanged.set(!App.VBoxChanged.get());
            App.requestRedrawFlag.set(!App.requestRedrawFlag.get());

        }else if(editTitleField.getText().equals("")){
            editTitleField.validate();
        }else if(editDescriptionField.getText().equals("")){
            editDescriptionField.validate();
        }else if(editDateNeededField.getValue() != null){
            editDateNeededField.validate();
        }else if(editTimeNeededField.getValue() != null){
            editTimeNeededField.validate();
        }
    }


    private boolean checkSpecialFields(ArrayList<String> input){
        for(String s: input){
            if(s.equals("")){
                return false;
            }
        }
        return true;
    }

    public void handleCancelButton(){
            JFXDialogLayout content = new JFXDialogLayout();
            Label header = new Label("Exit without saving changes?");
            header.getStyleClass().add("headline-2");
            content.setHeading(header);
            content.getStyleClass().add("dialogue");
            JFXDialog dialog = new JFXDialog(App.throwDialogHerePane, content, JFXDialog.DialogTransition.CENTER);
            JFXButton button1 = new JFXButton("CANCEL");
            JFXButton button2 = new JFXButton("EXIT");
            button1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dialog.close();
                }
            });
            button2.setOnAction(new EventHandler<ActionEvent>() {
                @SneakyThrows
                @Override
                public void handle(ActionEvent event) {
                    dialog.close();
                    App.newReqVBox.getChildren().clear();
                    App.VBoxChanged.set(!App.VBoxChanged.get());
                }
            });
            button1.getStyleClass().add("button-text");
            button2.getStyleClass().add("button-contained");
            ArrayList<Node> actions = new ArrayList<>();
            actions.add(button1);
            actions.add(button2);
            content.setActions(actions);
            dialog.show();
    }

    public JFXTextField[] generateSpecificFields() {

        //specificTitle.setText(currSpecificRequest.getType());
        JFXTextField[] ans = new JFXTextField[ currSpecificRequest.getSpecificFields().length];
        for(int i = 0; i <  currSpecificRequest.getSpecificFields().length; i++) {

            JFXTextField j = new JFXTextField();
            j.setPromptText( currSpecificRequest.getSpecificFields()[i]);
            j.setLabelFloat(true);
            j.setStyle("-fx-pref-width: 400px");
            j.setStyle("-fx-pref-height: 50px");
            j.setStyle("-fx-font-size: 16px");
           // j.setText( currSpecificRequest.getSpecificData().get(i));

            ans[i] = j;
            extraFieldsVBox.getChildren().add(0,j);
            Region r1 = new Region();
            r1.setPrefHeight(25);
            extraFieldsVBox.getChildren().add(0,r1);
        }
        return ans;
    }


    /**
     * Take the get values from unique fields, put it in a ArrayList
     * @return
     */
    public ArrayList<String> requestSpecificItems() {
        ArrayList<String> specifics = new ArrayList<>();
        for(JFXTextField j : specificTextFields) {
            specifics.add(j.getText());
        }
        return specifics;
    }



    public void makeListView(ArrayList<String> list, JFXListView<String> res){
        ObservableList<String> something = FXCollections.observableList(list);
        res.setItems(something);
    }

    public void addAssignee(){
        if(editAssigneesField.getText().equals("") || !existingAssignee.contains(editAssigneesField.getText())){
            editAssigneesField.validate();
        }else {
            editAssigneesListView.getItems().add(editAssigneesField.getText());
            editAssigneesField.setText("");
        }
    }
    public void deleteAssignee(){
        editAssigneesListView.getItems().remove(editAssigneesField.getText());
        editAssigneesField.setText("");
    }

    public void addLocation() {
        if (editLocationsField.getText().equals("") || !longNamestoID.containsKey(editLocationsField.getText())) {
            editLocationsField.validate();
        } else {
            try{
                //App.mapService.get
                editLocationsListView.getItems().add(editLocationsField.getText());
                editLocationsField.setText("");
            }catch(Exception e){

            }
        }
    }
        public void deleteLocation(){
            editLocationsListView.getItems().remove(editLocationsField.getText());
            editLocationsField.setText("");

        }

    /*
    App.mapService.getLongNames(string NodeID);




     */

}
