package edu.wpi.teamname.algorithms;

import java.util.LinkedList;

public class Graph {
  private final LinkedList<node> allNodes;
  private final LinkedList<edge> allEdges;

  public Graph() {
    allNodes = new LinkedList<>();
    allEdges = new LinkedList<>();
  }

  public void makeEdge(int _edgeID, node _startNode, node _endNode, Graph graph) {
    edge realEdge = new edge(_edgeID, _startNode, _endNode, graph);
    new edge(-_edgeID, _endNode, _startNode, graph);
    allEdges.addLast(realEdge);
  }

  public LinkedList<node> getNodes() {
    return allNodes;
  }

  public int getNumNodes() {
    return allNodes.size();
  }

  public LinkedList<edge> getEdges() {
    return allEdges;
  }

  public void addNode(node n) {
    allNodes.addLast(n);
  }

  public void removeNode(node n) {
    allNodes.remove(n);
    allEdges.removeIf(e -> e.startNode == n || e.endNode == n);
  }

  public void addEdge(edge e) {}

  public void removeEdge(edge e) {
    allEdges.remove(e);
  }
}
