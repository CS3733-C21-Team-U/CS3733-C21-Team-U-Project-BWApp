package edu.wpi.teamname.algorithms;

import java.util.Hashtable;

public class DFS {
  public Hashtable<node, Boolean>
      marked; // passes in node as key, returns true if node is marked, null otherwise

  public DFS(node s) {
    marked = new Hashtable<>();
    dfs(s);
  }

  // depth first search from node s
  private void dfs(node s) {
    marked.put(s, true); // marks current node as visited
    System.out.print(s.getNodeID() + " ");
    for (edge w : s.getEdges()) { // for every edge beginning from node s (source)
      if (marked.get(w.endNode) == null) { // if node has not been reached yet, search it
        dfs(w.endNode);
      }
    }
  }
}
