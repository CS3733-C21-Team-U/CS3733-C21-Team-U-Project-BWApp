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

        for (Node node: nodes) {
            nodeList.getChildren().add(new NodeItemController(node));
        }

        for (Edge edge: edges) {
            test.getChildren().add(new EdgeItemController(edge));
        }

        AdminToolStorage.haveSelectedNode.addListener((observable, oldValue, newValue)  ->
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
