package edu.wpi.teamname;

public class DFS {
  private boolean[] marked; // marked[v] = is there an s-v path?
  private int count; // number of vertices connected to s

  public DFS(UndirectedGraph G, int s) {
    marked = new boolean[G.V()];
    validateVertex(s);
    dfs(G, s);
  }

  // depth first search from v
  private void dfs(UndirectedGraph G, int v) {
    count++;
    marked[v] = true;
    for (int w : G.adj(v)) {
      if (!marked[w]) {
        dfs(G, w);
      }
    }
  }

  public boolean marked(int v) {
    validateVertex(v);
    return marked[v];
  }

  public int count() {
    return count;
  }

  // throw an IllegalArgumentException unless {@code 0 <= v < V}
  private void validateVertex(int v) {
    int V = marked.length;
    if (v < 0 || v >= V)
      throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
  }

  public static void main(String[] args) {
    UndirectedGraph G = new UndirectedGraph(6);
    G.addEdge(0, 3);
    G.addEdge(0, 2);
    G.addEdge(1, 4);
    G.addEdge(2, 4);
    G.addEdge(4, 5);
    G.addEdge(2, 5);

    int source = 5;
    DFS search = new DFS(G, source);
    for (int v = 0; v < G.V(); v++) {
      if (search.marked(v)) System.out.print(v + " ");
    }

    System.out.println();
    if (search.count() != G.V()) System.out.println("NOT connected");
    else System.out.println("connected");
  }
}
