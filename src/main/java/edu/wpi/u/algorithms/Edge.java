package edu.wpi.u.algorithms;

public class Edge {
  private String edgeID; // should be private
  private Node startNode;
  private Node endNode;
  private double weight;

  public Edge(String _edgeID, Node _startNode, Node _endNode) {
    this.edgeID = _edgeID;
    this.startNode = _startNode;
    this.endNode = _endNode;
    this.weight = calcWeight(_startNode, _endNode);
    _startNode.addEdge(this); // links this edge to beginning node
    _endNode.addEdge(this);
    _startNode.addAdjNode(_endNode);
    _endNode.addAdjNode(_startNode);
  }

  private double calcWeight(Node _startNode, Node _endNode) {
    // calculate the rise and run
    double[][] nodeLocation = {_startNode.getCords(), _endNode.getCords()};
    double dx = Math.abs(nodeLocation[0][0] - nodeLocation[1][0]);
    double dy = Math.abs(nodeLocation[0][1] - nodeLocation[1][1]);
    // calculate the distance then
    // return the distance (weight)
    return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
  }

  public Node getStartNode() {
    return this.startNode;
  }

  public Node getEndNode() {
    return this.endNode;
  }

  public String getEdgeID() {
    return this.edgeID;
  }


}
