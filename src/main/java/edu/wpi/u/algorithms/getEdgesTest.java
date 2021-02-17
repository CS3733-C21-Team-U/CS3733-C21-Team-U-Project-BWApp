package edu.wpi.u.algorithms;

import java.util.LinkedList;

public class getEdgesTest {

  public static LinkedList<Edge> EdgesFollowed(LinkedList<Node> AStarOutput) {
    LinkedList<Edge> output = new LinkedList<>();
    int length = AStarOutput.size();

    for (int i = 0; i < length - 1; i++) { // for every node in AStarOutput
      Node n1 = AStarOutput.get(i); // Start node of edge
      Node n2 = AStarOutput.get(i + 1); // finish node of edge

      for (Edge e : n1.getEdges()) { // for every edge starting at node n1
        if ((e.getEndNode() == n2 && e.getStartNode() == n1)
            || (e.getEndNode() == n1 && e.getStartNode() == n2)) { // if the end node is n2
          output.add(e); // add it to the output list
        }
      }
    }
    return output;
  }

  public static void main(String[] args) {
    GraphManager graph = new GraphManager();
    Node A = new Node("A", 0, 0);
    Node B = new Node("B", 1, 0);
    Node D = new Node("D", 2, 1);

    graph.makeEdge("1", "A", "B");
    graph.makeEdge("9", "B", "D");

    LinkedList<Node> exampleOutput = new LinkedList<>();
    exampleOutput.add(D);
    exampleOutput.add(B);
    exampleOutput.add(A);

    LinkedList<Edge> result = EdgesFollowed(exampleOutput);
    for (Edge e : result) {
      System.out.println(e.getEdgeID());
    }
  }
}
