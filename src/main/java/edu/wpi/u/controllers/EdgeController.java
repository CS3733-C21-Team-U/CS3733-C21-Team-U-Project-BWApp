package edu.wpi.u.controllers;

import edu.wpi.u.App;
import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.models.GraphService;
import edu.wpi.u.models.Graph;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class EdgeController {

  @FXML public TextField edgeId;

  @FXML public TextField endNode;

  @FXML public TextField startNode;

  @FXML public Button createButton;

  @FXML public Button deleteButton;

  @FXML public Button updateButton;

  @FXML public TableView<Edge> edgeTable;

  @FXML public TableColumn<Edge, String> edgeIdCol;

  @FXML public TableColumn<Edge, String> startNodeCol;

  @FXML public TableColumn<Edge, String> endNodeCol;

  ObservableList<Edge> list = FXCollections.observableArrayList();

  public void initialize() {
    GraphService graphService = App.getInstance().graphService;
    list.removeAll();
    list.addAll(graph.getEdges());
    edgeIdCol.setCellValueFactory(new PropertyValueFactory<Edge, String>("edgeID"));
    startNodeCol.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getStartNode().getNodeID()));
    endNodeCol.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getEndNode().getNodeID()));
    edgeTable.setItems(list);
  }

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
  public void displaySelected(MouseEvent mouseEvent) {
    Edge edge = edgeTable.getSelectionModel().getSelectedItem();
    if (edge != null) {
      Node start = edge.getStartNode();
      Node end = edge.getEndNode();
      edgeId.setText(edge.getEdgeID());
      startNode.setText(start.getNodeID());
      endNode.setText(end.getNodeID());
      deleteButton.setDisable(false);
      updateButton.setDisable(false);
    }
  }
}
