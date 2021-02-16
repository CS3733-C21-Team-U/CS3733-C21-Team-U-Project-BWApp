package edu.wpi.u.algorithms;

import java.util.Hashtable;

public class DFS {
  public Hashtable<Node, Boolean>
      marked; // passes in node as key, returns true if node is marked, null otherwise

  public DFS(Node s) {
    marked = new Hashtable<>();
    dfs(s);
  }

  // depth first search from node s
  private void dfs(Node s) {
    marked.put(s, true); // marks current node as visited
    System.out.print(s.getNodeID() + " ");
    for (Edge w : s.getEdges()) { // for every edge beginning from node s (source)
      if (marked.get(w.getEndNode()) == null) { // if node has not been reached yet, search it
        dfs(w.getEndNode());
      }
    }
  }
}
