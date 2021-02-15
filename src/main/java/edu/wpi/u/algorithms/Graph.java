package edu.wpi.u.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Graph {
  private final Set<Node> allNodes;
  private final LinkedList<Edge> allEdges;

  public Graph() {
    allNodes = new HashSet<>();
    allEdges = new LinkedList<>();
  }

  public void makeEdge(String _edgeID, Node _startNode, Node _endNode) {
    Edge realEdge = new Edge(_edgeID, _startNode, _endNode);
    this.allEdges.addLast(realEdge);
    this.addNode(_startNode);
    this.addNode(_endNode);
  }

  public LinkedList<Node> getNodes() {
    LinkedList<Node> nodes = new LinkedList<>();
    for (Node _node : allNodes) nodes.add(_node);

    return nodes;
  }

  public int getNumNodes() {
    return allNodes.size();
  }

  public LinkedList<Edge> getEdges() {
    return allEdges;
  }

  public void addNode(Node n) {
    allNodes.add(n);
  }

  public void removeNode(Node n) {
    for (Node adjNode : n.getAdjNodes()) {
      adjNode.removeAdjNode(n);
    }
    allNodes.remove(n);
    allEdges.removeIf(e -> e.getStartNode() == n || e.getEndNode() == n);
  }

  public void addEdge(Edge e) {}

  public void removeEdge(Edge e) {
    allEdges.remove(e);
  }
}
