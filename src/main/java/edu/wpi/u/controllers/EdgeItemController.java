package edu.wpi.u.controllers;

import edu.wpi.u.App;
import edu.wpi.u.algorithms.Edge;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class EdgeItemController extends AnchorPane implements Initializable {
    
    @FXML public Label edgeID;
    @FXML public Label startingNode;
    @FXML public Label endingNode;
    @FXML public VBox extendedInfo;
    @FXML public Button expandButton;
    @FXML public Button collapseButton;

    private final Edge edge;
    public EdgeItemController(Edge edge) throws IOException {
        FXMLLoader edgeLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/EdgeListItem.fxml"));
        edgeLoader.setRoot(this);
        edgeLoader.setController(this);
        edgeLoader.load();
        this.edge = edge;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.edgeID.setText(this.edge.getEdgeID());
        this.startingNode.setText(this.edge.getStartNode().getNodeID());
        this.endingNode.setText(this.edge.getEndNode().getNodeID());
    }

    @FXML
    public void handleEdgeExpandButton() {
        this.setPrefHeight(300);
        extendedInfo.setVisible(true);
        expandButton.setVisible(false);
    }

    @FXML
    public void handleEdgeCollapseButton() {
        this.setPrefHeight(100);
        extendedInfo.setVisible(false);
        expandButton.setVisible(true);
    }

    @FXML
    public void handleEdgeDeleteButton() {
        App.graphService.deleteEdge(edgeID.getText());
        this.setPrefHeight(0);
        this.setVisible(false);
        //could be consider 'sloppy delete' on UI side until AdminTool is reloaded
    }

    @FXML
    public void handleEdgeModifyButton() {
        //TODO: Make better way of doing this
        App.lastSelectedEdge = edgeID.getText();
        App.edgeField1 = startingNode.getText();
        App.edgeField2 = endingNode.getText();

//        FXMLLoader windowLoader = new FXMLLoader(getClass().getResource("../views/ModifyNode.fxml"));
//        ModifyNodeController controller = windowLoader.getController();
//        controller.modifyNodeID.setText(nodeID.getText());

        App.rightDrawerRoot.set( "/edu/wpi/u/views/ModifyEdge.fxml");
    }

}