package edu.wpi.u.models;

import edu.wpi.u.controllers.NodeContextMenuController;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class MapInteractionModel {




    private String nodeID = "";
    private String edgeID = "";
    private double Coords[] = new double[2];

    public AnchorPane selectedNodeContextBox;
    public AnchorPane selectedEdgeContextBox;
    public SimpleStringProperty mapImageResource = new SimpleStringProperty("/edu/wpi/u/views/Images/FaulknerCampus.png");
    public String floor = "G";
    public ArrayList<String> nodeIDList = new ArrayList<String>();
    public ArrayList<String> edgeIDList = new ArrayList<String>();

    public String getNodeID() {
        return nodeID;
    }

    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
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
