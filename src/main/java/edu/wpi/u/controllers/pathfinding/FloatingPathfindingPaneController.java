package edu.wpi.u.controllers.pathfinding;

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
import javafx.scene.input.InputMethodEvent;
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

public class FloatingPathfindingPaneController {
    public VBox textDirectionContainer;
    public JFXTextField endNodeField;
    public JFXTextField startNodeField;
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



    public void handleTestAddTextField() {


        textDirectionContainer.getChildren().clear();
        LinkedList<Edge> edgePath = getEdgesTest.EdgesFollowed(App.mapInteractionModel.path);

        if(edgePath.isEmpty());
        else {

            Node bNode = null;
            Node eNode = path.get(0);
            Node lastTurnNode = null;
            int index = 0;

            for(Edge e : edgePath) {
                index++;
                String iconID;
                String angleDescription;
                double angleDifferance;
                int dist = (int) Math.round(e.getWeight()/3); //3 being pixels for foot, see TextualDirections.java
                Node sNode = eNode;
                if (sNode == e.getStartNode())  eNode = e.getEndNode();
                else eNode = e.getStartNode(); //makes sure start and end nodes are actually what we want

                //Finds angle for path
                if(bNode == null) { //1st path exception
                    angleDescription = "";
                    iconID = "M5,9l1.41,1.41L11,5.83V22H13V5.83l4.59,4.59L19,9l-7-7L5,9z";
                    angleDifferance = 0;
                } else {
                    double angle = TextualDirections.getAngle(sNode, eNode);
                    double previousAngle = TextualDirections.getAngle(bNode, sNode);
                    angleDifferance = angle - previousAngle;

                    if (angleDifferance < 0) {
                        angleDifferance = 180 + (180 - Math.abs(angleDifferance));
                    }
                    angleDescription = TextualDirections.textualAngleDescription(angleDifferance);
                    iconID = TextualDirections.findTextualIconID(angleDifferance);
                }

                String turnText;

                if(sNode.getNodeType().equals("ELEV") || eNode.getNodeType().equals("ELEV"))  {
                    turnText = "Elevator";
                    iconID = "M19,5v14H5V5H19 M19,3H5C3.9,3,3,3.9,3,5v14c0,1.1,0.9,2,2,2h14c1.1,0,2-0.9,2-2V5C21,3.9,20.1,3,19,3L19,3z M10,18v-4h1 " +
                            "v-2.5c0-1.1-0.9-2-2-2H8c-1.1,0-2,0.9-2,2V14h1v4H10z M8.5,8.5c0.69,0,1.25-0.56,1.25-1.25S9.19,6,8.5,6S7.25,6.56,7.25,7.25 S7.81,8.5,8.5,8.5z M18,11l-2.5-4L13,11H18z M13,13l2.5,4l2.5-4H13z";
                }
                else if(sNode.getNodeType().equals("STAI") || eNode.getNodeType().equals("STAI"))  {
                    turnText = "Stairs";
                    iconID = "M19,5v14H5V5H19 M19,3H5C3.9,3,3,3.9,3,5v14c0,1.1,0.9,2,2,2h14c1.1,0,2-0.9,2-2V5C21,3.9,20.1,3,19,3L19,3z M18,6h-4.42 v3.33H11v3.33H8.42V16H6v2h4.42v-3.33H13v-3.33h2.58V8H18V6z";
                }

                HBox stepHBoxContainer;

                if(eNode.getNodeType().equals("STAI") || eNode.getNodeType().equals("ELEV")) {
                    stepHBoxContainer = createDirectionBox("Step into staircase/ elevator", iconID);
                    textDirectionContainer.getChildren().add(stepHBoxContainer);
                }

                else if(sNode.getNodeType().equals("STAI") || sNode.getNodeType().equals("ELEV")) {
                    stepHBoxContainer = createDirectionBox("Step off staircase/ elevator at floor " + eNode.getFloor(), iconID);
                    textDirectionContainer.getChildren().add(stepHBoxContainer);
                    lastTurnNode = eNode;
                }

                else if(sNode.getNodeType().equals("ELEV") && eNode.getNodeType().equals("ELEV")) { /*Elevator-To-Elevator connection*/}
                else if(sNode.getNodeType().equals("STAI") && eNode.getNodeType().equals("STAI")) { /*Stair-To-Stair connection*/}

                else if(angleDifferance > 22.5 && angleDifferance < 337.5) { //if turn is found on current node
                    if(lastTurnNode != null) {
                        stepHBoxContainer = createDirectionBox("Continue straight for " + distAggregate(lastTurnNode, sNode) + " feet", "M5,9l1.41,1.41L11,5.83V22H13V5.83l4.59,4.59L19,9l-7-7L5,9z");
                        textDirectionContainer.getChildren().add(stepHBoxContainer);
                    }
                    stepHBoxContainer = createDirectionBox(angleDescription + " onto " + sNode.getLongName(), iconID);
                    textDirectionContainer.getChildren().add(stepHBoxContainer);
                    index++;
                    lastTurnNode = sNode;
                }


                else if(bNode == null) {
                    stepHBoxContainer = createDirectionBox("Begin by travelling " + dist + " feet from " + sNode.getLongName(), iconID);
                    textDirectionContainer.getChildren().add(stepHBoxContainer);
                }

                else if((sNode.getNodeType().equals("HALL") && eNode.getNodeType().equals("HALL"))) {/*Hallway*/ }

                else {
                    stepHBoxContainer = createDirectionBox("Continue straight for " + distAggregate(lastTurnNode, eNode) + " feet", "M5,9l1.41,1.41L11,5.83V22H13V5.83l4.59,4.59L19,9l-7-7L5,9z");
                    textDirectionContainer.getChildren().add(stepHBoxContainer);
                    //lastTurnNode = eNode;
                }

                bNode = sNode;
            }
        }
    }

    /**
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

        AutoCompletionBinding<String> autoFillStart = TextFields.bindAutoCompletion(startNodeField , FXCollections.observableArrayList(nodeNames));
        AutoCompletionBinding<String> autoFillEnd = TextFields.bindAutoCompletion(endNodeField , FXCollections.observableArrayList(nodeNames));

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
                handleTestAddTextField();
        });

        startNodeField.requestFocus();

        App.mapInteractionModel.pathFlag.addListener(observable -> {
            handleTestAddTextField();
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


