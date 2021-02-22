package edu.wpi.u.controllers;

import edu.wpi.u.App;
import edu.wpi.u.algorithms.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class ViewNodesController {
    @FXML
    public VBox nodeList;

    public void initialize() throws IOException {

//            //This is how you add title panes here
        ArrayList<Node> nodes = App.graphService.getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            Node currentNodeInfo = nodes.get(i);
            FXMLLoader nodeLoader = new FXMLLoader(getClass().getResource("../views/NodeListItem.fxml"));
            AnchorPane node = nodeLoader.load();
            NodeItemController controller = nodeLoader.getController();
            controller.nodeID.setText(currentNodeInfo.getNodeID());
            controller.nodeLocation.setText("(" + currentNodeInfo.getXString() + ", " + currentNodeInfo.getYString() + ")");
            //TODO: Node Adjacency List
            nodeList.getChildren().add(node);
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
