package edu.wpi.u.models;

import edu.wpi.u.algorithms.Node;
import edu.wpi.u.controllers.mapbuilder.MapBuilderBaseController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.LinkedList;

public class MapInteractionModel {


    public boolean highRisk = false;
    private String currentAction = "SELECT";
    public SimpleStringProperty nodeID = new SimpleStringProperty(" ");
    public SimpleStringProperty nodeIDForHover = new SimpleStringProperty(" ");
    public SimpleBooleanProperty reloadPathfinding = new SimpleBooleanProperty(true);
    private String previousNodeID = "";
    private String previousPreviousNodeID = "";
    private String edgeID = "";
    public String previusEdgeID = "";
    private double Coords[] = new double[2];
    public SimpleStringProperty pathFlag = new SimpleStringProperty("");
    public SimpleStringProperty pathPreviewFlag = new SimpleStringProperty("");
    public ArrayList<Node> path = new ArrayList<>();
    public ArrayList<Node> pathPreview = new ArrayList<>();
    private String building = "Faulkner";
    public SimpleStringProperty editFlag = new SimpleStringProperty("");
    public AnchorPane selectedContextBox;
    public AnchorPane selectedEdgeContextBox;
    public SimpleStringProperty mapImageResourcePathfinding = new SimpleStringProperty("/edu/wpi/u/views/Images/FaulknerCampus.png");
    public SimpleStringProperty mapImageResource = new SimpleStringProperty("/edu/wpi/u/views/Images/FaulknerCampus.png");
    public String floor = "G";
    public String floorPathfinding = "G";
    public LinkedList<String> nodeIDList = new LinkedList<>();
    public String deselectedNodeID = "";
    public String toggledNodeID = "";
    public ArrayList<String> edgeIDList = new ArrayList<>();
    public boolean clickedOnNode = false;
    public SimpleBooleanProperty mapTargetNode = new SimpleBooleanProperty(false);//for start node
    public SimpleBooleanProperty mapTargetNode2 = new SimpleBooleanProperty(false);//for end node
    public String aline = "";
    public double alineValue;
    public SimpleStringProperty currentTargetNode = new SimpleStringProperty("");


    public SimpleStringProperty pathfindingFloorController = new SimpleStringProperty(" ");//for start node

    public void addToNodeIdList(String nodeID){
        if(!this.nodeIDList.contains(nodeID)){
            this.nodeIDList.addFirst(nodeID);
            this.toggledNodeID = nodeID;
        }else {
            this.nodeIDList.remove(nodeID);
            this.toggledNodeID = nodeID;
        }

    }

    public LinkedList<String> resetNodeIDList(){
        LinkedList<String> returnMe = this.nodeIDList;
        this.nodeIDList = new LinkedList<>();
        return returnMe;
    }

    public double avgSelectedCords(){
        double xTotal = 0.0, yTotal = 0.0;
        for (String nodeID: this.nodeIDList ) {
            //todo finish
        }
        return 0.0;
    }

    public String getPreviousPreviousNodeID() {
        return previousPreviousNodeID;
    }

    public void clearPreviousNodeID() {
        this.deselectedNodeID = "";
    }

    public String getPreviousNodeID() {
        return deselectedNodeID;
    }

    public String getBuilding() {
        return building;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(String currentAction) {
        this.currentAction = currentAction;
    }

    public String getNodeID() {
        return nodeID.get();
    }

    public void setNodeID(String nodeID) {
        if(!this.nodeID.get().equals(nodeID)) {
            deselectedNodeID = this.nodeID.get();
            this.nodeID.set(nodeID);
            addToNodeIdList(nodeID);
        }
    }

    public String getEdgeID() {
        return edgeID;
    }

    public void setEdgeID(String edgeID) {
        this.previusEdgeID = this.edgeID;
        this.edgeID = edgeID;
    }

    public double[] getCoords() {
        return Coords;
    }

    public void setCoords(double[] coords) {
        Coords = coords;
    }

    public void setEndNode(String nodeID){
        mapTargetNode.set(!mapTargetNode.get());
        System.out.println("I have not crashed yet 138 MapInteractionModel");
        this.nodeID.set(nodeID);
        // Crashes before
    }

    public void setStartNode(String nodeID){
        mapTargetNode2.set(!mapTargetNode2.get());
        this.nodeID.set(nodeID);
    }
}
