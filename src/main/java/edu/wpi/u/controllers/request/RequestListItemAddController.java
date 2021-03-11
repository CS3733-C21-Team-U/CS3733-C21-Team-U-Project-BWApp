package edu.wpi.u.controllers.request;

import com.jfoenix.controls.*;
import edu.wpi.u.App;
import edu.wpi.u.requests.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class RequestListItemAddController {


    public JFXTextField editTitleField;
    public JFXTextArea editDescriptionField;
    public JFXDatePicker editDateNeededField;
    public JFXTimePicker editTimeNeededField;
    public JFXTextField editAssigneesField;



    public VBox extraFieldsVBox;
    private JFXTextField[] specificTextFields;
    HashMap<String, String> longNamestoID;
    Set<String> existingAssignee;


    public JFXTextField makeTitleField;
    public JFXTextArea makeDescriptionField;
    public JFXChipView makeLocationChipView;
    public JFXChipView makeStaffChipView;
    public Label specificTitle;
    public Label errorMsg;

    @FXML
    public JFXListView<String> editAssigneesListView;// = new JFXListView<String>();
    public JFXTextField editLocationsField;

    @FXML
    public JFXListView<String> editLocationsListView;// = new JFXListView<String>();
    private SpecificRequest currSpecificRequest;




    @FXML
    public void initialize() throws IOException {
        String type = App.newNodeType;
        currSpecificRequest = new RequestFactory().makeRequest(type);
        specificTextFields = generateSpecificFields();
    }

    /**
     * Creates fields based on request type
     * @return JFXTextField[], one for each needed field, labeled correctly
     */
    public JFXTextField[] generateSpecificFields() {

        specificTitle.setText(currSpecificRequest.getType() + " Fields");
        JFXTextField[] ans = new JFXTextField[currSpecificRequest.getSpecificFields().length];
        for(int i = 0; i < currSpecificRequest.getSpecificFields().length; i++) {

            JFXTextField j = new JFXTextField();
            j.setPromptText(currSpecificRequest.getSpecificFields()[i]);
            j.setLabelFloat(true);
            j.setStyle("-fx-pref-width: 400px");
            j.setStyle("-fx-pref-height: 50px");
            j.setStyle("-fx-font-size: 16px");

            ans[i] = j;

            extraFieldsVBox.getChildren().add(j);
        }
        return ans;
    }


    /**
     * This returns the needed array list from JFXTextField[]
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
     * Pull from fields, and add Request
     */
    public void handleSaveButton(){
        try {
            ArrayList<String> locationsToAdd = new ArrayList<String>();
            for(String s :editLocationsListView.getItems()){
                locationsToAdd.add(longNamestoID.get(s));
            }
            ArrayList<String> assigneesToAdd = new ArrayList<>(editAssigneesListView.getItems());
            ArrayList<String> specifics = requestSpecificItems();

            //ADD REQUEST LOGIC STARTS HERE
            Random rand = new Random();
            int requestID = rand.nextInt();
            String ID = Integer.toString(requestID);//make a random id

            //make components of specifc request,  then set them
            Comment primaryComment = new Comment(makeTitleField.getText(), makeDescriptionField.getText(),
                    App.userService.getActiveUser().getName(), CommentType.PRIMARY,
                    Timestamp.valueOf(LocalDateTime.of(editDateNeededField.getValue(), editTimeNeededField.getValue())));

            Request newRequest = new Request(ID, new Timestamp(System.currentTimeMillis()), locationsToAdd, assigneesToAdd, primaryComment);
            App.requestService.addRequest(currSpecificRequest.setRequest(newRequest).setSpecificData(specifics));
            //


            //TODO: Hide FXML HERE
            //Comment in case animation/listener is needed
//            this.parent.needUpdate.set(!this.parent.needUpdate.get());
//            this.parent.switchFromEditToExpanded();

//            AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
//            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/ViewRequestList.fxml"));
//            anchor.getChildren().clear();
//            anchor.getChildren().add(root);
        } catch (Exception e) {
            errorMsg.setVisible(true);
        }

    }

    /**
     * Fair to assume the same behaviour as edit
     */
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
                //TODO: HIDE THIS FXML
                //parent.switchToCollapsed();
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
}
