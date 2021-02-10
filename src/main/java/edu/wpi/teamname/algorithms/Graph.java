package edu.wpi.teamname.algorithms;

import java.util.LinkedList;

public class Graph {
  private final LinkedList<node> allNodes;
  private final LinkedList<edge> allEdges;

  public Graph() {
    allNodes = new LinkedList<>();
    allEdges = new LinkedList<>();
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

  public void addEdge(edge e) {
    allEdges.addLast(e);
  }

  public void removeEdge(edge e) {
    allEdges.remove(e);
  }
}
