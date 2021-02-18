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



}
