package edu.wpi.teamname.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Graph {
  private final Set<node> allNodes;
  private final LinkedList<edge> allEdges;

  public Graph() {
    allNodes = new HashSet<>();
    allEdges = new LinkedList<>();
  }

  public void makeEdge(int _edgeID, node _startNode, node _endNode) {
    edge realEdge = new edge(_edgeID, _startNode, _endNode);
    this.allEdges.addLast(realEdge);
    this.addNode(_startNode);
    this.addNode(_endNode);
  }

  public LinkedList<node> getNodes() {
    LinkedList<node> nodes = new LinkedList<>();
    for (node _node : allNodes) nodes.add(_node);

    return nodes;
  }

  public int getNumNodes() {
    return allNodes.size();
  }

  public LinkedList<edge> getEdges() {
    return allEdges;
  }

  public void addNode(node n) {
    allNodes.add(n);
  }

  public void removeNode(node n) {
    for (node adjNode : n.getAdjNodes()){
      adjNode.removeAdjNode(n);
    }
    allNodes.remove(n);
    allEdges.removeIf(e -> e.startNode == n || e.endNode == n);
  }

  public void addEdge(edge e) {}

  public void removeEdge(edge e) {
    allEdges.remove(e);
  }
}
