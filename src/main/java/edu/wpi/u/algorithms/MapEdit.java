package edu.wpi.u.algorithms;

import edu.wpi.u.App;
import edu.wpi.u.exceptions.InvalidEdgeException;
import edu.wpi.u.users.StaffType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapEdit {
    private ArrayList<Edge> removedEdges = new ArrayList<>();
    private String ID;
    private EditTypes edit;
    private String type = "NONE";
    private Node oldNode = null;
    private Edge oldEdge = null;

    public static void main(String args[]){
        new MapEdit("NODE1_NODE2");
    }

    /**
     * Takes in an ID (Node or Edge)
     * @param ID
     */
    public MapEdit(String ID){
        System.out.println("MapEdit made");
        this.ID = ID;
        List<String> items = Arrays.asList(ID.split("\\s*_\\s*"));
        if(items.size() == 2){
            this.type = "EDGE";
        }else if (items.size() == 1){
            this.type = "NODE";
        }else {
        }
    }

    public MapEdit(){
        System.out.println("MapEdit made but was the lame way");
    }


    /**
     * creates an edge and it gets added to the database
     * @param xCoord
     * @param yCoord
     * @param floor
     * @param building
     * @param nodeType
     * @param longName
     * @param shortName
     * @throws InvalidEdgeException
     */
    public void addNode(double xCoord, double yCoord, String floor, String building, String nodeType, String longName, String shortName) throws InvalidEdgeException{
        try{
            this.type = "NODE";
            this.ID = App.mapService.addNode(xCoord, yCoord, floor, building, nodeType, longName, shortName);
            this.edit = EditTypes.ADD;
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * overload of other add node but takes in the node ID
     * @param nodeID
     * @param xCoord
     * @param yCoord
     * @param floor
     * @param building
     * @param nodeType
     * @param longName
     * @param shortName
     * @throws InvalidEdgeException
     */
    public void addNode(String nodeID,
                        double xCoord,
                        double yCoord,
                        String floor,
                        String building,
                        String nodeType,
                        String longName,
                        String shortName)throws InvalidEdgeException{
        try{
            App.mapService.addNodeWithID(nodeID,xCoord, yCoord, floor, building, nodeType, longName, shortName);
            this.edit = EditTypes.ADD;
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * deletes a node and saves the data on what the node used to be
     */
    public void deleteNode(){
        try{
            this.edit = EditTypes.DELETE;
            this.oldNode = new Node(App.mapService.getNodeFromID(this.ID));
            this.removedEdges = App.mapService.deleteNode(this.ID);
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * updates a node and saves the data on what the old node was
     * @param xCoord
     * @param yCoord
     * @param longName
     * @param shortName
     */
    public void updateNode(double xCoord, double yCoord, String nodeType, String longName, String shortName){
        try{
            this.edit = EditTypes.UPDATE;
            this.oldNode = new Node(App.mapService.getNodeFromID(this.ID));
            App.mapService.updateNode(this.ID,xCoord,yCoord, nodeType, shortName,longName);
        }catch (Exception e){
            throw e;
        }
    }

    /**
     *  creates a new edge and records that it is new
     * @param start_node
     * @param end_node
     * @throws InvalidEdgeException
     */
    public void addEdge(String start_node, String end_node, ArrayList<StaffType> permissions) throws InvalidEdgeException {
        try{
            this.edit = EditTypes.ADD;
            App.mapService.addEdge(start_node + "_" + end_node, start_node, end_node, permissions);
            this.ID = start_node + "_" + end_node;
        }catch (Exception e){
            throw e;
        }
    }
    /**
     * removes an edge from the model and the database and stores the old values
     */
    public void deleteEdge(){
        try{
            this.edit = EditTypes.DELETE;
            this.oldEdge = App.mapService.getEdgeFromID(this.ID);
            App.mapService.deleteEdge(this.ID);
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * updates the permissions of an edge and stores the old version
     * @param permissions
     */
    public void updateEdge(ArrayList<StaffType> permissions){
        try{
            this.edit = EditTypes.UPDATE;
            this.oldEdge = App.mapService.getEdgeFromID(this.ID);
            App.mapService.updateEdgePermissions(this.ID, permissions);
        }catch (Exception e) {
            throw e;
        }
    }

    /**
     * toggles the edited node/edge with the saved one
     * @throws InvalidEdgeException
     * @throws Exception
     */
    public void toggleEdit() throws InvalidEdgeException, Exception{
        try {
            if (this.type.equals("NODE")) {
                double x,y;
                String floor,building,nodeType,longName,shortName;

                switch (this.edit) {
                    case UPDATE:
                        x = this.oldNode.getCords()[0];
                        y = this.oldNode.getCords()[1];
                        floor = this.oldNode.getFloor();
                        building = this.oldNode.getBuilding();
                        nodeType = this.oldNode.getNodeType();
                        longName = this.oldNode.getLongName();
                        shortName = this.oldNode.getShortName();
                        this.updateNode(x, y,nodeType, longName, shortName);
                        break;
                    case DELETE:
                        x = this.oldNode.getCords()[0];
                        y = this.oldNode.getCords()[1];
                        floor = this.oldNode.getFloor();
                        building = this.oldNode.getBuilding();
                        nodeType = this.oldNode.getNodeType();
                        longName = this.oldNode.getLongName();
                        shortName = this.oldNode.getShortName();
                        this.addNode(this.ID, x, y, floor, building, nodeType, longName, shortName);
                        for(Edge curEdge: this.removedEdges){
                            this.addEdge(curEdge.getStartNode().getNodeID(),curEdge.getEndNode().getNodeID(),curEdge.getUserPermissions());
                        }
                        this.edit = EditTypes.ADD;
                        break;
                    case ADD:
                        this.deleteNode();
                        this.edit = EditTypes.DELETE;
                        break;
                }
            }else if (this.type.equals("EDGE")){
                switch (this.edit) {
                    case UPDATE:
                        this.updateEdge(oldEdge.getUserPermissions());
                        break;
                    case DELETE:
                        this.addEdge(oldEdge.getStartNode().getNodeID(),oldEdge.getEndNode().getNodeID(), oldEdge.getUserPermissions());
                        break;
                    case ADD:
                        this.deleteEdge();
                        break;
                }
            }else{
                throw new Exception("MapEdit somehow did not get the node/edge type in editing (~line 200)");
            }
        }catch (Exception e){
            throw e;
        }
    }


}
