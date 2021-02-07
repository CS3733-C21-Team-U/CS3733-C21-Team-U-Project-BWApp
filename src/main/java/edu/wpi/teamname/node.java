package edu.wpi.teamname;

import java.util.LinkedList;

public class node {
  int nodeID;
  public double xcoord;
  public double ycoord;
  String building;
  String nodeType;
  String longName;
  String shortName;
  String teamAssigned;
  LinkedList<edge> edges;

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
    this.edges = new LinkedList<edge>();
  }
  // simple constructor
  public node(int _nodeID, double _xcoord, double _ycoord) {
    this.nodeID = _nodeID;
    this.xcoord = _xcoord;
    this.ycoord = _ycoord;
    this.edges = new LinkedList<edge>();
  }
}
