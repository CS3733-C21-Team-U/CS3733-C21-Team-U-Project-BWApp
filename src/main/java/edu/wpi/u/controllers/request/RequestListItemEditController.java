package edu.wpi.u.controllers.request;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
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

    @FXML
    public JFXListView<String> editAssigneesListView;// = new JFXListView<String>();
    public JFXTextField editLocationsField;

    @FXML
    public JFXListView<String> editLocationsListView;// = new JFXListView<String>();

    public VBox extraFieldsVBox;
    private JFXTextField[] specificTextFields;




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
        assigneeValidator.setMessage("Valid Employee Required");
        RequiredFieldValidator locationValidator = new RequiredFieldValidator();
        locationValidator.setMessage("Valid Employee Required");
        editAssigneesField.getValidators().add(assigneeValidator);//Assignee and location validator setup here
        editLocationsField.getValidators().add(locationValidator);
        AutoCompletionBinding<String> autoFillAssignees = TextFields.bindAutoCompletion(editAssigneesField , App.userService.getEmployeeIDByType(parent.request.getRelevantRole()).keySet());
        Set<String> strings = App.mapService.getLongNames().keySet();
        AutoCompletionBinding<String> autoFillStart = TextFields.bindAutoCompletion(editLocationsField , strings);


//        parent.request.
        //Set Existing values for fields
        editTitleField.setText( parent.request.getGenericRequest().getTitle());
        editDescriptionField.setText( parent.request.getGenericRequest().getDescription());
        editDateNeededField.setValue( parent.request.getGenericRequest().getDateNeeded().toLocalDateTime().toLocalDate());
        editTimeNeededField.setValue( parent.request.getGenericRequest().getDateNeeded().toLocalDateTime().toLocalTime());
        makeListView( parent.request.getGenericRequest().getAssignees(), editAssigneesListView);
        makeListView( parent.request.getGenericRequest().getLocations(), editLocationsListView);
        specificTextFields = generateSpecificFields();

        editAssigneesListView.setOnMouseClicked(event -> editAssigneesField.setText(editAssigneesListView.getItems().get(editAssigneesListView.getSelectionModel().getSelectedIndex())));
        editLocationsListView.setOnMouseClicked(event -> editLocationsField.setText(editLocationsListView.getItems().get(editLocationsListView.getSelectionModel().getSelectedIndex())));

        /* adding items to the list view */
        /*making list view horizontal*/
//        editAssigneesListView.setOrientation(Orientation.HORIZONTAL);
//        /* creating horizontal box to add item objects */
//        HBox hbox = new HBox(editAssigneesListView);




    }

    /**
     * Pull from fields, and run update request
     */
    public void handleSaveButton(){
        ArrayList<String> locationsToAdd = new ArrayList<>(editLocationsListView.getItems());
        ArrayList<String> assigneesToAdd = new ArrayList<>(editAssigneesListView.getItems());

        parent.request.updateRequest(editTitleField.getText(), editDescriptionField.getText(),
                Timestamp.valueOf(editDateNeededField.getValue().atStartOfDay()),
                locationsToAdd, assigneesToAdd, requestSpecificItems());
        App.requestService.updateRequest(parent.request);


        this.parent.needUpdate.set(!this.parent.needUpdate.get());
        this.parent.switchFromEditToExpanded();
        //SCENE Switch
//        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
//        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/ViewRequestList.fxml"));
//        anchor.getChildren().clear();
//        anchor.getChildren().add(root);
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
        if(editAssigneesField.getText().equals("")){
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
        if (editLocationsField.getText().equals("")) {
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
