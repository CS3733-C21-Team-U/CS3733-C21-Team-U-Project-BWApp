package edu.wpi.teamname;

public class Main {

  public static void main(String[] args) {
    node A = new node(0, 0, 0);
    node B = new node(1, 2, 0);
    node C = new node(2, 2.5, 1.5);
    node D = new node(3, 1, 2.5);
    node E = new node(4, -0.5, 1.5);

    edge L = new edge(0, A, C);
    edge F = new edge(1, A, B);
    edge G = new edge(2, B, C);
    edge H = new edge(3, C, D);
    edge I = new edge(4, D, E);
    edge J = new edge(5, E, A);
    edge K = new edge(6, A, D);

    edge[] edges = {L, F, G, H, I, J, K};
    node[] nodes = {A, B, C, D, E};
    weightedGraph theGraph = new weightedGraph(nodes, edges);

    theGraph.print();
    App.launch(App.class, args);
  }
}
