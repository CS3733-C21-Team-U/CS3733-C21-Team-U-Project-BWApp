package edu.wpi.u.controllers;

import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class NodeItemController extends AnchorPane implements Initializable {

  private final Node node;
    @FXML public Label nodeID;
    @FXML public Label nodeLocation;
    @FXML public Label nodeAdj;
    @FXML public Button expandButton;
    @FXML public Button collapseButton;
    @FXML public VBox extendedInfo;
    public String x;
    public String y;

  public NodeItemController(Node node) throws IOException {
    FXMLLoader nodeLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NodeListItem.fxml"));
    nodeLoader.setController(this);
    nodeLoader.setRoot(this);
    nodeLoader.load();
    this.node = node;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    nodeID.setText(node.getNodeID());
    nodeLocation.setText(String.format("(%f, %f)", node.getCords()[0], node.getCords()[1]));
    if (node.getAdjNodes()
        .size() == 0) {
      nodeAdj.setText("No Adjacent Nodes");
    } else {
      String adjNodes = "Adj Nodes: " + node.getAdjNodes()
          .stream()
          .map(Node::getNodeID)
          .collect(Collectors.joining(", "));
      nodeAdj.setText(adjNodes);
    }
  }

    @FXML
    public void handleNodeExpandButton() {
        this.setPrefHeight(300);
        extendedInfo.setVisible(true);
        expandButton.setVisible(false);
    }

    @FXML
    public void handleNodeCollapseButton() {
        this.setPrefHeight(100);
        extendedInfo.setVisible(false);
        expandButton.setVisible(true);
    }

    @FXML
    public void handleNodeDeleteButton() {
        App.graphService.deleteNode(nodeID.getText());
        this.setPrefHeight(0);
        this.setVisible(false);
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

        App.rightDrawerRoot.set( "/edu/wpi/u/views/ModifyNode.fxml");
    }


}
