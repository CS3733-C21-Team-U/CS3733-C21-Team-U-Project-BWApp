/*-------------------------*/
/* DO NOT DELETE THIS TEST */
/*-------------------------*/

package edu.wpi.u;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.models.GraphService;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

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
    public void GraphServiceAddsNode() {
        //Tyler Sanderville: Checks for amount of nodes within GraphService object gs
        GraphService gs = new GraphService();
        gs.addNode("A",0,0);
        ArrayList<Node> test = gs.getNodes();
        assertEquals(test.size(), 1);
    }

    @Test
    public void AStarListsNodesPassedThrough() {
        //Tyler Sanderville: Checks for amount of nodes within GraphService object gs
        GraphService gs = new GraphService();
        gs.addNode("A",0,0);
        ArrayList<Node> test = gs.getNodes();
        assertEquals(test.size(), 1);
    }

    @Test
    public void lilyGetEdgesTest() {

        Node A = new Node("A",0,0);
        Node B = new Node("B",1,1);
        Edge Z = new Edge("Z",A,B);
        LinkedList<Edge> test = A.getEdges();
        assertEquals(test.size(), 1);

    }

}
