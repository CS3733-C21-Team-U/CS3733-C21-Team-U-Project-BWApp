/*-------------------------*/
/* DO NOT DELETE THIS TEST */
/*-------------------------*/

package edu.wpi.u;

import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.exceptions.PathNotFoundException;
import edu.wpi.u.models.MapManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class DefaultTest {


    @Test
    public void jacobNode() {
        Node N = new Node("Yo", 12, 14);

        assertEquals("Yo", N.getNodeID());
    }
    @Test
    public void tylerNode() {
        Node N = new Node("Hi", 12, 14);

        assertTrue(14== N.getCords()[1]);
    }

    @Test
    public void lilyGetEdgesTest() {

        Node A = new Node("A",0,0);
        Node B = new Node("B",1,1);
        Edge Z = new Edge("Z",A,B);
        LinkedList<Edge> test = A.getEdges();
        assertEquals(test.size(), 1);

    }

    static MapManager graph = new MapManager();

    private static MapManager ExampleGraph() {
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

        graph.deleteNode("B");
        graph.disableNode("F");

        graph.disableEdge("11");
        graph.disableEdge("4");
        graph.disableEdge("8");
        graph.enableEdge("8");
        graph.deleteEdge("10");

        return graph;
    }

    private boolean FindNodeID(LinkedList<Node> answer, String id) {
        for (Node n : answer) {
            if (n.getNodeID() == id) {
                return true;
            }
        }
        return false;
    }

    private boolean FindNodeID(ArrayList<Node> answer, String id) {
        for (Node n : answer) {
            if (n.getNodeID() == id) {
                return true;
            }
        }
        return false;
    }

    private boolean FindEdgeID(ArrayList<Edge> answer, String id) {
        for (Edge n : answer) {
            if (n.getEdgeID() == id) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void AStarNodeTest() throws PathNotFoundException {//Michael
        LinkedList<Node> answer = ExampleGraph().runAStar("A", "D");
        assertTrue(FindNodeID(answer, "A"));
        assertTrue(FindNodeID(answer, "D"));
        assertTrue(FindNodeID(answer, "E"));
        assertTrue(FindNodeID(answer, "C"));
    }

    @Test
    public void AStarEdgeTest() throws PathNotFoundException{//Michael
        LinkedList<Node> nodeP = ExampleGraph().runAStar("A", "D");
        LinkedList<Edge> answer = ExampleGraph().EdgesFollowed(nodeP);
        assertEquals(3, answer.size());
    }

    @Test
    public void allNodesTest(){//charlie
        ArrayList<Node> allNodes = ExampleGraph().getAllNodes();
        assertTrue(FindNodeID(allNodes, "A"));
        assertFalse(FindNodeID(allNodes, "B"));
        assertTrue(FindNodeID(allNodes, "C"));
        assertTrue(FindNodeID(allNodes, "D"));
        assertTrue(FindNodeID(allNodes, "E"));
        assertTrue(FindNodeID(allNodes, "F"));
    }
    @Test
    public void addNodeTest(){//Neville
        Node n = new Node("Test", 1, 5);
        ExampleGraph().addNodeObject(n);
        ArrayList<Node> nodes = ExampleGraph().getAllNodes();
        assertTrue(FindNodeID(nodes, "Test"));
    }
    @Test
    public void deleteNodeTest(){//Will
        Node n1 = new Node("TestID", 1, 5);
        ExampleGraph().addNodeObject(n1);
        ExampleGraph().deleteNode("TestID");
        ArrayList<Node> testList = ExampleGraph().getAllNodes();
        assertFalse(FindNodeID(testList,"TestID"));
    }

    @Test
    public void DeleteEdge(){//Ola
        MapManager gm = ExampleGraph();
        gm.deleteEdge("12");
        ArrayList<Edge> edges = gm.getAllEdges();
        assertFalse(FindEdgeID(edges, "12"));
    }

    @Test
    public void WalkableTest(){//nick
        Node tester = new Node("Test", 0, 0);
        tester.setWalkable(false);
        assertFalse(tester.walkable());
    }

    @Test
    public void WalkableEdge(){//Kohmei
        Node A = new Node("A", 0, 0);
        Node B = new Node("B", 0, 0);
        Edge e = new Edge("TEST", A, B);
        e.setWalkable(false);
        assertFalse(e.isWalkable());
    }

    @Test
    public void addEdgeTest(){//Kaamil
        MapManager gm = ExampleGraph();
        gm.addEdge("13","A","C");
        ArrayList<Edge> edges = gm.getAllEdges();
        assertTrue(FindEdgeID(edges, "13"));
    }
}
