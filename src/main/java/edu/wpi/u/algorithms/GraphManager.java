package edu.wpi.u.algorithms;

import java.util.*;

public class GraphManager {
  private final HashMap<String, Node> allNodes;
  private final HashMap<String, Edge> allEdges;

  public GraphManager() {
    System.out.println("Constrctor for Graph Manager");
    allNodes = new HashMap<>();
    allEdges = new HashMap<>();
  }

  public void makeEdge(String _edgeID, String _startNodeID, String _endNodeID) {
    Node _startNode = this.allNodes.get(_startNodeID);
    Node _endNode = this.allNodes.get(_endNodeID);
    Edge realEdge = new Edge(_edgeID, _startNode, _endNode);
    this.allEdges.put(_edgeID, realEdge);
    this.allNodes.put(_startNode.getNodeID(), _startNode);
    this.allNodes.put(_endNode.getNodeID(), _endNode);
  }

  public void removeEdge(String edgeId) {
    Edge e = this.allEdges.get(edgeId);
    allEdges.remove(edgeId);
    e.getStartNode().removeEdge(e);
    e.getEndNode().removeEdge(e);
  }

  public void disableEdge(String edgeId) {
    Edge e = this.allEdges.get(edgeId);
    e.setWalkable(false);
  }

  public void enableEdge(String edgeID) {
    Edge e = this.allEdges.get(edgeID);
    e.setWalkable(true);
  }

  public void makeNode(
          String _nodeID,
          double _xcoord,
          double _ycoord,
          int floor,
          String _building,
          String _nodeType,
          String _LongName,
          String _ShortName,
          String _teamAssigned) {
    Node n =
            new Node(
                    _nodeID, _xcoord, _ycoord, floor, _building, _nodeType, _LongName, _ShortName, _teamAssigned);
    allNodes.put(n.getNodeID(), n);
  }

  public void addNode(Node n) {
    this.allNodes.put(n.getNodeID(), n);
  }

  public void removeNode(String nodeID) {
    Node n = this.allNodes.get(nodeID);

    LinkedList<String> edgeIDsToRemove = new LinkedList<>();
    // removes a node and all edges that make it up
    allNodes.remove(nodeID);
    for (Map.Entry<String, Edge> entry : this.allEdges.entrySet()) {
      if (entry.getValue().getStartNode().equals(n) || entry.getValue().getEndNode().equals(n)) {
        edgeIDsToRemove.add(entry.getValue().getEdgeID());
      }
    }
    for (String ID : edgeIDsToRemove) {
      removeEdge(ID);
    }
  }

  public void disableNode(String nodeID) {
    Node _node = this.allNodes.get(nodeID);
    _node.setWalkable(false);
  }

  public void enableNode(String nodeID) {
    Node _node = this.allNodes.get(nodeID);
    _node.setWalkable(true);
  }

  public void updateCoords(String node_id, int x, int y){
    this.allNodes.get(node_id).updateCords(x,y);
  }

  public LinkedList<Node> runAStar(String _startNodeID, String _goalNodeID) {
    Node _startNode = this.allNodes.get(_startNodeID);
    Node _goalNode = this.allNodes.get(_goalNodeID);

    Set<Node> reachableNodes = new HashSet<>(); // record the reachable but unvisited nodes
    reachableNodes.add(_startNode); // add the start node to the seen nodes
    HashMap<Node, Node> cameFrom =
            new HashMap<>(); // record what node leads to which for shortest path
    HashMap<Node, Double> cost =
            new HashMap<>(); // record the cost to get to each node from the start node
    HashMap<Node, Double> priority =
            new HashMap<>(); // priority the cost + euclidean distance to the goal

    // setup the start node in the data
    cost.put(_startNode, 0.0);
    priority.put(_startNode, 0.0);
    cameFrom.put(_startNode, _startNode);

    // create the variable used in A*
    Node current = _startNode;

    // while there is reachable nodes loop
    while (!reachableNodes.isEmpty()) {
      // get the next node
      current = getNextNode(reachableNodes, priority);

      if (current == _goalNode) {
        // found the end
        break;
      }
      // iterate through the linked list of adjacent nodes
      for (Node adjNode : current.getAdjNodes()) {
        // calculate the cost to get to get from the start to the adjacent node
        double nextNodeCost = cost.get(current) + distBetweenNodes(current, adjNode);
        // if the adjacent node has not been seen yet add it
        if (!cost.containsKey(adjNode)) {
          cost.put(adjNode, nextNodeCost);
          priority.put(adjNode, nextNodeCost + distBetweenNodes(adjNode, _goalNode));
          reachableNodes.add(adjNode);
          cameFrom.put(adjNode, current);
        }
        // if the cost to get to the adjacent node is less update its cost and add it back to the
        // reachable nodes
        else if (cost.get(adjNode) > nextNodeCost) { // this has to be in the else if because cost.containsKey can return
          // NULL
          cost.put(adjNode, nextNodeCost);
          priority.put(adjNode, nextNodeCost + distBetweenNodes(adjNode, _goalNode));
          reachableNodes.add(adjNode);
          cameFrom.put(adjNode, current);
        }
      }
    } // end of while
    LinkedList<Node> returnMe = new LinkedList<>();
    // print out the path if there is one
    if (cameFrom.containsKey(_goalNode)) { // if exited with a path
      while (current != _startNode) {
        returnMe.addFirst(current);
        current = cameFrom.get(current);
      }
      returnMe.addFirst(_startNode);
    }

    return returnMe;
  }

  // takes in two nodes and returns the distance between them
  private double distBetweenNodes(Node n1, Node n2) {
    // calculate the rise and run
    double[][] nodeLocation = {n1.getCords(), n2.getCords()};
    double dx = Math.abs(nodeLocation[0][0] - nodeLocation[1][0]);
    double dy = Math.abs(nodeLocation[0][1] - nodeLocation[1][1]);
    // return the distance
    return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
  }

  // returns the node with the lowest priority from the set of reachable nodes
  private Node getNextNode(Set<Node> _reachableNodes, HashMap<Node, Double> priority) {
    // convert the set of reachable nodes into an array
    Node[] nodes = new Node[_reachableNodes.size()];
    _reachableNodes.toArray(nodes);
    // initialize a first node for comparison
    Node returnNode = nodes[0];
    // iterate through the array and find the node with the lowest priority
    for (Node curNode : nodes) {
      if (priority.get(returnNode) > priority.get(curNode)) {
        returnNode = curNode;
      }
    }
    // take the node picked out of the set of reachable nodes
    _reachableNodes.remove(returnNode);
    // return the node
    return returnNode;
  }

  // Switch output from nodes to edges
  public LinkedList<Edge> EdgesFollowed(LinkedList<Node> AStarOutput) {
    LinkedList<Edge> output = new LinkedList<>();
    int length = AStarOutput.size();

    for (int i = 0; i < length - 1; i++) { // for every node in AStarOutput
      Node n1 = AStarOutput.get(i); // Start node of edge
      Node n2 = AStarOutput.get(i + 1); // finish node of edge

      for (Edge e : n1.getEdges()) { // for every edge starting at node n1
        if ((e.getEndNode() == n2 && e.getStartNode() == n1)
            || (e.getEndNode() == n1 && e.getStartNode() == n2)) { // if the end node is n2
          output.add(e); // add it to the output list
        }
      }
    }
    return output;
  }

  public void updateEdgeStart(String edge_id, String start_node) {
    Edge edge = this.allEdges.get(edge_id);
    String endNodeId = edge.getEndNode().getNodeID();
    this.removeEdge(edge_id);
    this.makeEdge(edge_id, start_node,endNodeId);
  }

  public void updateEdgeEnd(String edge_id, String end_node) {
    Edge edge = this.allEdges.get(edge_id);
    String startNodeId = edge.getStartNode().getNodeID();
    this.removeEdge(edge_id);
    this.makeEdge(edge_id, startNodeId,end_node);
  }

  public ArrayList<Node> getAllNodes() {
    Collection<Node> allValues = this.allNodes.values();
    ArrayList<Node> nodeArrayList = new ArrayList<>(allValues);
    return nodeArrayList;
  }

  public ArrayList<Edge> getAllEdges() {
    Collection<Edge> allValues = this.allEdges.values();
    ArrayList<Edge> edgeArrayList = new ArrayList<>(allValues);
    return edgeArrayList;
  }
}
