package edu.wpi.teamname.algorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class AStar {
  private Graph map;

  public AStar(Graph graph) { // initial AStar
    this.map = graph;
  }

  public void run(node _startNode, node _goalNode) {
    Set<node> queue = new HashSet<>();
    queue.add(_startNode);
    HashMap<node, node> cameFrom = new HashMap<>();
    HashMap<node, Double> cost = new HashMap<>();
    HashMap<node, Double> priority = new HashMap<>();
    cost.put(_startNode, 0.0);
    priority.put(_startNode, 0.0);
    cameFrom.put(_startNode, _startNode);

    node current = _startNode;
    double next_cost;

    while (!queue.isEmpty()) {
      current = getNextNode(queue, priority);

      if (current == _goalNode) {
        // found the end
        break;
      }

      for (node next : current.adjNodes()) {
        next_cost = cost.get(current) + distBetweenNodes(current, next);
        if (!cost.containsKey(next)) {
          cost.put(next, next_cost);
          priority.put(next, next_cost + distBetweenNodes(next, _goalNode));
          queue.add(next);
          cameFrom.put(next, current);
        } else if (cost.get(next) > next_cost) {
          cost.put(next, next_cost);
          priority.put(next, next_cost + distBetweenNodes(next, _goalNode));
          queue.add(next);
          cameFrom.put(next, current);
        }
      }
    } // end of while

    if (cameFrom.containsKey(_goalNode)) { // exited with a path
      System.out.print("[END] ");
      while (current != _startNode) {
        System.out.print("[Node " + current.nodeID + "] <- ");
        current = cameFrom.get(current);
      }
      System.out.println("[Node " + current.nodeID + "] [START]");
    }
  }

  private double distBetweenNodes(node n1, node n2) {
    // calculate the rise and run
    double dx = Math.abs(n1.xcoord - n2.xcoord);
    double dy = Math.abs(n1.ycoord - n2.ycoord);
    // return the distance
    return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
  }

  private node getNextNode(Set<node> _queue, HashMap<node, Double> priority) {
    node[] nodes = new node[_queue.size()];
    _queue.toArray(nodes);

    node returnNode = nodes[0];

    for (node curNode : nodes) {
      if (priority.get(returnNode) > priority.get(curNode)) {
        returnNode = curNode;
      }
    }
    _queue.remove(returnNode);
    return returnNode;
  }
}
