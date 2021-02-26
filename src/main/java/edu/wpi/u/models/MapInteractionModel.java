package edu.wpi.u.models;

public class MapInteractionModel {
    private String nodeID = "";
    private String edgeID = "";
    private double Coords[] = new double[2];

    public MapInteractionModel(){ }

    /**
     *  get node ID
     * @return
     */
    public String getNodeID() {
        return nodeID;
    }

    /**
     * set node ID
     * @param nodeID
     */
    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }

    /**
     * get edge ID
     * @return
     */
    public String getEdgeID() {
        return edgeID;
    }

    /**
     * set Edge ID
     * @param edgeID
     */
    public void setEdgeID(String edgeID) {
        this.edgeID = edgeID;
    }

    /**
     * get coordinates in the format [x,y]
     * @return
     */
    public double[] getCoords() {
        return Coords;
    }

    /**
     * set coordinates in the format [x,y]
     * @param coords
     */
    public void setCoords(double[] coords) {
        Coords = coords;
    }
}
