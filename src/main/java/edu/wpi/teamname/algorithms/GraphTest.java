package edu.wpi.teamname.algorithms;

public class GraphTest {

  public static void makeEdge(int _edgeID, node _startNode, node _endNode, Graph graph) {
    new edge(_edgeID, _startNode, _endNode, graph);
    new edge(-_edgeID, _endNode, _startNode, graph);
  }

  public static void main(String[] args) {
    Graph graph = new Graph();
    node A = new node(1, 0, 0, graph);
    node B = new node(2, 1, 0, graph);
    node C = new node(3, 2, 0, graph);
    node D = new node(4, 2, 1, graph);
    node E = new node(5, 1, 30, graph);
    node F = new node(6, 0, 1, graph);

    // makeEdge(1, A, B, graph);
    makeEdge(2, B, C, graph);
    // makeEdge(3, D, C, graph);
    makeEdge(4, D, E, graph);
    makeEdge(5, E, F, graph);
    makeEdge(6, F, A, graph);
    makeEdge(7, F, B, graph);
    makeEdge(8, A, E, graph);
    makeEdge(9, B, D, graph);

    AStar test = new AStar(graph);
    test.run(A, D);

    //    DFS search = new DFS(B); // starting node
    //    if (search.marked.get(F) != null) System.out.println("\nC is connected");
    //    else System.out.println("\nNOT connected");
  }
}
