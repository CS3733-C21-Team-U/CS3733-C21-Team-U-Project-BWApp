package edu.wpi.u.controllers.mobile;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.algorithms.getEdgesTest;
import edu.wpi.u.exceptions.PathNotFoundException;
import edu.wpi.u.models.MapService;
import edu.wpi.u.models.TextualDirections;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PopupControl;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;


public class MobileFloatingPathfindingPaneController {
    public VBox textDirectionContainer;
   @FXML public JFXTextField endNodeField;
   @FXML public JFXTextField startNodeField;
    public Rectangle startFieldFlair;
    public Rectangle endFieldFlair;
    public AnchorPane mainAnchor;
    public ScrollPane ScrollPanel;
    public AnchorPane secondAnchor;
    @FXML
    Label endNode;
    @FXML
    Label startNode;
    @FXML



    SimpleStringProperty targetNode = new SimpleStringProperty("START");//flag for
    String startNodeID = "", endNodeID = "";
    ArrayList<Node> path = new ArrayList<>();
    ArrayList<String> textualDirectionsStrings = new ArrayList<>();
    String textualDirectionsMegaString = "";
    HashMap<String, String> namesAndIDs;

    public String parkingSpace;
    public String destination;


    public void handleTestAddTextField() {


        textDirectionContainer.getChildren().clear();
        LinkedList<Edge> edgePath = getEdgesTest.EdgesFollowed(App.mapInteractionModel.path);

//        if(!edgePath.isEmpty()){
//            double AnchorSize = Math.min(edgePath.size() * 80, 830);
//            mainAnchor.setPrefHeight(AnchorSize);
//
//
//            Node bNode = null;
//            Node eNode = path.get(0);
//            Node lastTurnNode = null;
//
//            for(Edge e : edgePath) {
//                String iconID;
//                String angleDescription;
//                double angleDifferance;
//                int dist = (int) Math.round(e.getWeight()/3); //3 being pixels for foot, see TextualDirections.java
//                Node sNode = eNode;
//                if (sNode == e.getStartNode())  eNode = e.getEndNode();
//                else eNode = e.getStartNode(); //makes sure start and end nodes are actually what we want
//
//                //Finds angle for path
//                if(bNode == null) { //1st path exception
//                    angleDescription = "";
//                    iconID = "M5,9l1.41,1.41L11,5.83V22H13V5.83l4.59,4.59L19,9l-7-7L5,9z";
//                    angleDifferance = 0;
//                } else {
//                    double angle = TextualDirections.getAngle(sNode, eNode);
//                    double previousAngle = TextualDirections.getAngle(bNode, sNode);
//                    angleDifferance = angle - previousAngle;
//
//                    if (angleDifferance < 0) {
//                        angleDifferance = 180 + (180 - Math.abs(angleDifferance));
//                    }
//                    angleDescription = TextualDirections.textualAngleDescription(angleDifferance);
//                    iconID = TextualDirections.findTextualIconID(angleDifferance);
//                }
//
//                String moveIntoText = "";
//                String moveOutOfText = "";
//
//                if(sNode.getNodeType().equals("ELEV") || eNode.getNodeType().equals("ELEV"))  {
//                    moveIntoText = "Step into elevator";
//                    moveOutOfText = "Step out of elevator at floor ";
//                    iconID = "M19,5v14H5V5H19 M19,3H5C3.9,3,3,3.9,3,5v14c0,1.1,0.9,2,2,2h14c1.1,0,2-0.9,2-2V5C21,3.9,20.1,3,19,3L19,3z M10,18v-4h1 " +
//                            "v-2.5c0-1.1-0.9-2-2-2H8c-1.1,0-2,0.9-2,2V14h1v4H10z M8.5,8.5c0.69,0,1.25-0.56,1.25-1.25S9.19,6,8.5,6S7.25,6.56,7.25,7.25 S7.81,8.5,8.5,8.5z M18,11l-2.5-4L13,11H18z M13,13l2.5,4l2.5-4H13z";
//                }
//                else if(sNode.getNodeType().equals("STAI") || eNode.getNodeType().equals("STAI"))  {
//                    moveIntoText = "Step into staircase";
//                    moveOutOfText = "Step out of staircase at floor ";
//                    iconID = "M19,5v14H5V5H19 M19,3H5C3.9,3,3,3.9,3,5v14c0,1.1,0.9,2,2,2h14c1.1,0,2-0.9,2-2V5C21,3.9,20.1,3,19,3L19,3z M18,6h-4.42 v3.33H11v3.33H8.42V16H6v2h4.42v-3.33H13v-3.33h2.58V8H18V6z";
//                }
//
//                HBox stepHBoxContainer;
//
//                if(eNode.getNodeType().equals("STAI") || eNode.getNodeType().equals("ELEV")) {
//                    if(sNode.getNodeType().equals("STAI") || sNode.getNodeType().equals("ELEV")) { } else {
//                        stepHBoxContainer = createDirectionBox(moveIntoText, iconID);
//                        textDirectionContainer.getChildren().add(stepHBoxContainer);
//                    }}
//
//                else if(sNode.getNodeType().equals("STAI") || sNode.getNodeType().equals("ELEV")) {
//                    if(eNode.getNodeType().equals("STAI") || eNode.getNodeType().equals("ELEV")) { } else {
//                        stepHBoxContainer = createDirectionBox(moveOutOfText + eNode.getFloor(), iconID);
//                        textDirectionContainer.getChildren().add(stepHBoxContainer);
//                        lastTurnNode = eNode;
//                    }}
//
//                else if(eNode.getLongName().equals("Admitting")) {
//                    if(sNode.getLongName().equals("Admitting")) {}
//                    else { stepHBoxContainer = createDirectionBox("Go Through Admitting", "M19 3h-4.18C14.4 1.84 13.3 1 12 1c-1.3 0-2.4.84-2.82 2H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm-7 0c.55 0 1" +
//                            " .45 1 1s-.45 1-1 1-1-.45-1-1 .45-1 1-1zm0 4c1.66 0 3 1.34 3 3s-1.34 3-3 3-3-1.34-3-3 1.34-3 3-3zm6 12H6v-1.4c0-2 4-3.1 6-3.1s6 1.1 6 3.1V19z");
//                        textDirectionContainer.getChildren().add(stepHBoxContainer);
//                    }}
//
//                else if(eNode.getLongName().equals("Atrium Main Lobby")) {
//                    if(sNode.getLongName().equals("Atrium Main Lobby")) {}
//                    else { stepHBoxContainer = createDirectionBox("Go Through Atrium Main Lobby", "M19 3h-4.18C14.4 1.84 13.3 1 12 1c-1.3 0-2.4.84-2.82 2H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm-7 0c.55 0 1" +
//                            " .45 1 1s-.45 1-1 1-1-.45-1-1 .45-1 1-1zm0 4c1.66 0 3 1.34 3 3s-1.34 3-3 3-3-1.34-3-3 1.34-3 3-3zm6 12H6v-1.4c0-2 4-3.1 6-3.1s6 1.1 6 3.1V19z");
//                        textDirectionContainer.getChildren().add(stepHBoxContainer);
//                    }}
//
//                //else if(sNode.getLongName().equals("Admitting") || bNode.getLongName().equals("Admitting")) { }
//
//                else if(angleDifferance > 22.5 && angleDifferance < 337.5) { //if turn is found on current node
//                    if(lastTurnNode != null && !(sNode.getLongName().equals("Admitting")) && !(bNode.getLongName().equals("Admitting"))
//                            && !(sNode.getLongName().equals("Atrium Main Lobby")) && !(bNode.getLongName().equals("Atrium Main Lobby"))) {
//                        stepHBoxContainer = createDirectionBox("Continue straight for " + distAggregate(lastTurnNode, sNode) + " feet", "M5,9l1.41,1.41L11,5.83V22H13V5.83l4.59,4.59L19,9l-7-7L5,9z");
//                        textDirectionContainer.getChildren().add(stepHBoxContainer);
//                    }
//                    String walking = getContextLocation(sNode);
////                    if(sNode.getNodeType().equals("HALL")) walking = "hallway";
////                    else if(sNode.getNodeType().equals("WALK")) walking = "walkway";
////                    else walking = sNode.getLongName();
//
//                    if(sNode.getLongName().equals("Admitting")) {}
//                    else if(walking.equals("")) {
//                        stepHBoxContainer = createDirectionBox(angleDescription, iconID);
//                        textDirectionContainer.getChildren().add(stepHBoxContainer);
//                        lastTurnNode = sNode;
//                    }
//                    else {stepHBoxContainer = createDirectionBox(angleDescription + " near " + walking, iconID);
//                        textDirectionContainer.getChildren().add(stepHBoxContainer);
//                        lastTurnNode = sNode;
//                    }}
//
//
//                else if(bNode == null) {
//                    stepHBoxContainer = createDirectionBox("Begin by travelling " + dist + " feet from " + sNode.getLongName(), "M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zM7 9c0-2.76 2.24-5 5-5s5 2.24 5 5c0 2.88-2.88 7.19-5 9.88C9.92 16.21 7 11.85 7 9z"
//                            + "M 14.5,9 A 2.5,2.5 0 0 1 12,11.5 2.5,2.5 0 0 1 9.5,9 2.5,2.5 0 0 1 12,6.5 2.5,2.5 0 0 1 14.5,9 Z");
//                    textDirectionContainer.getChildren().add(stepHBoxContainer);
//                }
//
//                else if((sNode.getNodeType().equals("HALL") && eNode.getNodeType().equals("HALL"))) {/*Hallway*/ }
//                else if((sNode.getNodeType().equals("WALK") && eNode.getNodeType().equals("WALK"))) {/*Walkway*/ }
//
//                else {
//                    if(!(sNode.getLongName().equals("Admitting")) && !(eNode.getLongName().equals("Admitting")) && !(bNode.getLongName().equals("Admitting"))) {
//                        stepHBoxContainer = createDirectionBox("Continue straight for " + distAggregate(lastTurnNode, eNode) + " feet", "M5,9l1.41,1.41L11,5.83V22H13V5.83l4.59,4.59L19,9l-7-7L5,9z");
//                        textDirectionContainer.getChildren().add(stepHBoxContainer);
//                        //lastTurnNode = eNode;
//                    }}
//
//                bNode = sNode;
//            }
//            HBox stepHBoxContainer = createDirectionBox("Arrived at " + path.get(path.size()-1).getLongName(), "M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zM7 9c0-2.76 2.24-5 5-5s5 2.24 5 5c0 2.88-2.88 7.19-5 9.88C9.92 16.21 7 11.85 7 9z"
//                    + "M 14.5,9 A 2.5,2.5 0 0 1 12,11.5 2.5,2.5 0 0 1 9.5,9 2.5,2.5 0 0 1 12,6.5 2.5,2.5 0 0 1 14.5,9 Z");
//            textDirectionContainer.getChildren().add(stepHBoxContainer);
//        }
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

    /** path.get(path.size()-1).getLongName()
     * sets flag for what field is being filled to END
     */

    public HBox createDirectionBox(String text, String iconID) {
        Label turnText = new Label(text);
        turnText.getStyleClass().add("subtitle");
        turnText.setWrapText(true);
        //Label distanceText = new Label(text);// TODO: add distance to walk after
        //distanceText.getStyleClass().add("caption");

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

        ArrayList<String> nodeNames = new ArrayList<>();

        AutoCompletionBinding<String> autoFillStart = TextFields.bindAutoCompletion(startNodeField , strings);
        AutoCompletionBinding<String> autoFillEnd = TextFields.bindAutoCompletion(endNodeField , strings);

        App.mapInteractionModel.nodeID.addListener((observable, oldValue, newValue)  ->{
            if(targetNode.getValue().equals("START")){
                startNodeField.setText(App.mapService.getNodeFromID(newValue).getLongName());
                startNodeID = newValue;
            } else if(targetNode.get().equals("END")){
                endNodeID = newValue;
                Node testNode = App.mapService.getNodeFromID(newValue);
                String longName = testNode.getLongName();
                endNodeField.setText(longName);
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
//                App.mapInteractionModel.pathPreview = path;
//                App.mapInteractionModel.pathPreviewFlag.set(String.valueOf(Math.random()));
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


