package edu.wpi.teamname;

public class Main {

  public static void main(String[] args) {
    node A = new node(1, 0, 0);
    node B = new node(2, 2, 0);
    node C = new node(3, 2.5, 1.5);
    node D = new node(4, 1, 2.5);
    node E = new node(5, -0.5, 1.5);
    node F = new node(6, -1, 2);

    edge G = new edge(1, A, D);
    edge H = new edge(2, B, E);
    edge I = new edge(3, C, E);
    edge J = new edge(4, E, F);
    edge K = new edge(5, C, F);
    edge L = new edge(6, F, C);

    DFS search = new DFS(E); // starting node

    if (search.marked.get(C) != null) System.out.println("C is connected");
    else System.out.println("NOT connected");
    App.launch(App.class, args);
  }
}
