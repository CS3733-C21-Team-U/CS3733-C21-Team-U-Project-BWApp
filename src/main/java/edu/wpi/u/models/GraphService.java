package edu.wpi.u.models;

import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import java.util.ArrayList;
import java.util.LinkedList;

public class GraphService {

  public GraphService() {
    initializeGraph();
  }

  private void initializeGraph() {
    /*
    Read from database, and make Graph in graphManager
     */
  }

  /*
  Make sure x & y are positive integers within the map coordinate range
  */
  public String addNode(String node_id, int x, int y) {
    if (validateNode(node_id)) {
      //            dm.addNode(node_id, x, y, 1, "Test", "Long", "short");
      return "";
    } else {
      return node_id;
    }
    /*
    Check if valid node_id
    Return "" is a success
    Return node_id if node already exists / invalid
     */
  }

  public String updateNode(String node_id, int x, int y) {
    //        dm.updCoords(node_id, x, y);
    /*
    Check if valid node_id
    Return "" is a success
    Return node_id if node already exists / invalid
     */
    return "";
  }

  public String deleteNode(String node_id) {
    /*
    Check if valid node_id
    Return "" is a success
    Return node_id if node already exists / invalid
     */
    //        dm.delNode(node_id);
    return "";
  }

  public String addEdge(String edge_id, String start_node, String end_node) {
    /*
    Check if valid edge_id
    Return "" is a success
    Return edge_id if edge already exists / invalid
    */
    //        dm.addEdge(edge_id, start_node, end_node);
    return "";
  }

  public String updateEdge(String edge_id, String start_node, String end_node) {
    /*
    Check if valid edge_id
    Return "" is a success
    Return edge_id if edge already exists / invalid
     */
    //        dm.updEdgeStart(edge_id, start_node);
    //        dm.updEdgeEnd(edge_id, end_node);
    return "";
  }

  public String deleteEdge(String edge_id) {
    /*
    Check if valid edge_id
    Return "" is a success
    Return edge_id if edge already exists / invalid
     */
    //        dm.delEdge(edge_id);
    return "";
  }

  public ArrayList<Node> getNodes() {
    /*
    Returns an ArrayList of all Node Objects in the graph
     */
    return null;
  }

  public ArrayList<Edge> getEdges() {
    /*
    Returns an ArrayList of all Edge Objects in the graph
     */
    Node A = new Node("A", 0, 0);
    Node B = new Node("B", 1, 0);
    Node C = new Node("C", 2, 0);

    ArrayList<Edge> returnList = new ArrayList<Edge>();

    Edge One = new Edge("One", A, B);
    Edge Two = new Edge("Two", B, C);
    Edge Three = new Edge("Three", A, C);

    returnList.add(One);
    returnList.add(Two);
    returnList.add(Three);

    return returnList;
  }

  public LinkedList<Node> aStar(String start_node_id, String end_node_id) {
    /*
    Reutrn the A* path given the start and end node ids
    Return LinkedList of Nodes if path is found
    Return Return null if either id is invalid or path is not found
     */
    // gm.aStar(start_node_id, end_node_id);
    return null;
  }

  private boolean validateNode(String node_id) {
    // db.validate(node_id);
    return true;
  }

  private boolean validateEdge(String edge_id) {
    // db.validate(edge_id);
    return true;
  }
}
