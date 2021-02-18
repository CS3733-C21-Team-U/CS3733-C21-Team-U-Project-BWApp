/*-------------------------*/
/* DO NOT DELETE THIS TEST */
/*-------------------------*/

package edu.wpi.u;

import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.models.GraphService;
import org.assertj.core.internal.bytebuddy.dynamic.scaffold.MethodGraph;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class DefaultTest {

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
