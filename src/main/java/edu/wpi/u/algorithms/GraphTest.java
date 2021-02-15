package edu.wpi.u.algorithms;

public class GraphTest {

  public static void main(String[] args) {
    Graph graph = new Graph();
    Node A = new Node("A", 0, 0);
    Node B = new Node("B", 1, 0);
    Node C = new Node("C", 2, 0);
    Node D = new Node("D", 2, 1);
    Node E = new Node("E", 1, 30);
    Node F = new Node("F", 0, 1);

    graph.makeEdge("1", A, B);
    graph.makeEdge("2", B, C);
    // graph.makeEdge("3", D, C);
    graph.makeEdge("4", D, E);
    graph.makeEdge("5", E, F);
    graph.makeEdge("6", F, A);
    graph.makeEdge("7", F, B);
    graph.makeEdge("8", E, A);
    graph.makeEdge("9", B, D);

    AStar test = new AStar();
    test.run(A, D);

    //    DFS search = new DFS(B); // starting node
    //    if (search.marked.get(F) != null) System.out.println("\nC is connected");
    //    else System.out.println("\nNOT connected");
  }
}
