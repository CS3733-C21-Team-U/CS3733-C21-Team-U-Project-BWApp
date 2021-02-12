package edu.wpi.teamname.algorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class AStar {

  public AStar() { // initial map for AStar
  }

  public void run(node _startNode, node _goalNode) {
    Set<node> reachableNodes = new HashSet<>(); // record the reachable but unvisited nodes
    reachableNodes.add(_startNode); // add the start node to the seen nodes
    HashMap<node, node> cameFrom =
        new HashMap<>(); // record what node leads to which for shortest path
    HashMap<node, Double> cost =
        new HashMap<>(); // record the cost to get to each node from the start node
    HashMap<node, Double> priority =
        new HashMap<>(); // priority the cost + euclidean distance to the goal

    // setup the start node in the data
    cost.put(_startNode, 0.0);
    priority.put(_startNode, 0.0);
    cameFrom.put(_startNode, _startNode);

    // create the variable used in A*
    node current = _startNode;

    // while there is reachable nodes loop
    while (!reachableNodes.isEmpty()) {
      // get the next node
      current = getNextNode(reachableNodes, priority);

      if (current == _goalNode) {
        // found the end
        break;
      }
      // iterate through the linked list of adjacent nodes
      for (node adjNode : current.getAdjNodes()) {
        // calculate the cost to get to get from the start to the adjacent node
        double nextNodeCost = cost.get(current) + distBetweenNodes(current, adjNode);
        // if the adjacent node has not been seen yet add it
        if (!cost.containsKey(adjNode)) {
          cost.put(adjNode, nextNodeCost);
          priority.put(adjNode, nextNodeCost + distBetweenNodes(adjNode, _goalNode));
          reachableNodes.add(adjNode);
          cameFrom.put(adjNode, current);
        }
        // if the cost to get to the adjacent node is less update its cost and add it back to the
        // reachable nodes
        else if (cost.get(adjNode)
            > nextNodeCost) { // this has to be in the else if because cost.containsKey can return
          // NULL
          cost.put(adjNode, nextNodeCost);
          priority.put(adjNode, nextNodeCost + distBetweenNodes(adjNode, _goalNode));
          reachableNodes.add(adjNode);
          cameFrom.put(adjNode, current);
        }
      }
    } // end of while

    // print out the path if there is one
    if (cameFrom.containsKey(_goalNode)) { // if exited with a path
      System.out.print("[END] ");
      while (current != _startNode) {
        System.out.print("[Node " + current.getNodeID() + "] <- ");
        current = cameFrom.get(current);
      }
      System.out.println("[Node " + current.getNodeID() + "] [START]");
    }
  }

  // takes in two nodes and returns the distance between them
  private double distBetweenNodes(node n1, node n2) {
    // calculate the rise and run
    double[][] nodeLocation = {n1.getCords(), n2.getCords()};
    double dx = Math.abs(nodeLocation[0][0] - nodeLocation[1][0]);
    double dy = Math.abs(nodeLocation[0][1] - nodeLocation[1][1]);
    // return the distance
    return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
  }

  // returns the node with the lowest priority from the set of reachable nodes
  private node getNextNode(Set<node> _reachableNodes, HashMap<node, Double> priority) {
    // convert the set of reachable nodes into an array
    node[] nodes = new node[_reachableNodes.size()];
    _reachableNodes.toArray(nodes);
    // initialize a first node for comparison
    node returnNode = nodes[0];
    // iterate through the array and find the node with the lowest priority
    for (node curNode : nodes) {
      if (priority.get(returnNode) > priority.get(curNode)) {
        returnNode = curNode;
      }
    }
    // take the node picked out of the set of reachable nodes
    _reachableNodes.remove(returnNode);
    // return the node
    return returnNode;
  }
}
