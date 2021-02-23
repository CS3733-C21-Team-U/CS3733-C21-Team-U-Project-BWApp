package edu.wpi.u.controllers;

import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class EdgeItemController {

    @FXML public AnchorPane edgeAnchor;
    @FXML public Label edgeID;
    @FXML public Label startingNode;
    @FXML public Label endingNode;
    @FXML public VBox extendedInfo;
    @FXML public Button expandButton;
    @FXML public Button collapseButton;

    @FXML
    public void handleEdgeExpandButton() {
        edgeAnchor.setPrefHeight(300);
        extendedInfo.setVisible(true);
        expandButton.setVisible(false);
    }

    @FXML
    public void handleEdgeCollapseButton() {
        edgeAnchor.setPrefHeight(100);
        extendedInfo.setVisible(false);
        expandButton.setVisible(true);
    }

    @FXML
    public void handleEdgeDeleteButton() {
        App.graphService.deleteEdge(edgeID.getText());
        edgeAnchor.setPrefHeight(0);
        edgeAnchor.setVisible(false);
        //could be consider 'sloppy delete' on UI side until AdminTool is reloaded
    }

    @FXML
    public void handleEdgeModifyButton() {
        //TODO: Make better way of doing this
//        FXMLLoader windowLoader = new FXMLLoader(getClass().getResource("../views/ModifyNode.fxml"));
//        ModifyNodeController controller = windowLoader.getController();
//        controller.modifyNodeID.setText(nodeID.getText());

        App.rightDrawerRoot.set( "../views/ModifyEdge.fxml");
    }
}
