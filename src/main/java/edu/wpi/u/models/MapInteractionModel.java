package edu.wpi.u.models;

import edu.wpi.u.algorithms.Node;
import edu.wpi.u.controllers.mapbuilder.MapBuilderBaseController;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.LinkedList;

public class MapInteractionModel {


    private String currentAction = "SELECT";
    public SimpleStringProperty nodeID = new SimpleStringProperty(" ");
    public SimpleStringProperty nodeIDForHover = new SimpleStringProperty(" ");
    private String previousNodeID = "";
    private String previousPreviousNodeID = "";
    private String edgeID = "";
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
    public ArrayList<String> edgeIDList = new ArrayList<>();
    public boolean clickedOnNode = false;
    public boolean pathThingy = false;


    public void addToNodeIdList(String nodeID){
        if(!this.nodeIDList.contains(nodeID)){
            this.nodeIDList.addFirst(nodeID);
        }else {
            this.nodeIDList.remove(nodeID);
        }

    }

    public LinkedList<String> resetNodeIDList(){
        String firstID = this.nodeIDList.getFirst();
        this.nodeIDList.remove(firstID);
        LinkedList<String> returnMe = this.nodeIDList;
        this.nodeIDList = new LinkedList<>();
        addToNodeIdList(firstID);
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
        this.previousNodeID = "";
    }

    public String getPreviousNodeID() {
        return previousNodeID;
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
        this.edgeID = edgeID;
    }

    public double[] getCoords() {
        return Coords;
    }

    public void setCoords(double[] coords) {
        Coords = coords;
    }


//    /**
//     *  get node ID
//     * @return
//     */
//    public String getNodeID() {
//        return nodeID;
//    }
//
//    /**
//     * set node ID
//     * @param nodeID
//     */
//    public void setNodeID(String nodeID) {
//        this.nodeID = nodeID;
//    }
//
//    /**
//     * get edge ID
//     * @return
//     */
//    public String getEdgeID() {
//        return edgeID;
//    }
//
//    /**
//     * set Edge ID
//     * @param edgeID
//     */
//    public void setEdgeID(String edgeID) {
//        this.edgeID = edgeID;
//    }
//
//    /**
//     * get coordinates in the format [x,y]
//     * @return
//     */
//    public double[] getCoords() {
//        return Coords;
//    }
//
//    /**
//     * set coordinates in the format [x,y]
//     * @param coords
//     */
//    public void setCoords(double[] coords) {
//        Coords = coords;
//    }
}
