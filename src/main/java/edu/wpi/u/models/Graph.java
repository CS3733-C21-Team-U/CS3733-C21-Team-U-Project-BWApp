package edu.wpi.u.models;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {

  // GraphManager gm = new GraphManager();
  DatabaseService dm = new DatabaseService();
  Edge e = new Edge();
  Node n = new Node();

  public Graph() throws IOException, SQLException {
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
      dm.addNode(node_id, x, y, 1, "Test", "nodetype", "Long", "short");
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
    dm.updCoords(node_id, x, y);
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
    dm.delNode(node_id);
    return "";
  }

  public String deleteNode(int x, int y) {
    /*
    Check if valid node_id
    Return "" is a success
    Return node_id if node already exists / invalid
     */
    dm.delNodeCoord(x, y);
    return "";
  }

  public String addEdge(String edge_id, String start_node, String end_node) {
    /*
    Check if valid edge_id
    Return "" is a success
    Return edge_id if edge already exists / invalid
    */
    dm.addEdge(edge_id, start_node, end_node);
    return "";
  }

  public String updateEdge(String edge_id, String start_node, String end_node) {
    /*
    Check if valid edge_id
    Return "" is a success
    Return edge_id if edge already exists / invalid
     */
    dm.updEdgeStart(edge_id, start_node);
    dm.updEdgeEnd(edge_id, end_node);
    return "";
  }

  public String deleteEdge(String edge_id) {
    /*
    Check if valid edge_id
    Return "" is a success
    Return edge_id if edge already exists / invalid
     */
    dm.delEdge(edge_id);
    return "";
  }
  public String deleteEdgeByNodes(String start_node_id, String end_node_id) {
    /*
    Check if valid edge_id
    Return "" is a success
    Return edge_id if edge already exists / invalid
     */
    dm.delEdgeByNodes(start_node_id, end_node_id);
    return "";
  }

  public ArrayList<Node> getNodes() {
    return null;
  }

  public ArrayList<Edge> getEdges() {
    return null;
  }

  public LinkedList<Node> aStar(String start_node_id, String end_node_id) {
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
