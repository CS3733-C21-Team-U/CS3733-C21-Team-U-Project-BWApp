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
  public void GraphServiceAddsNode() {
    //Tyler Sanderville: Checks for amount of nodes within GraphService object gs
    GraphService gs = new GraphService(true);
    gs.addNode("A",0,0);
    ArrayList<Node> test = gs.getNodes();
    assertEquals(test.size(), 1);
  }

  @Test
  public void AStarListsNodesPassedThrough() {
    //Tyler Sanderville: Checks for amount of nodes within GraphService object gs
    GraphService gs = new GraphService(true);
    gs.addNode("A",0,0);
    ArrayList<Node> test = gs.getNodes();
    assertEquals(test.size(), 1);
  }
}
