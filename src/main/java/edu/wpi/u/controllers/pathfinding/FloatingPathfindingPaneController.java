package edu.wpi.u.controllers.pathfinding;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.util.*;

public class FloatingPathfindingPaneController {
    public VBox textDirectionContainer;
    public JFXTextField endNodeField;
    public JFXTextField startNodeField;
    public AnchorPane mainAnchor;
    public ScrollPane ScrollPanel;
    public AnchorPane secondAnchor;
    public Rectangle startFieldFlair;
    public Rectangle endFieldFlair;
    public HBox pathContent;
    public VBox btnVBox;
    public AnchorPane treeViewListNodes;


    public JFXButton btnOne;
    public JFXButton btnTwo;
    public JFXButton btnThree;
    public JFXButton btnFour;
    public JFXButton btnFive;
    public JFXButton btnG;
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



    public void handleTestAddTextField(String floor) {

        btnOne.setDisable(true);
        btnOne.setPrefHeight(0);
        btnTwo.setDisable(true);
        btnTwo.setPrefHeight(0);
        btnThree.setDisable(true);
        btnThree.setPrefHeight(0);
        btnFive.setDisable(true);
        btnFive.setPrefHeight(0);
        btnFour.setDisable(true);
        btnFour.setPrefHeight(0);
        btnG.setDisable(true);
        btnG.setPrefHeight(0);

        textDirectionContainer.getChildren().clear();
        LinkedList<Edge> edgePath = getEdgesTest.EdgesFollowed(App.mapInteractionModel.path);

        if(!edgePath.isEmpty()) {

            Node bNode = null;
            Node eNode = path.get(0);
            Node lastTurnNode = null;
            String currentFloor = "";

             for(int i = 0; i < edgePath.size(); i++) {

                 Edge e = edgePath.get(i);

                 HBox stepHBoxContainer;
                 String iconID;
                 String angleDescription;
                 double angleDifferance;
                 int dist = (int) Math.round(e.getWeight() / 3); //3 being pixels for foot, see TextualDirections.java
                 Node sNode = eNode;
                 if (sNode == e.getStartNode()) eNode = e.getEndNode();
                 else eNode = e.getStartNode(); //makes sure start and end nodes are actually what we want


                 //Finds angle for path
                 if (bNode == null) { //1st path exception
                     angleDifferance = 0;
                     angleDescription = "";
                     iconID = "M5,9l1.41,1.41L11,5.83V22H13V5.83l4.59,4.59L19,9l-7-7L5,9z";
                 } else {
                     angleDifferance = findAngle(bNode, sNode, eNode);
                     angleDescription = TextualDirections.textualAngleDescription(angleDifferance);
                     iconID = TextualDirections.findTextualIconID(angleDifferance);
                 }

                String moveIntoText = "";
                String moveOutOfText = "";

                if(!sNode.getFloor().equals(currentFloor) && !eNode.getFloor().equals(currentFloor) &&
                        sNode.getFloor().equals(eNode.getFloor()) &&
                        (!sNode.getNodeType().equals("ELEV")) && (!eNode.getNodeType().equals("ELEV")) &&
                        (!sNode.getNodeType().equals("STAI")) && (!eNode.getNodeType().equals("STAI")))
                {
                    enableButton(sNode.getFloor());
                }



                if(sNode.getNodeType().equals("ELEV") || eNode.getNodeType().equals("ELEV"))  {
                    moveIntoText = "Step into elevator";
                    moveOutOfText = "Step out of elevator at floor ";
                    iconID = "M19,5v14H5V5H19 M19,3H5C3.9,3,3,3.9,3,5v14c0,1.1,0.9,2,2,2h14c1.1,0,2-0.9,2-2V5C21,3.9,20.1,3,19,3L19,3z M10,18v-4h1 " +
                            "v-2.5c0-1.1-0.9-2-2-2H8c-1.1,0-2,0.9-2,2V14h1v4H10z M8.5,8.5c0.69,0,1.25-0.56,1.25-1.25S9.19,6,8.5,6S7.25,6.56,7.25,7.25 S7.81,8.5,8.5,8.5z M18,11l-2.5-4L13,11H18z M13,13l2.5,4l2.5-4H13z";
                }
                else if(sNode.getNodeType().equals("STAI") || eNode.getNodeType().equals("STAI"))  {
                    moveIntoText = "Step into staircase";
                    moveOutOfText = "Step out of staircase at floor ";
                    iconID = "M19,5v14H5V5H19 M19,3H5C3.9,3,3,3.9,3,5v14c0,1.1,0.9,2,2,2h14c1.1,0,2-0.9,2-2V5C21,3.9,20.1,3,19,3L19,3z M18,6h-4.42 v3.33H11v3.33H8.42V16H6v2h4.42v-3.33H13v-3.33h2.58V8H18V6z";
                }
                 if(!sNode.getFloor().equals(floor)) continue;

                String sNodeType = sNode.getNodeType();
                String eNodeType = eNode.getNodeType();

                //function(sNodeType, eNodeType) --> stepBoxContainer

                if(eNodeType.equals("STAI") || eNodeType.equals("ELEV")) {
                    if(sNode.getNodeType().equals("STAI") || sNode.getNodeType().equals("ELEV")) { } else {
                    stepHBoxContainer = createDirectionBox(moveIntoText, iconID);
                    textDirectionContainer.getChildren().add(stepHBoxContainer);
                    HBox endContainer = createDirectionBox(" Switch to next floor", "M13.5 5.5c1.1 0 2-.9 2-2s-.9-2-2-2-2 .9-2 2 .9 2 2 2zM9.8 8.9L7 23h2.1l1.8-8 2.1 2v6h2v-7.5l-2.1-2 " +
                                ".6-3C14.8 12 16.8 13 19 13v-2c-1.9 0-3.5-1-4.3-2.4l-1-1.6c-.4-.6-1-1-1.7-1-.3 0-.5.1-.8.1L6 8.3V13h2V9.6l1.8-.7");
                    textDirectionContainer.getChildren().add(endContainer);
                }}

                else if(sNodeType.equals("ELEV")) {
                    if(eNode.getNodeType().equals("STAI") || eNode.getNodeType().equals("ELEV")) { } else {
                    stepHBoxContainer = createDirectionBox(moveOutOfText + eNode.getFloor(), iconID);
                    textDirectionContainer.getChildren().add(stepHBoxContainer);
                    lastTurnNode = eNode;
                }}

                else if(eNode.getLongName().equals("Admitting")) {
                    if(sNode.getLongName().equals("Admitting")) {}
                    else { stepHBoxContainer = createDirectionBox("Go Through Admitting", "M19 3h-4.18C14.4 1.84 13.3 1 12 1c-1.3 0-2.4.84-2.82 2H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm-7 0c.55 0 1" +
                            " .45 1 1s-.45 1-1 1-1-.45-1-1 .45-1 1-1zm0 4c1.66 0 3 1.34 3 3s-1.34 3-3 3-3-1.34-3-3 1.34-3 3-3zm6 12H6v-1.4c0-2 4-3.1 6-3.1s6 1.1 6 3.1V19z");
                    textDirectionContainer.getChildren().add(stepHBoxContainer);
                        HBox endContainer = createDirectionBox(" Switch to next floor", "M13.5 5.5c1.1 0 2-.9 2-2s-.9-2-2-2-2 .9-2 2 .9 2 2 2zM9.8 8.9L7 23h2.1l1.8-8 2.1 2v6h2v-7.5l-2.1-2 " +
                                ".6-3C14.8 12 16.8 13 19 13v-2c-1.9 0-3.5-1-4.3-2.4l-1-1.6c-.4-.6-1-1-1.7-1-.3 0-.5.1-.8.1L6 8.3V13h2V9.6l1.8-.7");
                        textDirectionContainer.getChildren().add(endContainer);
                    }}

                else if(eNode.getLongName().equals("Atrium Main Lobby")) {
                    if(sNode.getLongName().equals("Atrium Main Lobby")) {}
                    else { stepHBoxContainer = createDirectionBox("Go Through Atrium Main Lobby", "M19 3h-4.18C14.4 1.84 13.3 1 12 1c-1.3 0-2.4.84-2.82 2H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm-7 0c.55 0 1" +
                            " .45 1 1s-.45 1-1 1-1-.45-1-1 .45-1 1-1zm0 4c1.66 0 3 1.34 3 3s-1.34 3-3 3-3-1.34-3-3 1.34-3 3-3zm6 12H6v-1.4c0-2 4-3.1 6-3.1s6 1.1 6 3.1V19z");
                        textDirectionContainer.getChildren().add(stepHBoxContainer);
                        HBox endContainer = createDirectionBox(" Switch to next floor", "M13.5 5.5c1.1 0 2-.9 2-2s-.9-2-2-2-2 .9-2 2 .9 2 2 2zM9.8 8.9L7 23h2.1l1.8-8 2.1 2v6h2v-7.5l-2.1-2 " +
                                ".6-3C14.8 12 16.8 13 19 13v-2c-1.9 0-3.5-1-4.3-2.4l-1-1.6c-.4-.6-1-1-1.7-1-.3 0-.5.1-.8.1L6 8.3V13h2V9.6l1.8-.7");
                        textDirectionContainer.getChildren().add(endContainer);
                    }}

                //else if(sNode.getLongName().equals("Admitting") || bNode.getLongName().equals("Admitting")) { }

                else if(angleDifferance > 22.5 && angleDifferance < 337.5) { //if turn is found on current node
                    if(lastTurnNode != null && !(sNode.getLongName().equals("Admitting")) && !(bNode.getLongName().equals("Admitting"))
                            && !(sNode.getLongName().equals("Atrium Main Lobby")) && !(bNode.getLongName().equals("Atrium Main Lobby"))) {
                        stepHBoxContainer = createDirectionBox("Continue straight for " + distAggregate(lastTurnNode, sNode) + " feet", "M5,9l1.41,1.41L11,5.83V22H13V5.83l4.59,4.59L19,9l-7-7L5,9z");
                        textDirectionContainer.getChildren().add(stepHBoxContainer);
                    }
                    String walking = getContextLocation(sNode);

                    if(sNode.getLongName().equals("Admitting") || sNode.getLongName().equals("Atrium Main Lobby")) {}
                    else if(walking.equals("")) {
                        stepHBoxContainer = createDirectionBox(angleDescription, iconID);
                        textDirectionContainer.getChildren().add(stepHBoxContainer);
                        lastTurnNode = sNode;
                    }
                    else {stepHBoxContainer = createDirectionBox(angleDescription + " near " + walking, iconID);
                    textDirectionContainer.getChildren().add(stepHBoxContainer);
                    lastTurnNode = sNode;
                }}


                else if(bNode == null) {
                    stepHBoxContainer = createDirectionBox("Begin by travelling " + dist + " feet from " + sNode.getLongName(), "M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zM7 9c0-2.76 2.24-5 5-5s5 2.24 5 5c0 2.88-2.88 7.19-5 9.88C9.92 16.21 7 11.85 7 9z"
                            + "M 14.5,9 A 2.5,2.5 0 0 1 12,11.5 2.5,2.5 0 0 1 9.5,9 2.5,2.5 0 0 1 12,6.5 2.5,2.5 0 0 1 14.5,9 Z");
                    textDirectionContainer.getChildren().add(stepHBoxContainer);
                }

                else if((sNodeType.equals("HALL") && eNodeType.equals("HALL"))) {
                    continue;
                }
                else if((sNodeType.equals("WALK") && eNodeType.equals("WALK"))) {
                    continue;
                }


                else {
                    if(!(sNode.getLongName().equals("Admitting")) && !(eNode.getLongName().equals("Admitting")) && !(bNode.getLongName().equals("Admitting")) && distAggregate(lastTurnNode, eNode) != 0) {
                    stepHBoxContainer = createDirectionBox("Continue straight for " + distAggregate(lastTurnNode, eNode) + " feet", "M5,9l1.41,1.41L11,5.83V22H13V5.83l4.59,4.59L19,9l-7-7L5,9z");
                    textDirectionContainer.getChildren().add(stepHBoxContainer);
                    //lastTurnNode = eNode;
                }}

                bNode = sNode;

                 if(i == edgePath.size() - 1) {
                     if (path.get(path.size() - 1).getFloor().equals(sNode.getFloor())) { //check if last node is on current floor
                         HBox endContainer = createDirectionBox("Arrived at " + path.get(path.size() - 1).getLongName(), "M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zM7 9c0-2.76 2.24-5 5-5s5 2.24 5 5c0 2.88-2.88 7.19-5 9.88C9.92 16.21 7 11.85 7 9z"
                                 + "M 14.5,9 A 2.5,2.5 0 0 1 12,11.5 2.5,2.5 0 0 1 9.5,9 2.5,2.5 0 0 1 12,6.5 2.5,2.5 0 0 1 14.5,9 Z");
                         textDirectionContainer.getChildren().add(endContainer);
                     }
                 }
            }

            if(textDirectionContainer.getChildren().isEmpty()) {
                HBox stepHBoxContainer = createDirectionBox("Click on Respective Floor Buttons to Find Path. Begin on floor " + path.get(0).getFloor(), "M13.5 5.5c1.1 0 2-.9 2-2s-.9-2-2-2-2 .9-2 2 .9 2 2 2zM9.8 8.9L7 23h2.1l1.8-8 2.1 2v6h2v-7.5l-2.1-2 " +
                        ".6-3C14.8 12 16.8 13 19 13v-2c-1.9 0-3.5-1-4.3-2.4l-1-1.6c-.4-.6-1-1-1.7-1-.3 0-.5.1-.8.1L6 8.3V13h2V9.6l1.8-.7"); //Guy walking

                textDirectionContainer.getChildren().add(stepHBoxContainer);
            }
        }
        double AnchorSize = Math.min(textDirectionContainer.getChildren().size() * 90, 830);
        mainAnchor.setPrefHeight(Region.USE_COMPUTED_SIZE);
        secondAnchor.setPrefHeight(Region.USE_COMPUTED_SIZE);
        pathContent.setPrefHeight(Region.USE_COMPUTED_SIZE);
        treeViewListNodes.setPrefHeight(0);
    }

    private void enableButton(String button) {
        switch(button) {
            case("G"):
                btnG.setDisable(false);
                btnG.setPrefHeight(25);
                return;
            case("1"):
                btnOne.setDisable(false);
                btnOne.setPrefHeight(25);
                return;
            case("2"):
                btnTwo.setDisable(false);
                btnTwo.setPrefHeight(25);
                return;
            case("3"):
                btnThree.setDisable(false);
                btnThree.setPrefHeight(25);
                return;
            case("4"):
                btnFour.setDisable(false);
                btnFour.setPrefHeight(25);
                return;
            case("5"):
                btnFive.setDisable(false);
                btnFive.setPrefHeight(25);
                return;
        }
    }

    private double findAngle(Node bNode, Node sNode, Node eNode) {
        if(bNode == null) return 0;

        double angle = TextualDirections.getAngle(sNode, eNode);
        double previousAngle = TextualDirections.getAngle(bNode, sNode);
        double result = angle - previousAngle;
        if (result < 0) result = 180 + (180 - Math.abs(result));
        return result;
    }

    /**
     * returns the long name of the closest non hallway or walkway node
     * @param givenNode the node to check for
     *
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
        turnIcon.setStyle("-fx-fill: -onBackground");
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

        pathContent.setPrefHeight(0);
        selectViewOption();

        targetNode.addListener((observable, oldVal, newVal) ->{
            if(newVal.equals("START")){
                startFieldFlair.setVisible(true);
                endFieldFlair.setVisible(false);
            }else{
                startFieldFlair.setVisible(false);
                endFieldFlair.setVisible(true);
            }
            App.mapInteractionModel.currentTargetNode.set(newVal);
        });

        App.mapInteractionModel.mapTargetNode.addListener((observable, oldVal, newVal) ->{
                targetNode.set("END");
        });
        App.mapInteractionModel.mapTargetNode2.addListener((observable, oldVal, newVal) ->{
            targetNode.set("START");
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
            selectViewOption();
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
            selectViewOption();
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

        App.mapInteractionModel.pathFlag.addListener(observable -> {
            handleTestAddTextField(null);
        });

        startNodeField.requestFocus();
    }

    public void selectViewOption(){
        if(endNodeField.getText().equals("") || startNodeField.getText().equals("")){
            if(endNodeField.getText().equals("")) endNodeID = "";
            if(startNodeField.getText().equals("")) startNodeID = "";
            treeViewListNodes.setVisible(true);
            pathContent.setVisible(false);
            treeViewListNodes.setPrefHeight(Region.USE_COMPUTED_SIZE);
            pathContent.setPrefHeight(0);
            App.mapInteractionModel.mapTargetNode2.set(!App.mapInteractionModel.mapTargetNode2.get());

        } else{
            treeViewListNodes.setVisible(false);
            pathContent.setVisible(true);
            treeViewListNodes.setPrefHeight(0);
            pathContent.setPrefHeight(Region.USE_COMPUTED_SIZE);
            App.mapInteractionModel.mapTargetNode2.set(!App.mapInteractionModel.mapTargetNode2.get());
        }
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


    public void HandleDisplayFloorG(ActionEvent actionEvent) {
        handleTestAddTextField("G");
        App.mapInteractionModel.pathfindingFloorController.set("G");
    }

    public void HandleDisplayFloorOne(ActionEvent actionEvent) {
        handleTestAddTextField("1");
        App.mapInteractionModel.pathfindingFloorController.set("1");
    }

    public void HandleDisplayFloorTwo(ActionEvent actionEvent) {
        handleTestAddTextField("2");
        App.mapInteractionModel.pathfindingFloorController.set("2");
    }

    public void HandleDisplayFloorThree(ActionEvent actionEvent) {
        handleTestAddTextField("3");
        App.mapInteractionModel.pathfindingFloorController.set("3");
    }

    public void HandleDisplayFloorFour(ActionEvent actionEvent) {
        handleTestAddTextField("4");
        App.mapInteractionModel.pathfindingFloorController.set("4");
    }

    public void HandleDisplayFloorFive(ActionEvent actionEvent) {
        handleTestAddTextField("5");
        App.mapInteractionModel.pathfindingFloorController.set("5");
    }

    @FXML
    public void handleClearStartPoint(){
        App.mapInteractionModel.setStartNode("");
        startNodeField.setText("");
        startNodeID = "";
    }

    @FXML
    public void handleClearEndPoint(){
        App.mapInteractionModel.setEndNode("");
        endNodeField.setText("");
        endNodeID = "";

    }

    public void handleInputMethodChange(InputMethodEvent inputMethodEvent) { //TODO: What does this do?
    }
}


