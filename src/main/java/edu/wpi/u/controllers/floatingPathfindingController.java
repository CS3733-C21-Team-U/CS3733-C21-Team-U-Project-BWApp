package edu.wpi.u.controllers;

import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.exceptions.PathNotFoundException;
import edu.wpi.u.models.TextualDirections;
import javafx.fxml.FXML;

import java.awt.*;
import java.util.ArrayList;

public class floatingPathfindingController {
    @FXML
    Label textualDirections;
    @FXML
    Label endNode;
    @FXML
    Label startNode;
    @FXML

    String targetNode = "START";//flag for
    String startNodeID = "", endNodeID = "";
    ArrayList<Node> path = new ArrayList<>();
    ArrayList<String> textualDirectionsStrings = new ArrayList<>();
    String textualDirectionsMegaString = "";
    public void endNodeButtonHandler(){
        targetNode = "END";
    }

    @FXML
    public void startNodeButtonHandler(){
        targetNode = "START";
    }

    public void initialize(){
        textualDirections.setText("Click on a node to select a location\n");
        App.mapInteractionModel.nodeID.addListener((observable, oldValue, newValue)  ->{
            if(targetNode.equals("START")){
                startNode.setText(newValue);
                startNodeID = newValue;
                targetNode = "END";
            }else{
                endNode.setText(newValue);
                endNodeID = newValue;
            }
            if(!startNodeID.equals("") && !endNodeID.equals("")){
                try {
                    path = App.mapService.runPathfinding(startNodeID,endNodeID);
                } catch (PathNotFoundException e) {
                    e.printStackTrace();
                }
                textualDirectionsStrings = TextualDirections.getTextualDirections(path);
            }
        });
    }

}


