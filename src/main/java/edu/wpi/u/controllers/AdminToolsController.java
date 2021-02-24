package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleNode;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.*;
import edu.wpi.u.models.AdminToolStorage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class AdminToolsController {
    @FXML public VBox nodeList;
    @FXML public VBox test;

    @FXML public JFXButton editNodeButton;
    @FXML public JFXButton editEdgeButton;
    @FXML public JFXButton newNodeButton;
    @FXML public JFXButton newEdgeButton;

    public void initialize() throws IOException {

        //Nodes
        ArrayList<Node> nodes = App.graphService.getNodes();
        ArrayList<Edge> edges = App.graphService.getEdges();

        for (int i = 0; i < nodes.size(); i++) {
            Node currentNodeInfo = nodes.get(i);
            FXMLLoader nodeLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NodeListItem.fxml"));
            AnchorPane node = nodeLoader.load();
            NodeItemController controller = nodeLoader.getController();
            controller.nodeID.setText(currentNodeInfo.getNodeID());
            double XPos = (nodes.get(i).getCords()[0]);
            double YPos = (nodes.get(i).getCords()[1]);
            controller.nodeLocation.setText("(" + Double.toString(XPos) + ", " + Double.toString(YPos) + ")");
            StringBuilder string = new StringBuilder("Adj Nodes: ");
            Iterator<Node> it = currentNodeInfo.getAdjNodes().descendingIterator();
            while (it.hasNext()) {
                string.append(it.next().getNodeID()+", ");
            }
            String label = String.valueOf(string);
            if(label.length() > 0) {
                label = label.substring(0, label.length()-2);
                controller.nodeAdj.setText(label);
            }else{
                controller.nodeAdj.setText("No Adjacent Nodes");
            }
            nodeList.getChildren().add(node);
        }

        //Edges
        for (int i = 0; i < edges.size(); i++) {
            Edge currentEdgeInfo = edges.get(i);
            FXMLLoader edgeLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/EdgeListItem.fxml"));
            AnchorPane edge = edgeLoader.load();
            EdgeItemController controller = edgeLoader.getController();
            controller.edgeID.setText(currentEdgeInfo.getEdgeID());
            controller.startingNode.setText(currentEdgeInfo.getStartNode().getNodeID());
            controller.endingNode.setText(currentEdgeInfo.getEndNode().getNodeID());
            test.getChildren().add(edge);
        }

        App.AdminStorage.haveSelectedNode.addListener((observable, oldValue, newValue)  ->
        {
            if(AdminToolStorage.nodeIsSelected){
                editNodeButton.setVisible(true);
                newNodeButton.setVisible(false);
            }
        });

    }



    public void handleNewNodeButton() {
        App.rightDrawerRoot.set( "/edu/wpi/u/views/NewNode.fxml");
    }

    public void handleNewEdgeButton() {
        App.rightDrawerRoot.set( "/edu/wpi/u/views/NewEdge.fxml");
    }

    public void handleEditNodeButton(){

    }
    public void handleEditEdgeButton(){

    }
}
