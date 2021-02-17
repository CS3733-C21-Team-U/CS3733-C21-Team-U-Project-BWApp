package edu.wpi.u.models;

import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.GraphManager;
import edu.wpi.u.algorithms.Node;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class GraphService {

  static GraphManager gm = new GraphManager();
  static DatabaseManager dm;


  public static void main (String[] args){

  }

  public GraphService() {

    System.out.println("Constrctor for Graph SERVICE");
//    dm.start();
//    dm.stop();
    dm = new DatabaseManager();
    dm.loadGraph(gm);
    /*
    Node A = new Node("A", 0, 0);
    Node B = new Node("B", 1, 0);
    Node C = new Node("C", 2, 0);
    Node D = new Node("D", 2, 1);
    Node E = new Node("E", 1, 30);
    Node F = new Node("F", 0, 1);

    gm.addNode(A);
    gm.addNode(B);
    gm.addNode(C);
    gm.addNode(D);
    gm.addNode(E);
    gm.addNode(F);

    gm.makeEdge("1", "A", "B");
    gm.makeEdge("2", "B", "C");
    gm.makeEdge("3", "D", "C");
    gm.makeEdge("4", "D", "E");
    gm.makeEdge("5", "E", "F");
    gm.makeEdge("6", "F", "A");
    gm.makeEdge("7", "F", "B");
    gm.makeEdge("8", "E", "A");
    gm.makeEdge("9", "B", "D");
    gm.makeEdge("10", "A", "D");
    gm.makeEdge("11", "F", "D");
    gm.makeEdge("12", "E", "C");

     */
  }
  /*
  Make sure x & y are positive integers within the map coordinate range
  */
  public String addNode(String node_id, int x, int y) {
    dm.addNode(node_id,x,y,0, "Default", "Default", "Default", "Default");
    gm.makeNode(node_id,x,y,0,"Default", "Default", "Default", "Default", "u");
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
