package edu.wpi.u.models;

import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.database.MapData;
import edu.wpi.u.exceptions.InvalidEdgeException;
import edu.wpi.u.exceptions.PathNotFoundException;

import java.util.ArrayList;
import java.util.LinkedList;

public class MapService {

  static MapManager mm = new MapManager();
  static MapData md;


  public static void main (String[] args){

  }

  public MapService() {
    md = new MapData();
    md.loadGraph(mm);
  }

  public Node getNodeFromID(String nodeID){
    return mm.getNodeFromID(nodeID);
  }


  public void saveCSVFile(String path, String tableName){
    //md.dropValues();
    md.saveCSV(tableName,path, "test"); // TODO: Provide header
  }

  public void loadCSVFile(String path, String tableName){
    md.dropValues();
    md.readCSV(path,tableName);
  }

  /*
  Make sure x & y are positive integers within the map coordinate range
  */
  public String addNode(String node_id, double x, double y) throws InvalidEdgeException {
    try{
      md.addNode(node_id, x, y, "0", "Def", "Def", "Def", "Def");
      mm.addNode(node_id, x, y, "0", "Def", "Def", "Def", "Def", "u");
      return "";
    } catch (Exception e){
      InvalidEdgeException invalidEdge = new InvalidEdgeException();
      invalidEdge.description = "Invalid node";
      throw invalidEdge;

    }
    /*
    Check if valid node_id
    Return "" is a success
    Return node_id if node already exists / invalid
     */
  }

  public String updateNode(String node_id, double x, double y) {
    if (md.isNode(node_id)){
      md.updateCoords(node_id, x, y);
      mm.updateCoords(node_id, x, y);
      return "";
    }
    else {
      return node_id;
    }

    // gm needs to write
    /*
    Check if valid node_id
    Return "" is a success
    Return node_id if node already exists / invalid
     */
  }

  public String deleteNode(String node_id) {
    if (md.isNode(node_id)){
      md.deleteNode(node_id);
      mm.deleteNode(node_id);
      return "";
    }
    else {
      return node_id;
    }
    /*
    Check if valid node_id
    Return "" is a success
    Return node_id if node already exists / invalid
     */
    //        dm.delNode(node_id);
  }

  public String addEdge(String edge_id, String start_node, String end_node) throws InvalidEdgeException {
    if (md.isNode(start_node) && md.isNode(end_node)){
      md.addEdge(edge_id, start_node, end_node);
      mm.addEdge(edge_id, start_node, end_node);
      return "";
    }
    else {
      InvalidEdgeException invalidEdge = new InvalidEdgeException();
      invalidEdge.description = "Edge Creation Failed!";
      throw invalidEdge;
    }


    /*
    Check if valid edge_id
    Return "" is a success
    Return edge_id if edge already exists / invalid
    */
    //        dm.addEdge(edge_id, start_node, end_node);
  }

  public String updateStartEdge(String edge_id, String start_node) {
    if (md.isNode(start_node)){
      md.updateEdgeStart(edge_id, start_node);
      mm.updateEdgeStart(edge_id, start_node);
      return "";
    }
    else {
      return edge_id;
    }
    /*
    Check if valid edge_id
    Return "" is a success
    Return edge_id if edge already exists / invalid
     */
  }

  public String updateEndEdge(String edge_id, String end_node) {
    if (md.isNode(end_node)){
      md.updateEdgeEnd(edge_id, end_node);
      mm.updateEdgeEnd(edge_id, end_node);
      return "";
    }
    else {
      return edge_id;
    }
    /*
    Check if valid edge_id
    Return "" is a success
    Return edge_id if edge already exists / invalid
     */
  }

  public String deleteEdge(String edge_id) {
    if (md.isEdge(edge_id)){
      md.deleteEdge(edge_id);
      mm.deleteEdge(edge_id);
      return "";
    }
    else {
      return edge_id;
    }

    /*
    Check if valid edge_id
    Return "" is a success
    Return edge_id if edge already exists / invalid
     */
    //        dm.delEdge(edge_id);
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

  public LinkedList<Node> aStar(String start_node_id, String end_node_id) throws PathNotFoundException {
    if (md.isNode(start_node_id) && md.isNode(end_node_id)){
      return mm.runAStar(start_node_id, end_node_id);
    }
    else {
      return null;
    }
    /*
    Reutrn the A* path given the start and end node ids
    Return LinkedList of Nodes if path is found
    Return Return null if either id is invalid or path is not found
     */
  }
}
