package edu.wpi.teamname.algorithms;

import java.util.LinkedList;

public class node {
  private int nodeID;
  private double xcoord;
  private double ycoord;
  private String building;
  private String nodeType;
  private String longName;
  private String shortName;
  private String teamAssigned;
  private LinkedList<edge> edges;
  private LinkedList<node> adjNodes;

  // full constructor
  public node(
      int _nodeID,
      double _xcoord,
      double _ycoord,
      String _building,
      String _nodeType,
      String _LongName,
      String _ShortName,
      String _teamAssigned) {
    this.nodeID = _nodeID;
    this.xcoord = _xcoord;
    this.ycoord = _ycoord;
    this.building = _building;
    this.nodeType = _nodeType;
    this.longName = _LongName;
    this.shortName = _ShortName;
    this.teamAssigned = _teamAssigned;
    this.edges = new LinkedList<>();
  }

  // simple constructor
  public node(int _nodeID, double _xcoord, double _ycoord) {
    this.nodeID = _nodeID;
    this.xcoord = _xcoord;
    this.ycoord = _ycoord;
    this.edges = new LinkedList<>();
    this.adjNodes = new LinkedList<>();
  }

  public double[] getCords() {
    double[] returnMe = {this.xcoord, this.ycoord};
    return returnMe;
  }

  public int getNodeID() {
    return this.nodeID;
  }

  public void addAdjNode(node _node) {
    this.adjNodes.add(_node);
  }

  public void addEdge(edge _edge) {
    this.edges.add(_edge);
  }

  public LinkedList<node> getAdjNodes() {
    return this.adjNodes;
  }

  public LinkedList<edge> getEdges() {
    return edges;
  }
}
