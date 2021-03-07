package edu.wpi.u.controllers.pathfinding;

import com.jfoenix.controls.JFXTextArea;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.exceptions.PathNotFoundException;
import edu.wpi.u.models.TextualDirections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class FloatingPathfindingPaneController {
    @FXML
    JFXTextArea textualDirections;
    @FXML
    Label endNode;
    @FXML
    Label startNode;


    String targetNode = "START";//flag for
    String startNodeID = "", endNodeID = "";
    ArrayList<Node> path = new ArrayList<>();
    ArrayList<String> textualDirectionsStrings = new ArrayList<>();
    String textualDirectionsMegaString = "";

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
        textualDirections.setText("Click on a node to select a location.\nUse the buttons to pick which location to fill.");

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
                textualDirections.setText(textualDirectionsMegaString);
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

}


