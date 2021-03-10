package edu.wpi.u.controllers.request;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

public class ButtonPageForNewRequestController extends AnchorPane implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RequiredFieldValidator assigneeValidator = new RequiredFieldValidator();
        assigneeValidator.setMessage("Valid Assignee Required");
        RequiredFieldValidator locationValidator = new RequiredFieldValidator();
        locationValidator.setMessage("Valid Location Required");
        editAssigneesField.getValidators().add(assigneeValidator);//Assignee and location validator setup here
        editLocationsField.getValidators().add(locationValidator);
        ArrayList<String> assingneeList = new ArrayList<String>(App.userService.getEmployeeIDByType(parent.request.getRelevantRole()).keySet());
        AutoCompletionBinding<String> autoFillAssignees = TextFields.bindAutoCompletion(editAssigneesField , assingneeList);
        Set<String> strings = App.mapService.getLongNames().keySet();
        AutoCompletionBinding<String> autoFillStart = TextFields.bindAutoCompletion(editLocationsField , strings);


        //Set Existing values for fields
        editTitleField.setText( parent.request.getGenericRequest().getTitle());
        editDescriptionField.setText( parent.request.getGenericRequest().getDescription());
        editDateNeededField.setValue( parent.request.getGenericRequest().getDateNeeded().toLocalDateTime().toLocalDate());
        editTimeNeededField.setValue( parent.request.getGenericRequest().getDateNeeded().toLocalDateTime().toLocalTime());
        makeListView( parent.request.getGenericRequest().getAssignees(), editAssigneesListView);
        //load in name as opposed to Node Ids
        ArrayList<String> locationNames = new ArrayList<>();
        for(String s: parent.request.getGenericRequest().getLocations()){
            locationNames.add(App.mapService.getNodeFromID(s).getLongName());
        }

        makeListView( locationNames, editLocationsListView);
        specificTextFields = generateSpecificFields();

        editAssigneesListView.setOnMouseClicked(event -> editAssigneesField.setText(editAssigneesListView.getItems().get(editAssigneesListView.getSelectionModel().getSelectedIndex())));
        editLocationsListView.setOnMouseClicked(event -> editLocationsField.setText(editLocationsListView.getItems().get(editLocationsListView.getSelectionModel().getSelectedIndex())));

        /* adding items to the list view */
        /*making list view horizontal*/
//        editAssigneesListView.setOrientation(Orientation.HORIZONTAL);
//        /* creating horizontal box to add item objects */
//        HBox hbox = new HBox(editAssigneesListView);




    }


    @FXML
    public void handleMakeSecurityButton()throws IOException {
        App.newNodeType = "Security";

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/AddRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    @FXML
    public void handleMakeLaundryButton()throws IOException{
        App.newNodeType = "Laundry";

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/AddRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    @FXML
    public void handleMakeMaintenanceButton()throws IOException {
        App.newNodeType = "Maintenance";

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/AddRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }



    @FXML
    public void ButtonPageForNRCancelJFXButton(ActionEvent actionEvent) throws IOException{
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/ViewRequestList.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    public void handleLanguageButton() throws Exception{
        App.newNodeType = "Language";

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/AddRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    public void handleSanitationButton() throws Exception{
        App.newNodeType = "Sanitation";

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/AddRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    public void handleGiftButton() throws Exception{
        App.newNodeType = "Gift";

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/AddRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    public void handleFloralButton() throws Exception{
        App.newNodeType = "Floral";

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/AddRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    public void handleReligiousButton() throws Exception{
        App.newNodeType = "Religious";

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/AddRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    public void handleComputerButton() throws Exception{
        App.newNodeType = "Computer";

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/AddRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    public void handleAudioVisualButton() throws Exception{
        App.newNodeType = "AudioVisual";

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/AddRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    public void handleMedicineButton() throws Exception{
        App.newNodeType = "Medical";

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/AddRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    public void handleFoodButton() throws Exception{
    }
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



    public void makeListView(ArrayList<String> list, JFXListView<String> res){
        ObservableList<String> something = FXCollections.observableList(list);
        res.setItems(something);
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

    public ArrayList<String> requestSpecificItems() {
        ArrayList<String> specifics = new ArrayList<>();
        for(JFXTextField j : specificTextFields) {
            specifics.add(j.getText());
        }
        return specifics;
    }
}

