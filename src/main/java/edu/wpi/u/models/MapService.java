package edu.wpi.u.models;

import edu.wpi.u.App;
import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.database.Database;
import edu.wpi.u.database.MapData;
import edu.wpi.u.exceptions.InvalidEdgeException;
import edu.wpi.u.exceptions.PathNotFoundException;
import edu.wpi.u.users.Role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class MapService {
  static MapManager mm = new MapManager();
  public static MapData md; //Temporairly did this to acess md.getLong name - Kohmei
  public HashMap<String, Integer> currentIDNumber = new HashMap<>();


  public static void main (String[] args){

  }

  public MapService() {
    md = new MapData();
  }

  /**
   * No args constructor used to pass through connection to test DB
   * @param testURL
   */
  public MapService(String testURL){
    md = new MapData(testURL);
  }

  public void loadStuff(){
    md.loadGraph(mm); //TODO: Can cause app to crash, this is ran before database initializes
  }

  /**
   * returns a node object from the node ID reference
   * @param nodeID
   * @return
   */
  public Node getNodeFromID(String nodeID){
    return mm.getNodeFromID(nodeID);
  }

  /**
   *  returns the Edge object given the edge ID reference
   * @param edgeId
   * @return
   */
  public Edge getEdgeFromID(String edgeId){ return mm.getEdgeFromID(edgeId);}

  /**
   * saves the currtent database to a csv
   * @param path
   * @param tableName
   */
  public static void saveCSVFile(String path, String tableName){
    Database.getDB().saveCSV(tableName,path, "test"); // TODO: Provide header
  }

  /**
   * loads a csv into the database the database handles updating the model
   * @param path
   * @param tableName
   */
  public void loadCSVFile(String path, String tableName){
    this.currentIDNumber = new HashMap<>();
    Database.getDB().dropValues(tableName);
    Database.getDB().readCSV(path,tableName);
  }

  /**
   * adds a node to the database and the model
   * TODO: make sure the coordinates are valid
   * @param node_id
   * @param x
   * @param y
   * @return
   * @throws InvalidEdgeException TODO: change to Invalid Edge Exception
   */
  public String addNode(String node_id, double x, double y, String floor, String Building, String nodeType, String longName, String shortName) throws InvalidEdgeException {
    try{
      md.addNode(node_id, x, y, floor, Building, nodeType, longName, shortName);
      mm.addNode(node_id, x, y, floor, Building, nodeType, longName, shortName, "u");
      return "";
    } catch (Exception e){
      InvalidEdgeException invalidEdge = new InvalidEdgeException();
      invalidEdge.description = "Invalid node";
      throw invalidEdge;

    }
  }

  public String  addNode (double xCoord, double yCoord, String floor, String building, String nodeType, String longName, String shortName) throws InvalidEdgeException{
    try{
      int curIndex;
      if(!currentIDNumber.containsKey(nodeType + floor)){
        curIndex = 1;
        currentIDNumber.put(nodeType + floor, 1);
      }else{
        curIndex = currentIDNumber.get(nodeType + floor) + 1;
      }

      currentIDNumber.put(nodeType + floor, curIndex);

      String curIndexString;
      if(curIndex < 10){
        curIndexString = "00" + String.valueOf(curIndex);
      }else if(curIndex < 100){
        curIndexString = "0" + String.valueOf(curIndex);
      }else {
        curIndexString = String.valueOf(curIndex);
      }
      String nodeID = "U" + nodeType + curIndexString + "0" + floor;

      md.addNode(nodeID, xCoord, yCoord, floor, building, nodeType, longName, shortName);
      mm.addNode(nodeID, xCoord, yCoord, floor, building, nodeType, longName, shortName, "u");
      return nodeID;
    } catch (Exception e){
      InvalidEdgeException invalidEdge = new InvalidEdgeException();
      invalidEdge.description = "Invalid node";
      throw invalidEdge;
    }
  }

  public void addNodeWithID(String nodeID,double xCoord,double yCoord,String floor,String building,String nodeType,String longName,String shortName) throws InvalidEdgeException{
    try{
      md.addNode(nodeID, xCoord, yCoord, floor, building, nodeType, longName, shortName);
      mm.addNode(nodeID, xCoord, yCoord, floor, building, nodeType, longName, shortName, "u");
    } catch (Exception e){
      InvalidEdgeException invalidEdge = new InvalidEdgeException();
      invalidEdge.description = "Invalid node";
      throw invalidEdge;

    }
  }

  /**
   * updates a nodes data
   * TODO:Make it so more than just the coordinates are updatable
   * @param node_id Node ID of the node to update
   * @param x       x coordinate
   * @param y       y coordinate
   * @return
   * TODO throw error if ID is not valid
   */
  public String updateNode(String node_id, double x, double y, String nodeType, String shortName, String longName) {
    if (md.isNode(node_id)){
      md.updateCoords(node_id, x, y);
      md.updLongname(node_id,longName);
      md.updShortname(node_id, shortName);
      md.updateNodeType(node_id,nodeType);
      mm.updateCoords(node_id, x, y);
      mm.setNodeType(node_id,nodeType);
      mm.updateNames(node_id,shortName,longName);
      return "";
    }
    else {
      return node_id;
    }
  }

  /**
   * removes a node from the model and database
   * @param node_id
   * @return
   * TODO add error checking on node ID
   */
  public ArrayList<Edge> deleteNode(String node_id) {
    if (md.isNode(node_id)){
      md.deleteNode(node_id);
      return mm.deleteNode(node_id);
    }
    else {
      return null;
    }
  }

  /**
   * add an edge to the database
   * @param edge_id
   * @param start_node
   * @param end_node
   * @return
   * @throws InvalidEdgeException
   * TODO add Node ID checking
   */
  public String addEdge(String edge_id, String start_node, String end_node, ArrayList<Role> permissions) throws InvalidEdgeException {
    if (md.isNode(start_node) && md.isNode(end_node)){
      md.addEdge(edge_id, start_node, end_node);
      md.updatePermissions(edge_id, permissions);
      mm.addEdge(edge_id, start_node, end_node, permissions);
      return "";
    }
    else {
      InvalidEdgeException invalidEdge = new InvalidEdgeException();
      invalidEdge.description = "Edge Creation Failed!";
      throw invalidEdge;
    }
  }

  /**
   * set the permission for the current edge
   * @param edgeID
   * @param permissions
   */
  public void updateEdgePermissions(String edgeID, ArrayList<Role> permissions){
    md.updatePermissions(edgeID, permissions);
    mm.updateUserPermissions(edgeID, permissions);
  }

  /**
   * updates the start node of an edge in the database and model
   * @param edge_id
   * @param start_node
   * @return
   * TODO add ID checking on Node and Edge IDs
   */
  public String updateStartEdge(String edge_id, String start_node) {
    if (md.isNode(start_node)){
      md.updateEdgeStart(edge_id, start_node);
      mm.updateEdgeStart(edge_id, start_node);
      return "";
    }
    else {
      return edge_id;
    }
  }

  /**
   * updates the end node of an edge in the database and model
   * @param edge_id
   * @param end_node
   * @return
   * TODO node and edge id error checking
   */
  public String updateEndEdge(String edge_id, String end_node) {
    if (md.isNode(end_node)){
      md.updateEdgeEnd(edge_id, end_node);
      mm.updateEdgeEnd(edge_id, end_node);
      return "";
    }
    else {
      return edge_id;
    }
  }

  /**
   * removes an edge from the database and model
   * @param edge_id
   * @return
   * TODO edge ID error checking
   */
  public String deleteEdge(String edge_id) {
    if (md.isEdge(edge_id)){
      md.removePermission(edge_id);
      md.deleteEdge(edge_id);
      mm.deleteEdge(edge_id);
      return "";
    }
    else {
      return edge_id;
    }
  }

  public ArrayList<Node> getNodes() {
    return mm.getAllNodes();
    /*
    Returns an ArrayList of all Node Objects in the graph
     */
  }

  public ArrayList<Edge> getEdges() {
    return mm.getAllEdges();
    /*
    Returns an ArrayList of all Edge Objects in the graph
     */
  }

  /**
   * runs the pathfinding algorithm specified in the APP class
   * @param start_node_id
   * @param end_node_id
   * @return
   * @throws PathNotFoundException
   */
  public ArrayList<Node> runPathfinding(String start_node_id, String end_node_id) throws PathNotFoundException {

    switch (App.pathfindingAlgorithm){
      case "ASTAR":
        return new ArrayList<>(this.aStar(start_node_id,end_node_id));
      case "DFS":
        return mm.runDFS(start_node_id,end_node_id);
      case "BFS":
        return mm.runBFS(start_node_id,end_node_id);
      default:
        return new ArrayList<>();
    }
  }

  /**
   * runs A* on the model and gets a linked list of the path
   * returns an empty list if there is no path to be found TODO make no path found an error
   * @param start_node_id
   * @param end_node_id
   * @return
   * @throws PathNotFoundException
   */
  public LinkedList<Node> aStar(String start_node_id, String end_node_id) throws PathNotFoundException {
    if (md.isNode(start_node_id) && md.isNode(end_node_id)){
      return mm.runAStar(start_node_id, end_node_id);
    }
    else {
      return null;
    }
  }
}
