package edu.wpi.teamname;

public class AStar {

  public AStar(Graph graph, node goal) { // initial AStar

    for (node n : graph.getNodes()) { // sets optimistic distance from any node to the goal
      n.setDistToGoal(goal);
    }

    System.out.println(this.ToString(graph)); // Test print showing distToGoal worked
  }

  private String ToString(Graph graph) {
    String NEWLINE = System.getProperty("line.separator");
    StringBuilder s = new StringBuilder();
    int N = graph.getNodes().size();
    s.append(N + " nodes and their distance to the goal:" + NEWLINE);
    for (int v = 0; v < N; v++) {
      s.append("node " + v + ": " + graph.getNodes().get(v).distanceToGoal);
      s.append(NEWLINE);
    }
    return s.toString();
  }
}
