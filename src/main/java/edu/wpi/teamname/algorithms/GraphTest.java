package edu.wpi.teamname.algorithms;

public class GraphTest {

  public static void main(String[] args) {
    GraphService graph = new GraphService();
    Node A = new Node("A", 0, 0);
    Node B = new Node("B", 1, 0);
    Node C = new Node("C", 2, 0);
    Node D = new Node("D", 2, 1);
    Node E = new Node("E", 1, 30);
    Node F = new Node("F", 0, 1);

    graph.addNode(A);
    graph.addNode(B);
    graph.addNode(C);
    graph.addNode(D);
    graph.addNode(E);
    graph.addNode(F);

    graph.makeEdge("1", "A", "B");
    graph.makeEdge("2", "B", "C");
    graph.makeEdge("3", "D", "C");
    graph.makeEdge("4", "D", "E");
    graph.makeEdge("5", "E", "F");
    graph.makeEdge("6", "F", "A");
    graph.makeEdge("7", "F", "B");
    graph.makeEdge("8", "E", "A");
    graph.makeEdge("9", "B", "D");
    graph.makeEdge("10", "A", "D");
    graph.makeEdge("11", "F", "D");
    graph.makeEdge("12", "E", "C");

    graph.removeNode("B");
    graph.disableNode("F");

    graph.disableEdge("11");
    graph.disableEdge("4");
    graph.disableEdge("8");
    graph.enableEdge("8");
    graph.removeEdge("10");

    for (Edge e : graph.EdgesFollowed(graph.runAStar("A", "D"))) {
      System.out.print(e.getEdgeID());
    }
  }
}
