package edu.wpi.teamname;

public class edge {
  public int edgeID;
  node startNode;
  node endNode;
  public double weight;

  public edge(int _edgeID, node _startNode, node _endNode, Graph g) {
    this.edgeID = _edgeID;
    this.startNode = _startNode;
    this.endNode = _endNode;
    this.weight = calcWeight(_startNode, _endNode);
    _startNode.edges.add(this); // links this edge to beginning node
    g.addEdge(this);
  }

  private double calcWeight(node _startNode, node _endNode) {
    // calculate the rise and run
    double dx = Math.abs(_startNode.xcoord - _endNode.xcoord);
    double dy = Math.abs(_startNode.ycoord - _endNode.ycoord);
    // calculate the distance
    double weight = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    // return the distance (weight)
    return weight;
  }
}
