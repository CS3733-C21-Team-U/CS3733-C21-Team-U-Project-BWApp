package edu.wpi.u.controllers.request;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;

public class RequestListItemNewController extends AnchorPane {

    public RequestListItemContainerController parent;
    public Region pushDown1;

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


    public RequestListItemNewController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/request/RequestListItemNew.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();
    }

//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        RequiredFieldValidator assigneeValidator = new RequiredFieldValidator();
//        assigneeValidator.setMessage("Valid Assignee Required");
//        RequiredFieldValidator locationValidator = new RequiredFieldValidator();
//        locationValidator.setMessage("Valid Location Required");
//        editAssigneesField.getValidators().add(assigneeValidator);//Assignee and location validator setup here
//        editLocationsField.getValidators().add(locationValidator);
//        ArrayList<String> assingneeList = new ArrayList<String>(App.userService.getEmployeeIDByType(parent.request.getRelevantRole()).keySet());
//        AutoCompletionBinding<String> autoFillAssignees = TextFields.bindAutoCompletion(editAssigneesField , assingneeList);
//        Set<String> strings = App.mapService.getLongNames().keySet();
//        AutoCompletionBinding<String> autoFillStart = TextFields.bindAutoCompletion(editLocationsField , strings);
//
//
//        //Set Existing values for fields
//        editTitleField.setText( parent.request.getGenericRequest().getTitle());
//        editDescriptionField.setText( parent.request.getGenericRequest().getDescription());
//        editDateNeededField.setValue( parent.request.getGenericRequest().getDateNeeded().toLocalDateTime().toLocalDate());
//        editTimeNeededField.setValue( parent.request.getGenericRequest().getDateNeeded().toLocalDateTime().toLocalTime());
//        makeListView( parent.request.getGenericRequest().getAssignees(), editAssigneesListView);
//        //load in name as opposed to Node Ids
//        ArrayList<String> locationNames = new ArrayList<>();
//        for(String s: parent.request.getGenericRequest().getLocations()){
//            locationNames.add(App.mapService.getNodeFromID(s).getLongName());
//        }
//
//        makeListView( locationNames, editLocationsListView);
//        specificTextFields = generateSpecificFields();
//
//        editAssigneesListView.setOnMouseClicked(event -> editAssigneesField.setText(editAssigneesListView.getItems().get(editAssigneesListView.getSelectionModel().getSelectedIndex())));
//        editLocationsListView.setOnMouseClicked(event -> editLocationsField.setText(editLocationsListView.getItems().get(editLocationsListView.getSelectionModel().getSelectedIndex())));
//
//        /* adding items to the list view */
//        /*making list view horizontal*/
////        editAssigneesListView.setOrientation(Orientation.HORIZONTAL);
////        /* creating horizontal box to add item objects */
////        HBox hbox = new HBox(editAssigneesListView);
//
//
//
//
//    }

    /**
     * Pull from fields, and run update request
     */
    public void handleSaveButton(){
        ArrayList<String> locationsToAdd = new ArrayList<String>();
        for(String s :editLocationsListView.getItems()){
            locationsToAdd.add(longNamestoID.get(s));
        }
        ArrayList<String> assigneesToAdd = new ArrayList<>(editAssigneesListView.getItems());

        parent.request.updateRequest(editTitleField.getText(), editDescriptionField.getText(),
                Timestamp.valueOf(LocalDateTime.of(editDateNeededField.getValue(), editTimeNeededField.getValue())),
                locationsToAdd, assigneesToAdd, requestSpecificItems());
        App.requestService.updateRequest(parent.request);


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
                    parent.switchToCollapsed();
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
        JFXTextField[] ans = new JFXTextField[ parent.request.getSpecificFields().length];
        for(int i = 0; i <  parent.request.getSpecificFields().length; i++) {
            HBox h = new HBox();

            JFXTextField j = new JFXTextField();
            j.setPromptText( parent.request.getSpecificFields()[i]);
            j.setLabelFloat(true);
            j.setStyle("-fx-pref-width: 400px");
            j.setStyle("-fx-pref-height: 50px");
            j.setStyle("-fx-font-size: 16px");
            j.setText( parent.request.getSpecificData().get(i));

            ans[i] = j;
            extraFieldsVBox.getChildren().add(j);
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
