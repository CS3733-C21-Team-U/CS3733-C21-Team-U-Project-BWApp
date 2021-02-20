package edu.wpi.u.models;

import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.database.DatabaseManager;

import java.util.ArrayList;
import java.util.LinkedList;

public class GraphService {

  static GraphManager gm = new GraphManager();
  static DatabaseManager dm;


  public static void main (String[] args){

  }

  public GraphService() {
    System.out.println("Constrctor for Graph SERVICE");
    dm = new DatabaseManager();
    dm.loadGraph(gm);
  }

  public void saveAndExitDB(){
    dm.stop();
  }


  /*
  Make sure x & y are positive integers within the map coordinate range
  */
  public String addNode(String node_id, int x, int y) {
    dm.addNode(node_id,x,y,0, "Def", "Def", "Def", "Def");
    gm.makeNode(node_id,x,y,0,"Def", "Def", "Def", "Def", "u");
    return "";
    /*
    Check if valid node_id
    Return "" is a success
    Return node_id if node already exists / invalid
     */
  }

  public String updateNode(String node_id, int x, int y) {
    if (dm.isNode(node_id)){
      dm.updCoords(node_id, x, y);
      gm.updateCoords(node_id, x, y);
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
    if (dm.isNode(node_id)){
      dm.delNode(node_id);
      gm.removeNode(node_id);
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

  public String addEdge(String edge_id, String start_node, String end_node) {
    if (dm.isNode(start_node) && dm.isNode(end_node)){
      dm.addEdge(edge_id, start_node, end_node);
      gm.makeEdge(edge_id, start_node, end_node);
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
    //        dm.addEdge(edge_id, start_node, end_node);
  }

  public String updateStartEdge(String edge_id, String start_node) {
    if (dm.isNode(start_node)){
      dm.updEdgeStart(edge_id, start_node);
      gm.updateEdgeStart(edge_id, start_node);
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
    if (dm.isNode(end_node)){
      dm.updEdgeEnd(edge_id, end_node);
      gm.updateEdgeEnd(edge_id, end_node);
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
    if (dm.isEdge(edge_id)){
      dm.delEdge(edge_id);
      gm.removeEdge(edge_id);
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
    return gm.getAllNodes();
    /*
    Returns an ArrayList of all Node Objects in the graph
     */
  }

  public ArrayList<Edge> getEdges() {
    return gm.getAllEdges();
    /*
    Returns an ArrayList of all Edge Objects in the graph
     */
  }

  public LinkedList<Node> aStar(String start_node_id, String end_node_id) {
    //if (dm.isNode(start_node_id) && dm.isNode(end_node_id)){
      return gm.runAStar(start_node_id, end_node_id);
    /*}
    else {
      return null;
    }*/
    /*
    Reutrn the A* path given the start and end node ids
    Return LinkedList of Nodes if path is found
    Return Return null if either id is invalid or path is not found
     */
  }
}
