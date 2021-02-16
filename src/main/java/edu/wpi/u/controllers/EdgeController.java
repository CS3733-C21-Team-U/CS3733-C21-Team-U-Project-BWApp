package edu.wpi.u.controllers;

import edu.wpi.u.App;
import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.models.GraphService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class EdgeController {

  ObservableList list = FXCollections.observableArrayList();

  public void initialize() {
    loadData();
  }

  @FXML public TextField edgeId;

  @FXML public TextField endNode;

  @FXML public TextField startNode;

  @FXML public ListView<Edge> edgeList;

  @FXML public Button createButton;

  @FXML public Button deleteButton;

  @FXML public Button updateButton;

  @FXML
  public void addEdge(ActionEvent actionEvent) {}

  @FXML
  // check while database
  public void deleteEdge(ActionEvent actionEvent) {}

  @FXML
  // check while database
  public void editEdge(ActionEvent actionEvent) {}

  @FXML
  public void buttonPressMain() throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/MainPage.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }

  @FXML
  public void buttonPressNode() throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/EditNodeLily.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }

  @FXML
  public void buttonPressAS() throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/AStarTyler.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }

  @FXML
  public void loadData() {
    GraphService graphService = App.getInstance().graphService;
    list.removeAll(list);
    edgeList.getItems().addAll(graphService.getEdges());
  }

  @FXML
  public void displaySelected(MouseEvent mouseEvent) {
    Edge edge = edgeList.getSelectionModel().getSelectedItem();
    if (edge != null) {
      Node start = edge.getStartNode();
      Node end = edge.getEndNode();
      edgeId.setText(edge.getEdgeID());
      startNode.setText(start.getNodeID());
      endNode.setText(end.getNodeID());
    }
    deleteButton.setDisable(false);
    updateButton.setDisable(false);
  }
}
