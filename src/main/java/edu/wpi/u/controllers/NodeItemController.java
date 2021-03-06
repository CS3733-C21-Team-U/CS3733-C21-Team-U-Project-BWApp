package edu.wpi.u.controllers;

import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class NodeItemController {

    @FXML public AnchorPane nodeAnchor;
    @FXML public Label nodeID;
    @FXML public Label nodeLocation;
    @FXML public Label nodeAdj;
    @FXML public Button expandButton;
    @FXML public Button collapseButton;
    @FXML public VBox extendedInfo;

    public String x;
    public String y;

    @FXML
    public void handleNodeExpandButton() {
        nodeAnchor.setPrefHeight(300);
        extendedInfo.setVisible(true);
        expandButton.setVisible(false);
    }

    @FXML
    public void handleNodeCollapseButton() {
        nodeAnchor.setPrefHeight(100);
        extendedInfo.setVisible(false);
        expandButton.setVisible(true);
    }

    @FXML
    public void handleNodeDeleteButton() {
        App.mapService.deleteNode(nodeID.getText());
        nodeAnchor.setPrefHeight(0);
        nodeAnchor.setVisible(false);
        //could be consider 'sloppy delete' on UI side until AdminTool is reloaded
    }

    @FXML
    public void handleNodeModifyButton() {
        App.lastSelectedNode = nodeID.getText();
        App.nodeField1 = x;
        App.nodeField2 = y;
        //TODO: Make better way of doing this
//        FXMLLoader windowLoader = new FXMLLoader(getClass().getResource("../views/ModifyNode.fxml"));
//        ModifyNodeController controller = windowLoader.getController();
//        controller.modifyNodeID.setText(nodeID.getText());

        App.rightDrawerRoot.set("/edu/wpi/u/views/Oldfxml/ModifyNode.fxml");
    }

}
