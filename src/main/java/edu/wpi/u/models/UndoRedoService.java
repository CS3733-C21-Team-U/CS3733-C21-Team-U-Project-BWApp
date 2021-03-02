package edu.wpi.u.models;

import edu.wpi.u.App;
import edu.wpi.u.algorithms.MapEdit;
import edu.wpi.u.exceptions.InvalidEdgeException;
import edu.wpi.u.users.StaffType;

import java.util.ArrayList;
import java.util.LinkedList;

public class UndoRedoService {
    LinkedList<MapEdit> edits = new LinkedList<>();
    int curIndex = -1;

    public UndoRedoService(){
        System.out.println("Starting the UndoRedoService");
    }

    /**
     * makes a node and addes it to the list of edits made
     * also removes all edits that have been undone
     * @param xCoord
     * @param yCoord
     * @param floor
     * @param building
     * @param nodeType
     * @param longName
     * @param shortName
     * @throws InvalidEdgeException
     */
    public void addNode(double xCoord, double yCoord, String floor, String building, String nodeType, String longName, String shortName) throws InvalidEdgeException {
        MapEdit thisEdit = new MapEdit();
        thisEdit.addNode(xCoord,yCoord,floor,building,nodeType,longName,shortName);
        if(curIndex < edits.size() - 1){
            while(curIndex != edits.size() - 1){
                edits.removeLast();
            }
        }
        this.curIndex ++;
        edits.addLast(thisEdit);
    }

    /**
     * removes a node and addes the edits to the list
     * also removes all edits that have been undone
     * @param nodeID
     */
    public void deleteNode(String nodeID){
        MapEdit thisEdit = new MapEdit(nodeID);
        thisEdit.deleteNode();
        if(curIndex < edits.size() - 1){
            while(curIndex != edits.size() - 1){
                edits.removeLast();
            }
        }
        this.curIndex ++;
        edits.addLast(thisEdit);
    }

    /**
     * updates a node and adds that to the list of edits
     * also removes all edits that have been undone
     * @param nodeID
     * @param xCoord
     * @param yCoord
     * @param longName
     * @param shortName
     */
    public void updateNode(String nodeID,double xCoord, double yCoord, String nodeType, String longName, String shortName){
        MapEdit thisEdit = new MapEdit(nodeID);
        thisEdit.updateNode(xCoord,yCoord, nodeType, longName,shortName);
        if(curIndex < edits.size() - 1){
            while(curIndex != edits.size() - 1){
                edits.removeLast();
            }
        }
        this.curIndex ++;
        edits.addLast(thisEdit);
    }

    /**
     * adds an edge and addes it to the list of edits
     * also removes all edits that have been undone
     * @param start_node
     * @param end_node
     * @param permissions
     * @throws InvalidEdgeException
     */
    public void addEdge(String start_node, String end_node, ArrayList<StaffType> permissions) throws InvalidEdgeException {
        MapEdit thisEdit = new MapEdit();
        thisEdit.addEdge(start_node,end_node,permissions);
        if(curIndex < edits.size() - 1){
            while(curIndex != edits.size() - 1){
                edits.removeLast();
            }
        }
        this.curIndex ++;
        edits.addLast(thisEdit);
    }

    /**
     * deletes the edge and adds the edit to the list of edits
     * also removes all edits that have been undone
     * @param edgeID
     */
    public void deleteEdge(String edgeID){
        MapEdit thisEdit = new MapEdit(edgeID);
        thisEdit.deleteEdge();
        if(curIndex < edits.size() - 1){
            while(curIndex != edits.size() - 1){
                edits.removeLast();
            }
        }
        this.curIndex ++;
        edits.addLast(thisEdit);

    }

    /**
     * updates the edge and adds the edit to the list of edits
     * also removes all edits that have been undone
     * @param edgeID
     * @param permissions
     */
    public void updateEdge(String edgeID, ArrayList<StaffType> permissions){
        MapEdit thisEdit = new MapEdit(edgeID);
        thisEdit.updateEdge(permissions);
        if(curIndex < edits.size() - 1){
            while(curIndex != edits.size() - 1){
                edits.removeLast();
            }
        }
        this.curIndex ++;
        edits.addLast(thisEdit);

    }

    /**
     * undoes the last edit and moves the current index
     * @throws Exception
     */
    public void undo() throws Exception {
        if(curIndex >= 0) {
            MapEdit curEdit = edits.get(curIndex);
            curIndex--;
            curEdit.toggleEdit();
        }else{
            throw new Exception("Tried To UNDO an edit and there was no edits left");
        }
        App.mapInteractionModel.editFlag.set(String.valueOf(Math.random()));
    }

    /**
     * redoes an edit and moves the index
     * @throws Exception
     */
    public void redo() throws Exception {
        if(curIndex < edits.size() - 1) {
            curIndex++;
            MapEdit curEdit = edits.get(curIndex);
            curEdit.toggleEdit();
        }else{
            throw new Exception("Tried To REDO an edit and there was no edits left");
        }
        App.mapInteractionModel.editFlag.set(String.valueOf(Math.random()));
    }



}
