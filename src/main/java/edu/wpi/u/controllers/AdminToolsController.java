package edu.wpi.u.controllers;

import edu.wpi.u.App;
import edu.wpi.u.algorithms.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class AdminToolsController {
    @FXML public VBox nodeList;
    @FXML public VBox test;

    public void initialize() throws IOException {

        //Nodes
        ArrayList<Node> nodes = App.graphService.getNodes();
        ArrayList<Edge> edges = App.graphService.getEdges();

        for (int i = 0; i < nodes.size(); i++) {
            Node currentNodeInfo = nodes.get(i);
            FXMLLoader nodeLoader = new FXMLLoader(getClass().getResource("../views/NodeListItem.fxml"));
            AnchorPane node = nodeLoader.load();
            NodeItemController controller = nodeLoader.getController();
            controller.nodeID.setText(currentNodeInfo.getNodeID());
            controller.nodeLocation.setText("(" + currentNodeInfo.getXString() + ", " + currentNodeInfo.getYString() + ")");
            StringBuilder adjacencies = new StringBuilder();
            for(Node a : currentNodeInfo.getAdjNodes()) {
                adjacencies.append(a.getNodeID() + ", ");
            }
            controller.nodeAdj.setText(adjacencies.toString());
            nodeList.getChildren().add(node);
        }

        //Edges
        for (int i = 0; i < edges.size(); i++) {
            Edge currentEdgeInfo = edges.get(i);
            FXMLLoader edgeLoader = new FXMLLoader(getClass().getResource("../views/EdgeListItem.fxml"));
            AnchorPane node = edgeLoader.load();
            EdgeItemController controller = edgeLoader.getController();
            controller.edgeID.setText(currentEdgeInfo.getEdgeID());
            controller.startingNode.setText(currentEdgeInfo.getStartNode().getNodeID());
            controller.endingNode.setText(currentEdgeInfo.getEndNode().getNodeID());
            test.getChildren().add(node);
        }

    }

    @FXML
    public void handleChangeToEditRequest(){
        //Switch to a new right drawer
        App.rightDrawerRoot.set( "../views/EditRequest.fxml");

    }

    @FXML
    public void handleNewRequestButton() {
        App.rightDrawerRoot.set( "../views/NewRequest.fxml");
    }
}
