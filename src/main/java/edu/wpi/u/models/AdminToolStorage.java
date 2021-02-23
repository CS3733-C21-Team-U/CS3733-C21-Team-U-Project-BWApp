package edu.wpi.u.models;

import com.jfoenix.controls.JFXToggleNode;
import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.controllers.NodeItemController;
import javafx.beans.property.SimpleIntegerProperty;

public class AdminToolStorage {


    public static String id = "";
    public static int xloc = 0;
    public static int yloc = 0;

    public static NodeItemController selectedNode = new NodeItemController();
    public static SimpleIntegerProperty haveSelectedNode = new SimpleIntegerProperty(0);
    public static boolean nodeIsSelected = false;

    public Edge selectedEdge;


}
