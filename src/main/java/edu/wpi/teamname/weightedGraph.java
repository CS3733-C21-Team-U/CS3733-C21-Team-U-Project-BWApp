package edu.wpi.teamname;

import java.util.LinkedList;

public class weightedGraph {
  int numEdges = 0;
  LinkedList<edge>[] adjacencyList;

  weightedGraph(edge edgeList[]) {
    this.numEdges = edgeList.length;
    adjacencyList = new LinkedList[this.numEdges];
    for (int i = 0; i < this.numEdges; i++) {
      this.adjacencyList[i] = new LinkedList<>();
    }
    for (int i = 0; i < numEdges; i++) {
      edge currentEdge = edgeList[i];
      for (int j = 0; j < numEdges; j++) {
        edge comparedEdge = edgeList[j];

        if (currentEdge.endNode == comparedEdge.startNode) { // if the edges are connected
          this.adjacencyList[currentEdge.endNode.nodeID].addFirst(comparedEdge);
        }
      }
    }
  }

  public void printGraph() {
    for (int i = 0; i < this.numEdges; i++) {
      LinkedList<edge> list = adjacencyList[i];
      for (int j = 0; j < list.size(); j++) {
        System.out.println(
            "Node[ID: "
                + i
                + "] is connected to Node[ID: "
                + list.get(j).endNode.nodeID
                + "] and is "
                + list.get(j).weight
                + " units");
      }
    }
  }

  public node[] adj(node _curNode){
    LinkedList<edge> list = adjacencyList[_curNode.nodeID];
    node[] returnMe = new node [list.size()];

    for(int i = 0; i  < list.size(); i++){
      returnMe[i] = list.get(i).endNode;
    }

    return  returnMe;
  }

  public node[] listAllNodes(){
    node[] returnMe = new node[];


  }

}
