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

    @FXML
    public void handleNodeExpandButton() {
        nodeAnchor.setPrefHeight(300);
        extendedInfo.setVisible(true);
        expandButton.setVisible(false);
    }

    @FXML
    public void handleNodeCollapseButton() {
        nodeAnchor.setPrefHeight(50);
        extendedInfo.setVisible(false);
        expandButton.setVisible(true);
    }

    @FXML
    public void handleNodeCancelButton() {
        App.rightDrawerRoot.set( "../views/AdminTools.fxml");
    }

    @FXML
    public void handleNodeModifyButton() {
        App.rightDrawerRoot.set( "../views/AdminTools.fxml");
    }

}
