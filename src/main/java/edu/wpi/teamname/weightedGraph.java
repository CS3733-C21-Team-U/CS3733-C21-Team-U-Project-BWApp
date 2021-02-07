package edu.wpi.teamname;

public class weightedGraph {
  int numEdges;
  int numNodes;
  double[][] adjacencyMatrix;

  weightedGraph(node[] nodeList, edge[] edgeList) {
    this.numNodes = nodeList.length;
    this.numEdges = edgeList.length;

    adjacencyMatrix = new double[numNodes][numNodes];

    for (int i = 0; i < numEdges; i++) {
      this.adjacencyMatrix[edgeList[i].startNode.nodeID][edgeList[i].endNode.nodeID] =
          edgeList[i].weight;
      this.adjacencyMatrix[edgeList[i].endNode.nodeID][edgeList[i].startNode.nodeID] =
          edgeList[i].weight;
    }
  }

  public void print() {
    for (int i = 0; i < this.numNodes; i++) {
      for (int j = 0; j < this.numNodes; j++) {
        double A = this.adjacencyMatrix[i][j] * 100;
        double B = Math.round(A);
        double C = B / 100;
        System.out.print(C + " ");
      }
      System.out.print("\n");
    }
  }
}
