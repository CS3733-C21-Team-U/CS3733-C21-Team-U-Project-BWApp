package edu.wpi.u.controllers.pathfinding;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.algorithms.getEdgesTest;
import edu.wpi.u.exceptions.PathNotFoundException;
import edu.wpi.u.models.MapService;
import edu.wpi.u.models.TextualDirections;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FloatingPathfindingPaneController {
    public VBox textDirectionContainer;
    public JFXTextField endNodeField;
    public JFXTextField startNodeField;
    @FXML
    Label endNode;
    @FXML
    Label startNode;


    String targetNode = "START";//flag for
    String startNodeID = "", endNodeID = "";
    ArrayList<Node> path = new ArrayList<>();
    ArrayList<String> textualDirectionsStrings = new ArrayList<>();
    String textualDirectionsMegaString = "";


    public void handleTestAddTextField() {


        textDirectionContainer.getChildren().clear();


        LinkedList<Edge> edgePath = getEdgesTest.EdgesFollowed(App.mapInteractionModel.path);
        if(edgePath.isEmpty()) System.out.println("empty list!!");
        else {
            double currentAngle = 0, prevAngle = 0;

            Node bNode = null;
            Node eNode = path.get(0);
            int index = 0;
            for(Edge e : edgePath) {
                index++;
                String iconID = "";
                int eDist = (int) Math.round(e.getWeight()/3); //3 being pixels for foot, see TextualDirections.java

                String angleDescription = "";

                Node sNode = eNode;
                if (sNode == e.getStartNode())  eNode = e.getEndNode();
                else eNode = e.getStartNode(); //makes sure start and end nodes are actually what we want

                //Finds angle for path
                if(bNode == null) {
                    angleDescription = "";
                    iconID = "M9,5v2h6.59L4,18.59L5.41,20L17,8.41V15h2V5H9z";
                }
                else {
                    double angle = TextualDirections.getAngle(sNode, eNode);
                    double previousAngle = TextualDirections.getAngle(bNode, sNode);
                    double angleDifferance = angle - previousAngle;

                    if (angleDifferance < 0) {
                        angleDifferance = 180 + (180 - Math.abs(angleDifferance));
                    }
                    angleDescription = TextualDirections.textualAngleDescription(angleDifferance);
                    iconID = "M9,5v2h6.59L4,18.59L5.41,20L17,8.41V15h2V5H9z";
                }


                iconID = "M9,5v2h6.59L4,18.59L5.41,20L17,8.41V15h2V5H9z"; //TODO: make dynamic to type of turn
                Label turnText = new Label(index + ": " + angleDescription + sNode.getLongName() + " to " + eNode.getLongName()); //TODO: add tailored directions here
                turnText.getStyleClass().add("subtitle");
                turnText.setWrapText(true);
                Label distanceText = new Label("Continue straight for "+ eDist +" feet");// TODO: add distance to walk after
                distanceText.getStyleClass().add("caption");
                SVGPath turnIcon = new SVGPath();
                turnIcon.setStyle("-fx-padding: 20px");
                turnIcon.setContent(iconID);
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

                bNode = sNode;
            }
        }
    }

    /**
     * sets flag for what field is being filled to END
     */


    public void endNodeButtonHandler(){
        targetNode = "END";
    }

    /**
     * sets flag for what field is being filled to START
     */
    @FXML
    public void startNodeButtonHandler(){
        targetNode = "START";
    }

    public void initialize(){
//        textualDirections.setText("Click on a node to select a location.\nUse the buttons to pick which location to fill.");

        //TODO: In theory this gives all Longnames as string but something is broken! Kohmei
        HashMap<String, String> namesAndIDs= MapService.md.getLongnames();
        Set<String> strings = namesAndIDs.keySet();

        ArrayList<String> nodeNames = new ArrayList<>();

        AutoCompletionBinding<String> autoFillStart = TextFields.bindAutoCompletion(startNodeField , FXCollections.observableArrayList(nodeNames));
        AutoCompletionBinding<String> autoFillEnd = TextFields.bindAutoCompletion(endNodeField , FXCollections.observableArrayList(nodeNames));

        App.mapInteractionModel.nodeID.addListener((observable, oldValue, newValue)  ->{
            if(targetNode.equals("START")){
                startNode.setText(App.mapService.getNodeFromID(newValue).getLongName());
                startNodeID = newValue;
                targetNode = "END";
            } else {
                endNode.setText(App.mapService.getNodeFromID(newValue).getLongName());
                endNodeID = newValue;
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
    }

    public void handleStartEndSwap(ActionEvent actionEvent) {
        String tempStorage = startNodeField.getText();
        startNodeField.setText(endNodeField.getText());
        endNodeField.setText(tempStorage);
    }
}


