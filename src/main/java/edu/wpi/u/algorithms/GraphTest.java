package edu.wpi.u.algorithms;

import edu.wpi.u.exceptions.PathNotFoundException;
import edu.wpi.u.models.MapManager;

import java.util.ArrayList;

public class GraphTest {

  public static void main(String[] args) throws PathNotFoundException {
    MapManager graph = new MapManager();
    Node A = new Node("A", 0, 0);
    Node B = new Node("B", 1, 0);
    Node C = new Node("C", 2, 0);
    Node D = new Node("D", 2, 1);
    Node E = new Node("E", 1, 30);
    Node F = new Node("F", 0, 1);

    graph.addNodeObject(A);
    graph.addNodeObject(B);
    graph.addNodeObject(C);
    graph.addNodeObject(D);
    graph.addNodeObject(E);
    graph.addNodeObject(F);

    graph.addEdge("1", "A", "B");
    graph.addEdge("2", "B", "C");
    graph.addEdge("3", "D", "C");
    graph.addEdge("4", "D", "E");
    graph.addEdge("5", "E", "F");
    graph.addEdge("6", "F", "A");
    graph.addEdge("7", "F", "B");
    graph.addEdge("8", "E", "A");
    graph.addEdge("9", "B", "D");
    graph.addEdge("10", "A", "D");
    graph.addEdge("11", "F", "D");
    graph.addEdge("12", "E", "C");

    graph.deleteEdge("10");
    graph.deleteEdge("8");
//    graph.deleteEdge("11");
    /*
    graph.deleteNode("B");
    graph.disableNode("F");

    graph.disableEdge("11");
    graph.disableEdge("4");
    graph.disableEdge("8");
    graph.enableEdge("8");
    graph.deleteEdge("10");
    */

    ArrayList<Node> path = graph.runBFS("A","D");
    for(Node curNode: path){
      System.out.println(curNode.getNodeID());
    }
    System.out.println("==========================\nDepth");
    ArrayList<Node> path2 = graph.runDFS("A","D");
    for(Node curNode: path2){
      System.out.println(curNode.getNodeID());
    }
  }
}
