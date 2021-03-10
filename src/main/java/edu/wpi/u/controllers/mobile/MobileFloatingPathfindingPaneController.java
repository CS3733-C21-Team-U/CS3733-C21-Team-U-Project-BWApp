package edu.wpi.u.controllers.mobile;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.exceptions.PathNotFoundException;
import edu.wpi.u.models.TextualDirections;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class MobileFloatingPathfindingPaneController {
    public VBox textDirectionContainer;
   @FXML public JFXTextField endNodeField;
   @FXML public JFXTextField startNodeField;
    public Rectangle startFieldFlair;
    public Rectangle endFieldFlair;
    @FXML
    Label endNode;
    @FXML
    Label startNode;


    SimpleStringProperty targetNode = new SimpleStringProperty("START");//flag for
    String startNodeID = "", endNodeID = "";
    ArrayList<Node> path = new ArrayList<>();
    ArrayList<String> textualDirectionsStrings = new ArrayList<>();
    String textualDirectionsMegaString = "";
    HashMap<String, String> namesAndIDs;

    public String parkingSpace;
    public String destination;


    public void handleTestAddTextField(ActionEvent actionEvent) {

        Label turnText = new Label("Turn left at the corner of the MRI room");
        turnText.getStyleClass().add("subtitle");
        Label distanceText = new Label("Continue straight for 15 meters");
        distanceText.getStyleClass().add("caption");
        SVGPath turnIcon = new SVGPath();
        turnIcon.setStyle("-fx-padding: 20px");
        turnIcon.setContent("M9,5v2h6.59L4,18.59L5.41,20L17,8.41V15h2V5H9z");
        VBox textVBox = new VBox();
        textVBox.getChildren().add(turnText);
        textVBox.getChildren().add(distanceText);
        HBox stepHBoxContainer = new HBox();
        stepHBoxContainer.setAlignment(Pos.CENTER_LEFT);
        stepHBoxContainer.getChildren().add(turnIcon);
        stepHBoxContainer.getChildren().add(textVBox);
        stepHBoxContainer.setStyle("-fx-padding: 10px 40px");
        stepHBoxContainer.setSpacing(40);
        textDirectionContainer.getChildren().add(stepHBoxContainer);
    }

    /**
     * sets flag for what field is being filled to END
     */


    public void endNodeButtonHandler(){
        targetNode.set("END");
    }

    /**
     * sets flag for what field is being filled to START
     */
    @FXML
    public void startNodeButtonHandler(){
        targetNode.set("START");
    }

    public void initialize(){

        targetNode.addListener((observable, oldVal, newVal) ->{
            if(newVal.equals("START")){
                startFieldFlair.setVisible(true);
                endFieldFlair.setVisible(false);
            }else{
                startFieldFlair.setVisible(false);
                endFieldFlair.setVisible(true);
            }
        });

        destination = endNodeField.getText();
        parkingSpace = startNodeField.getText();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                startNodeField.requestFocus();
            }

        });

        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Input Required");
        endNodeField.getValidators().add(validator);
        endNodeField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) { //Focus loss
                endNodeField.validate();
            }else{ //Focus gain
                System.out.println("Trag: END");
                targetNode.set("END");
            }
        });
        endNodeField.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Current Selected Tab " + App.tabPaneRoot.getSelectionModel().getSelectedIndex());
            if(App.tabPaneRoot.getSelectionModel().getSelectedIndex() == 0){
                System.out.println("Trag: END - EndNodeField input "+newValue+" which has nodeID "+namesAndIDs.get(newValue));
                endNodeField.requestFocus();
                targetNode.set("END");
                if(namesAndIDs.get(newValue) != null){
                    App.mapInteractionModel.setNodeID((namesAndIDs.get(newValue)));
                }else{
                    System.out.println("No valid node ID for this end input");
                }
            }
        });

        startNodeField.getValidators().add(validator);
        startNodeField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) { //Foucs Loss
                startNodeField.validate();
            }else{ //Focus gain
                System.out.println("Trag: START");
                targetNode.set("START");
            }
        });
        startNodeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(App.tabPaneRoot.getSelectionModel().getSelectedIndex() == 0) {
                System.out.println("Trag: START - StartNodeField input " + newValue + " which has nodeID " + namesAndIDs.get(newValue));
                targetNode.set("START");
                startNodeField.requestFocus();
                if (namesAndIDs.get(newValue) != null) {
                    App.mapInteractionModel.setNodeID((namesAndIDs.get(newValue)));
                    if (endNodeField.getText().equals("")) {
                        endNodeField.requestFocus();
                    }
                } else {
                    System.out.println("No valid node ID for this start input");
                }
            }
        });

        namesAndIDs = App.mapService.getLongNames();
        Set<String> strings = namesAndIDs.keySet();

        AutoCompletionBinding<String> autoFillStart = TextFields.bindAutoCompletion(startNodeField , strings);
        AutoCompletionBinding<String> autoFillEnd = TextFields.bindAutoCompletion(endNodeField , FXCollections.observableArrayList(strings));


//        String test = namesAndIDs.get(startNodeField.getText());




        App.mapInteractionModel.nodeID.addListener((observable, oldValue, newValue)  ->{
            if(targetNode.getValue().equals("START")){
                startNodeField.setText(App.mapService.getNodeFromID(newValue).getLongName());
                startNodeID = newValue;
            } else if(targetNode.get().equals("END")){
                endNodeField.setText(App.mapService.getNodeFromID(newValue).getLongName());
                endNodeID = newValue;
//                endNodeField.requestFocus();
            }

            if(!startNodeID.equals("") && !endNodeID.equals("")){
                try {
                    path = App.mapService.runPathfinding(startNodeID,endNodeID);
                } catch (PathNotFoundException e) {
                    e.printStackTrace();
                }
                App.mapInteractionModel.path = path;
                App.mapInteractionModel.pathFlag.set(String.valueOf(Math.random()));

                textualDirectionsStrings = TextualDirections.getTextualDirections(path);
                textualDirectionsMegaString = "";
                for(String curString : textualDirectionsStrings){
                    textualDirectionsMegaString = textualDirectionsMegaString + curString + "\n";
                }
//                textualDirections.setText(textualDirectionsMegaString);
            }
        });

        App.mapInteractionModel.nodeIDForHover.addListener((observable, oldValue, newValue)  ->{
            if(!startNodeID.equals("") && !App.mapInteractionModel.nodeIDForHover.getValue().equals("")){
                try {
                    path = App.mapService.runPathfinding(startNodeID,App.mapInteractionModel.nodeIDForHover.getValue());
                } catch (PathNotFoundException e) {
                    e.printStackTrace();
                }
                App.mapInteractionModel.pathPreview = path;
                App.mapInteractionModel.pathPreviewFlag.set(String.valueOf(Math.random()));
            }
        });

    }



    public void handleStartEndSwap(ActionEvent actionEvent) {
        String tempStorage = startNodeField.getText();
        String originalTarget = targetNode.get();
        targetNode.set("START");
        startNodeField.setText(endNodeField.getText());
        targetNode.set("END");
        endNodeField.setText(tempStorage);
        targetNode.set(originalTarget);
    }

    public void handleInputMethodChange(InputMethodEvent inputMethodEvent) {
        System.out.println("Help!");
    }

    public String getParkingSpace() {
        parkingSpace = startNodeField.getText();
        return parkingSpace;
    }

    public void setDestination(){
      //  destination = call upon a DB function depending on COVID survey result.
        endNodeField.setText(destination);
    }
}


