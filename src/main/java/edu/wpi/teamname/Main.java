package edu.wpi.teamname;

public class Main {

  public static void main(String[] args) {
    node A = new node(1, 0, 0);
    node B = new node(2, 2, 0);
    node C = new node(3, 2.5, 1.5);
    node D = new node(4, 1, 2.5);
    node E = new node(5, -0.5, 1.5);

    edge F = new edge(1, A, B);
    edge G = new edge(2, B, C);
    edge H = new edge(3, C, D);
    edge I = new edge(4, D, E);
    edge J = new edge(5, E, A);
    edge K = new edge(6, A, D);
    edge L = new edge(7, A, C);

    edge[] edges = {F, G, H, I, J, K, L};

    weightedGraph theGraph = new weightedGraph(edges);

    theGraph.printGraph();
    App.launch(App.class, args);
  }
}
