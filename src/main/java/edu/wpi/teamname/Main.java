package edu.wpi.teamname;

public class Main {

  public static void main(String[] args) {
    node A = new node(1, 0, 0);
    node B = new node(2, 2, 0);
    node C = new node(3, 2.5, 1.5);
    node D = new node(4, 1, 2.5);
    node E = new node(5, -0.5, 1.5);
    node F = new node(6, -1, 2);

    edge G = new edge(1, A, B);
    edge H = new edge(2, B, C);
    edge I = new edge(3, D, C);
    edge J = new edge(4, D, E);
    edge K = new edge(5, E, F);
    edge L = new edge(6, F, A);

    DFS search = new DFS(B); // starting node
    if (search.marked.get(F) != null) System.out.println("\nC is connected");
    else System.out.println("\nNOT connected");
    App.launch(App.class, args);
  }
}
