package edu.wpi.u.controllers.request;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.requests.Comment;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;

public class RequestListItemEditController extends AnchorPane implements Initializable {

    public RequestListItemContainerController parent;
    public Region pushDown1;

    public JFXTextField editTitleField;
    public JFXTextArea editDescriptionField;
    public JFXDatePicker editDateNeededField;
    public JFXTimePicker editTimeNeededField;
    public JFXTextField editAssigneesField;
    public Label titleLabel;

    @FXML
    public JFXListView<String> editAssigneesListView;// = new JFXListView<String>();
    public JFXTextField editLocationsField;

    @FXML
    public JFXListView<String> editLocationsListView;// = new JFXListView<String>();

    @FXML
    public SVGPath typeIconSVG;

    public VBox extraFieldsVBox;
    private JFXTextField[] specificTextFields;
    HashMap<String, String> longNamestoID;
    Set<String> existingAssignee;
    ArrayList<String> oldAssignees;
    AutoCompletionBinding<String> autoFillAssignees;
    AutoCompletionBinding<String> autoFillStart;


    public RequestListItemEditController(RequestListItemContainerController parent) throws IOException {
        this.parent = parent; //THIS SHOULD ALWAYS BE FIRST
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/request/RequestListItemEdit.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RequiredFieldValidator assigneeValidator = new RequiredFieldValidator();
        assigneeValidator.setMessage("Valid Assignee Required");
        RequiredFieldValidator locationValidator = new RequiredFieldValidator();
        locationValidator.setMessage("Valid Location Required");
        editAssigneesField.getValidators().add(assigneeValidator);//Assignee and location validator setup here
        editLocationsField.getValidators().add(locationValidator);
        existingAssignee = App.userService.getEmployeeIDByType(parent.request.getRelevantRole()).keySet();
        autoFillAssignees = TextFields.bindAutoCompletion(editAssigneesField , existingAssignee);
        //longNamestoID = App.mapService.getLongNames(parent.request.getGenericRequest().);
        longNamestoID  = App.mapService.getLongNames();
        autoFillStart = TextFields.bindAutoCompletion(editLocationsField , longNamestoID.keySet());
        titleLabel.setText("Edit " + parent.request.getType() + " Request");


        //Set Existing values for fields
        typeIconSVG.setContent(parent.getIcon(parent.request.getType()));
        editTitleField.setText( parent.request.getGenericRequest().getTitle());
        editDescriptionField.setText( parent.request.getGenericRequest().getDescription());
        editDateNeededField.setValue( parent.request.getGenericRequest().getDateNeeded().toLocalDateTime().toLocalDate());
        editTimeNeededField.setValue( parent.request.getGenericRequest().getDateNeeded().toLocalDateTime().toLocalTime());
        makeListView( parent.request.getGenericRequest().getAssignees(), editAssigneesListView);
        ArrayList<String> locationNames = new ArrayList<>();
        for(String s: parent.request.getGenericRequest().getLocations()){
            locationNames.add(App.mapService.getNodeFromID(s).getLongName());
        }

        makeListView( locationNames, editLocationsListView);
        specificTextFields = generateSpecificFields();

        editAssigneesListView.setOnMouseClicked(event -> editAssigneesField.setText(editAssigneesListView.getItems().get(editAssigneesListView.getSelectionModel().getSelectedIndex())));
        editLocationsListView.setOnMouseClicked(event -> editLocationsField.setText(editLocationsListView.getItems().get(editLocationsListView.getSelectionModel().getSelectedIndex())));

        System.out.println("In requestEdit init of" + parent.request.getGenericRequest().getTitle());
        //listener for filling edit fields
        parent.editRequestFillFields.addListener((o, q, p) -> {
            existingAssignee = App.userService.getEmployeeIDByType(parent.request.getRelevantRole()).keySet();
            autoFillAssignees = TextFields.bindAutoCompletion(editAssigneesField , existingAssignee);
            longNamestoID  = App.mapService.getLongNames();
            autoFillStart = TextFields.bindAutoCompletion(editLocationsField , longNamestoID.keySet());
            oldAssignees = new ArrayList<>(parent.request.getGenericRequest().getAssignees());

        });
    }

    /**
     * Pull from fields, and run update request
     */
    public void handleSaveButton(){
        //oldAssignees = new ArrayList<>(parent.request.getGenericRequest().getAssignees());
        ArrayList<String> locationsToAdd = new ArrayList<String>();
        for(String s :editLocationsListView.getItems()){
            locationsToAdd.add(longNamestoID.get(s));
        }
        ArrayList<String> assigneesToAdd = new ArrayList<>(editAssigneesListView.getItems());
//        assigneesToAdd.forEach(s -> {
//            if (oldAssignees.contains(s)){
//                assigneesToAdd.remove(s);
//            }
//        });
        Comment c = parent.request.updateRequest(editTitleField.getText(), editDescriptionField.getText(),
                Timestamp.valueOf(LocalDateTime.of(editDateNeededField.getValue(), editTimeNeededField.getValue())),
                locationsToAdd, assigneesToAdd, requestSpecificItems());
        App.requestService.updateRequest(parent.request, c);

        //todo : change to observable list thing Kohmei said
        Thread t = new Thread(() ->{
            Platform.runLater(() ->{
                for (String r : assigneesToAdd){
                    if (!oldAssignees.contains(r)){
                        System.out.println("New username: " + r);
                        switch (App.userService.getPreferredContactMethod(r)) {
                            case "Both":
                                System.out.println("Here at BOTH");
                                App.emailService.sendMail(App.userService.getEmail(r), parent.request);
                                App.textingService.sendText(App.userService.getPhoneNumberFromUserName(r), parent.request);
                                break;
                            case "Email":
                                System.out.println("Here at EMAIL");
                                App.emailService.sendMail(App.userService.getEmail(r), parent.request);
                                break;
                            case "SMS":
                                System.out.println("Here at SMS");
                                App.textingService.sendText(App.userService.getPhoneNumberFromUserName(r), parent.request);
                                break;
                        }
                    }
                }
            });
        });
        t.start();
        this.parent.needUpdate.set(!this.parent.needUpdate.get());
        this.parent.switchFromEditToExpanded();
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
            button1.setOnAction(event -> dialog.close());
            button2.setOnAction(event -> {
                dialog.close();
                parent.switchToCollapsed();
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
        JFXTextField[] ans = new JFXTextField[ parent.request.getSpecificFields().length];
        for(int i = 0; i <  parent.request.getSpecificFields().length; i++) {
            HBox h = new HBox();

            JFXTextField j = new JFXTextField();
            j.setPromptText( parent.request.getSpecificFields()[i]);
            j.setLabelFloat(true);
            j.setStyle("-fx-pref-width: 400px");
            j.setStyle("-fx-pref-height: 50px");
            j.setStyle("-fx-font-size: 12px");
            j.setText( parent.request.getSpecificData().get(i));

            ans[i] = j;
            Region r1 = new Region();
            r1.setPrefHeight(25);
            extraFieldsVBox.getChildren().add(0, r1);
            extraFieldsVBox.getChildren().add(0, j);
        }
        return ans;
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
        if (editLocationsField.getText().equals("") || !longNamestoID.keySet().contains(editLocationsField.getText())) {
            editLocationsField.validate();
        } else {
            try{
                //App.mapService.get
                editLocationsListView.getItems().add(editLocationsField.getText());
                editLocationsField.setText("");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void deleteLocation(){
        editLocationsListView.getItems().remove(editLocationsField.getText());
        editLocationsField.setText("");

    }

}
