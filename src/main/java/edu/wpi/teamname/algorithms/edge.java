package edu.wpi.teamname.algorithms;

public class edge {
  public int edgeID;
  node startNode;
  node endNode;
  public double weight;

  public edge(int _edgeID, node _startNode, node _endNode) {
    this.edgeID = _edgeID;
    this.startNode = _startNode;
    this.endNode = _endNode;
    this.weight = calcWeight(_startNode, _endNode);
    _startNode.addEdge(this); // links this edge to beginning node
    _startNode.addAdjNode(_endNode);
    _endNode.addAdjNode(_startNode);
  }

  private double calcWeight(node _startNode, node _endNode) {
    // calculate the rise and run
    double[][] nodeLocation = {_startNode.getCords(), _endNode.getCords()};
    double dx = Math.abs(nodeLocation[0][0] - nodeLocation[1][0]);
    double dy = Math.abs(nodeLocation[0][1] - nodeLocation[1][1]);
    // calculate the distance then
    // return the distance (weight)
    return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
  }
}
