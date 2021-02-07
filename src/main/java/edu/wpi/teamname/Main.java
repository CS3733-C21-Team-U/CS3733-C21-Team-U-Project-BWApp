package edu.wpi.teamname;

public class Main {

  public static void makeEdge(int _edgeID, node _startNode, node _endNode) {
    new edge(_edgeID, _startNode, _endNode);
    new edge(-_edgeID, _endNode, _startNode);
  }

  public static void main(String[] args) {
    node A = new node(1, 0, 0);
    node B = new node(2, 2, 0);
    node C = new node(3, 2.5, 1.5);
    node D = new node(4, 1, 2.5);
    node E = new node(5, -0.5, 1.5);
    node F = new node(6, -1, 2);

    makeEdge(1, A, B);
    makeEdge(2, B, C);
    makeEdge(3, D, C);
    makeEdge(4, D, E);
    makeEdge(5, E, F);
    makeEdge(6, F, A);

    DFS search = new DFS(B); // starting node
    if (search.marked.get(F) != null) System.out.println("\nC is connected");
    else System.out.println("\nNOT connected");
    App.launch(App.class, args);
  }
}
