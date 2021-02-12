package edu.wpi.teamname.algorithms;

public class GraphTest {

  public static void main(String[] args) {
    Graph graph = new Graph();
    node A = new node(1, 0, 0, graph);
    node B = new node(2, 1, 0, graph);
    node C = new node(3, 2, 0, graph);
    node D = new node(4, 2, 1, graph);
    node E = new node(5, 1, 30, graph);
    node F = new node(6, 0, 1, graph);

    // graph.makeEdge(1, A, B, graph);
    graph.makeEdge(2, B, C, graph);
    // graph.makeEdge(3, D, C, graph);
    graph.makeEdge(4, D, E, graph);
    graph.makeEdge(5, E, F, graph);
    graph.makeEdge(6, F, A, graph);
    graph.makeEdge(7, F, B, graph);
    graph.makeEdge(8, E, A, graph);
    graph.makeEdge(9, B, D, graph);

    AStar test = new AStar();
    test.run(A, D);

    //    DFS search = new DFS(B); // starting node
    //    if (search.marked.get(F) != null) System.out.println("\nC is connected");
    //    else System.out.println("\nNOT connected");
  }
}
