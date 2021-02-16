package edu.wpi.u.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class GraphManager {
  private final Set<Node> allNodes;
  private final LinkedList<Edge> allEdges;

  public GraphManager() {
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

  public Node getNodeById(String nodeID){
    for (Node node: allNodes ){
      if (node.getNodeID().equals(nodeID)){
        return node;
      }
    }
    return null;
  }

  public Edge getEdgeByNodes(Node startNode, Node endNode){
    for (Edge edge : allEdges){
      if(edge.getStartNode().equals(startNode) && edge.getEndNode().equals(endNode)){
        return edge;
      }
    }
    return null;
  }

  public Edge updateEdge(String _edgeID, Node _startNode, Node _endNode) {
    Edge realEdge = new Edge(_edgeID, _startNode, _endNode);
    for (Edge edge : allEdges){
      if(edge.getEdgeID().equals(_edgeID)){
        allEdges.remove(edge);
        allEdges.add(realEdge);
        return realEdge;
      }
    }
   return null;
  }
}
