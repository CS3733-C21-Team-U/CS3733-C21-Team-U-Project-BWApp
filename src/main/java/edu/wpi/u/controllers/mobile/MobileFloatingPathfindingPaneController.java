package edu.wpi.u.controllers.mobile;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.exceptions.PathNotFoundException;
import edu.wpi.u.models.TextualDirections;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;


public class MobileFloatingPathfindingPaneController {
   @FXML public JFXTextField endNodeField;
   @FXML public JFXTextField startNodeField;
    public Rectangle startFieldFlair;
    public Rectangle endFieldFlair;
    public AnchorPane mainAnchor;



    SimpleStringProperty targetNode = new SimpleStringProperty("START");//flag for
    String startNodeID = "", endNodeID = "";
    ArrayList<Node> path = new ArrayList<>();
    ArrayList<String> textualDirectionsStrings = new ArrayList<>();
    String textualDirectionsMegaString = "";
    HashMap<String, String> namesAndIDs;

    public String parkingSpace;


    public void handleTestAddTextField() {
    }

    /**
     * returns the long name of the closest non hallway or walkway node
     * @param givenNode the node to check for
     * @return long name
     */
    private String getContextLocation(Node givenNode){
        LinkedList<Node> adjNodes = givenNode.whatAreAdjNodes();
        LinkedList<Node> adjNodesTrimmed = new LinkedList<>();

        LinkedList<Node> SecondLevel =  new LinkedList<>();
        for(Node curNode: adjNodes){
            if(!curNode.getNodeType().equals("WALK") && !curNode.getNodeType().equals("HALL")){
                adjNodesTrimmed.add(curNode);
            }
        }
        if(adjNodesTrimmed.isEmpty()) {
            for(Node n : adjNodes) {
                SecondLevel.addAll(n.whatAreAdjNodes());
                SecondLevel.remove(givenNode);
            }
            for(Node curNode: SecondLevel){
                if(!curNode.getNodeType().equals("WALK") && !curNode.getNodeType().equals("HALL")){
                    adjNodesTrimmed.add(curNode);
                }
            }
        }
        int curMinDistance = Integer.MAX_VALUE;
        String closestNode = "";
        for(Node curNode :adjNodesTrimmed){
            if(distAggregate(curNode,givenNode) < curMinDistance){
                curMinDistance = distAggregate(curNode,givenNode);
                closestNode = curNode.getLongName();
            }
        }
        return closestNode;
    }

    /**
     * path.get(path.size()-1).getLongName()
     * sets flag for what field is being filled to END
     */
    public HBox createDirectionBox(String text, String iconID) {
        Label turnText = new Label(text);
        turnText.getStyleClass().add("subtitle");
        turnText.setWrapText(true);

        SVGPath turnIcon = new SVGPath();
        turnIcon.setStyle("-fx-padding: 20px");
        turnIcon.setContent(iconID);
        VBox textVBox = new VBox();
        textVBox.getChildren().add(turnText);
        //textVBox.getChildren().add(distanceText);
        HBox stepHBoxContainer = new HBox();
        stepHBoxContainer.setAlignment(Pos.CENTER_LEFT);
        stepHBoxContainer.getChildren().add(turnIcon);
        stepHBoxContainer.getChildren().add(textVBox);
        stepHBoxContainer.setStyle("-fx-padding: 10px 40px");
        stepHBoxContainer.setSpacing(40);
        return stepHBoxContainer;
    }

    public int distAggregate(Node refNode, Node endNode) {
        double[][] nodeLocation = {refNode.getCords(), endNode.getCords()};
        double dx = Math.abs(nodeLocation[0][0] - nodeLocation[1][0]);
        double dy = Math.abs(nodeLocation[0][1] - nodeLocation[1][1]);
        // calculate the distance then
        // return the distance (weight)
        return (int) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2))/3;
    }

    public void initialize(){

        App.mobileUpdateDestinationField.addListener((o,oldVal,newVal) ->{
            endNodeField.setText(parkingSpace);
            startNodeField.setText("");
        });

        App.mobileUpdateParkingSpot.addListener((o,oldVal,newVal) ->{
            parkingSpace = startNodeField.getText();
        });

        targetNode.addListener((observable, oldVal, newVal) ->{
            if(newVal.equals("START")){
                startFieldFlair.setVisible(true);
                endFieldFlair.setVisible(false);
            }else{
                startFieldFlair.setVisible(false);
                endFieldFlair.setVisible(true);
            }
        });

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
            System.out.println("Trag: END - EndNodeField input "+newValue+" which has nodeID "+namesAndIDs.get(newValue));
            endNodeField.requestFocus();
            targetNode.set("END");
            if(namesAndIDs.get(newValue) != null){
                App.mapInteractionModel.setNodeID((namesAndIDs.get(newValue)));
            }else{
                System.out.println("No valid node ID for this end input");
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
            System.out.println("Trag: START - StartNodeField input "+newValue+" which has nodeID "+namesAndIDs.get(newValue));
            targetNode.set("START");
            startNodeField.requestFocus();
            if(namesAndIDs.get(newValue) != null){
                App.mapInteractionModel.setNodeID((namesAndIDs.get(newValue)));
                if(endNodeField.getText().equals("")){
                    endNodeField.requestFocus();
                }
            }else{
                System.out.println("No valid node ID for this start input");
            }
        });

        namesAndIDs = App.mapService.getLongNames();
        Set<String> strings = namesAndIDs.keySet();

        App.mapInteractionModel.nodeID.addListener((observable, oldValue, newValue)  ->{
            if(targetNode.getValue().equals("START")){
                startNodeField.setText(App.mapService.getNodeFromID(newValue).getLongName());
                startNodeID = newValue;
            } else if(targetNode.get().equals("END")){
                endNodeID = newValue;
                Node testNode = App.mapService.getNodeFromID(newValue);
                String longName = testNode.getLongName();
                endNodeField.setText(longName);
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
            }
        });

        App.mapInteractionModel.nodeIDForHover.addListener((observable, oldValue, newValue)  ->{
            if(!startNodeID.equals("") && !App.mapInteractionModel.nodeIDForHover.getValue().equals("")){
                try {
                    path = App.mapService.runPathfinding(startNodeID,App.mapInteractionModel.nodeIDForHover.getValue());
                } catch (PathNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        App.mapInteractionModel.pathFlag.addListener(observable -> {
            handleTestAddTextField();
        });

        startNodeField.requestFocus();

        Platform.runLater(()->{
            startNodeID = "UWALK0200G";
            startNodeField.setText(App.mapService.getNodeFromID("UWALK0200G").getLongName());
            if(App.mapInteractionModel.highRisk){
                endNodeID = "UEXIT0020G";
                endNodeField.setText(App.mapService.getNodeFromID("UEXIT0020G").getLongName());
                try {
                    path = App.mapService.runPathfinding(startNodeID,endNodeID);
                } catch (PathNotFoundException e) {
                    e.printStackTrace();
                }
                App.mapInteractionModel.path = path;
                App.mapInteractionModel.pathFlag.set(String.valueOf(Math.random()));
            }
            else{
                endNodeID = "UEXIT0010G";
                endNodeField.setText(App.mapService.getNodeFromID("UEXIT0010G").getLongName());
                try {
                    path = App.mapService.runPathfinding(startNodeID,endNodeID);
                } catch (PathNotFoundException e) {
                    e.printStackTrace();
                }
                App.mapInteractionModel.path = path;
                App.mapInteractionModel.pathFlag.set(String.valueOf(Math.random()));
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
}


