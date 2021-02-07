package edu.wpi.teamname;

import java.util.LinkedList;

public class UndirectedGraph {
  private static final String NEWLINE = System.getProperty("line.separator");
  private final int V;
  private int E;
  private LinkedList<Integer>[] adj;

  public UndirectedGraph(int V) {
    if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
    this.V = V;
    this.E = 0;
    adj = (LinkedList<Integer>[]) new LinkedList[V];
    for (int v = 0; v < V; v++) {
      adj[v] = new LinkedList<Integer>();
    }
  }

  public int V() {
    return V;
  }

  public int E() {
    return E;
  }

  private void validateVertex(int v) {
    if (v < 0 || v >= V)
      throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
  }

  public void addEdge(int v, int w) {
    validateVertex(v);
    validateVertex(w);
    E++;
    adj[v].add(w);
    adj[w].add(v);
  }

  public Iterable<Integer> adj(int v) {
    validateVertex(v);
    return adj[v];
  }

  public int degree(int v) {
    validateVertex(v);
    return adj[v].size();
  }

  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(V + " vertices, " + E + " edges " + NEWLINE);
    for (int v = 0; v < V; v++) {
      s.append(v + ": ");
      for (int w : adj[v]) {
        s.append(w + " ");
      }
      s.append(NEWLINE);
    }
    return s.toString();
  }

  public static void main(String[] args) {
    UndirectedGraph g = new UndirectedGraph(6);
    g.addEdge(0, 3);
    g.addEdge(1, 4);
    g.addEdge(2, 4);
    g.addEdge(4, 5);
    g.addEdge(2, 5);

    System.out.println(g);
  }
}
