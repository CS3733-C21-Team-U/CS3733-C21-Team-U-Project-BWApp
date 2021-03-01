package edu.wpi.u.controllers.OldControllers;

import edu.wpi.u.App;
import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.models.MapService;
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

  @FXML public Label errorLabel;

  ObservableList<Edge> list = FXCollections.observableArrayList();

  public void initialize() {
    MapService mapService = App.mapService;
    list.removeAll();
    list.addAll(mapService.getEdges());
    edgeIdCol.setCellValueFactory(new PropertyValueFactory<Edge, String>("edgeID"));
    startNodeCol.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getStartNode().getNodeID()));
    endNodeCol.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getEndNode().getNodeID()));
    edgeTable.setItems(list);
  }

  @FXML
  public void addEdge(ActionEvent actionEvent) {
    String tempEdgeID = edgeId.getText();
    String tempStartID = startNode.getText();
    String tempEndID = endNode.getText();



//    if(tempEdgeID.trim().equals("") || tempStartID.trim().equals("") || tempEndID.trim().equals("")){
//      errorLabel.setText("Missing Fields");
//      System.out.println("Adding edge attempt");
//      return;
//    }
//
//    if(App.graphService.addEdge(tempEdgeID, tempStartID, tempEndID).equals(tempEdgeID)){
//      errorLabel.setText("At least one node may not exist");
//      return;
//    }
//    else{
//      errorLabel.setText("Edge addition successful");
//      list = FXCollections.observableList(App.graphService.getEdges());
//      edgeTable.setItems(list);
//    }
  }

  @FXML
  // check while database
  public void deleteEdge(ActionEvent actionEvent) {
    String tempEdgeID = edgeId.getText();

    if(tempEdgeID.trim().equals("")){
      errorLabel.setText("Missing Field");
      return;
    }
    else if(App.mapService.deleteEdge(tempEdgeID).equals(tempEdgeID)){
      errorLabel.setText("This is edge ID is invalid");
      return;
    }
    else{
      errorLabel.setText("Edge deletion successful");

      //"update"
      list = FXCollections.observableList(App.mapService.getEdges());
      edgeTable.setItems(list);
    }
  }

  @FXML
  // check while database
  public void editEdge(ActionEvent actionEvent) {
    String tempEdgeID = edgeId.getText();
    String tempStartID = startNode.getText();
    String tempEndID = endNode.getText();

//    //Delete the node first
//    if(tempEdgeID.trim().equals("")){
//      errorLabel.setText("Missing Field");
//      return;
//    }
//    else if(App.graphService.deleteEdge(tempEdgeID).equals(tempEdgeID)){
//      errorLabel.setText("This is edge ID is invalid");
//      return;
//    }
//    else{
//      errorLabel.setText("Edge deletion successful");
//
//      //"update"
//      list = FXCollections.observableList(App.graphService.getEdges());
//      edgeTable.setItems(list);
//    }
//    //Done with delete
//    //Now we add a new node
//    if(tempEdgeID.trim().equals("") || tempStartID.trim().equals("") || tempEndID.trim().equals("")){
//      errorLabel.setText("Missing Fields");
//      System.out.println("Adding edge attempt");
//      return;
//    }
//
//    if(App.graphService.addEdge(tempEdgeID, tempStartID, tempEndID).equals(tempEdgeID)){
//      errorLabel.setText("At least one node may not exist");
//      return;
//    }
//    else{
//      errorLabel.setText("Edge addition successful");
//      list = FXCollections.observableList(App.graphService.getEdges());
//      edgeTable.setItems(list);
//    }
//    errorLabel.setText("Edge update successful");
  }

  @FXML
  public void buttonPressMain() throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/Oldfxml/MainPage.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }

  @FXML
  public void buttonPressNode() throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/Oldfxml/EditNodeLily.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }

  @FXML
  public void buttonPressAS() throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/Oldfxml/AStarTyler.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }

  @FXML
  public void loadData() {
    MapService mapService = App.mapService;
    list.removeAll(list);
//    list.getItems().addAll(graphService.getEdges());
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
