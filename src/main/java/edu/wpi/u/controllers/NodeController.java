package edu.wpi.u.controllers;

import edu.wpi.u.App;
import edu.wpi.u.algorithms.*;
import edu.wpi.u.models.GraphService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.Observable;

public class NodeController {

  @FXML public TextField enterNodeID;

  @FXML public TextField enterXCoo;

  @FXML public TextField enterYCoo;

  @FXML public Label errorLabel;

  @FXML public Button editNodeButton;

  @FXML public Button deleteNodeButton;

  @FXML public TableView<Node> nodeTable;

  @FXML public TableColumn<Node, String> colNodeID;

  @FXML public TableColumn<Node, String> colXCoo;

  @FXML public TableColumn<Node, String> colYCoo;

  ObservableList<Node> list = FXCollections.observableArrayList();

  private ObservableList<Node> allNodes;

  public void initialize() {
    colNodeID.setCellValueFactory(new PropertyValueFactory<>("nodeID"));
    colXCoo.setCellValueFactory(new PropertyValueFactory<>("xcoord"));
    colYCoo.setCellValueFactory(new PropertyValueFactory<>("ycoord"));
    update();

    GraphService graphService = App.graphService;
    list.removeAll();
    list.addAll(graphService.getNodes());
    colNodeID.setCellValueFactory(new PropertyValueFactory<Node, String>("nodeID"));
    colXCoo.setCellValueFactory(
            cellData -> new SimpleStringProperty(cellData.getValue().getXString()));
    colYCoo.setCellValueFactory(
            cellData -> new SimpleStringProperty(cellData.getValue().getYString()));
    nodeTable.setItems(list);
  }

  public void update() {
//    if (App.graphService.getNodes().isEmpty()) {
//      editNodeButton.setDisable(true);
//      deleteNodeButton.setDisable(true);
//    } else {
//      editNodeButton.setDisable(false);
//      deleteNodeButton.setDisable(false);
//    }

    allNodes = FXCollections.observableList(App.graphService.getNodes());
    nodeTable.setItems(allNodes);
  }

  public void addNode() {

    String tempID = enterNodeID.getText();

    if (tempID.equals("")) {
      errorLabel.setText("Missing Node ID.");
      return;
    }
    if (checkTextBoxesErrorCoordinates()) return;

    int tempX = Integer.parseInt(enterXCoo.getText());
    int tempY = Integer.parseInt(enterYCoo.getText());

    String ret = App.graphService.addNode(tempID, tempX, tempY);
    if (ret.equals(tempID)) {
      errorLabel.setText("Node already exists");
      return;
    }

    update();
    errorLabel.setText("Node added successfully.");
  }

  public void editNode() {
    String tempID = enterNodeID.getText();
    if (tempID.equals("")) {
      errorLabel.setText("Missing Node ID.");
      return;
    }
    if (checkTextBoxesErrorCoordinatesEmpty()) return;
    if (checkTextBoxesErrorCoordinates()) return;
    int tempX = Integer.parseInt(enterXCoo.getText());
    int tempY = Integer.parseInt(enterYCoo.getText());
    if (App.graphService.updateNode(tempID, tempX, tempY).equals(tempID))
      errorLabel.setText("Node does not exists.");
    else {
      update();
      errorLabel.setText("node edited successfully.");
    }
  }

  public void deleteNode() {
    String tempID = enterNodeID.getText();
    if (tempID.equals("")) errorLabel.setText("Missing Node ID.");
    else {
      if (App.graphService.deleteNode(tempID).equals(tempID))
        errorLabel.setText("Node does not exists.");
      else {
        update();
        errorLabel.setText("node deleted successfully.");
      }
    }
  }

  private boolean checkTextBoxesErrorCoordinates() {
    try {
      Integer.parseInt(enterXCoo.getText());
    } catch (NumberFormatException e) {
      errorLabel.setText("x-coordinate is not a valid number");
      return true;
    }
    try {
      Integer.parseInt(enterYCoo.getText());
    } catch (NumberFormatException e) {
      errorLabel.setText("y-coordinate is not a valid number");
      return true;
    }

    return false;
  }

  private boolean checkTextBoxesErrorCoordinatesEmpty() {
    if (enterXCoo.getText().equals("")) {
      errorLabel.setText("Missing x-coordinate.");
      return true;
    }
    if (enterYCoo.getText().equals("")) {
      errorLabel.setText("Missing y-coordinate.");
      return true;
    }
    return false;
  }



  @FXML
  public void buttonPressMain() throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/MainPage.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }

  @FXML
  public void buttonPressEdge() throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/EditEdgeNick.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }

  @FXML
  public void buttonPressAS() throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/AStarTyler.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }

  @FXML
  public void displaySelected(MouseEvent mouseEvent) {
    Node node = nodeTable.getSelectionModel().getSelectedItem();
    if (node != null) {
      enterNodeID.setText(node.getNodeID());
      enterXCoo.setText(node.getXString());
      enterYCoo.setText(node.getYString());
      editNodeButton.setDisable(false);
      deleteNodeButton.setDisable(false);
    }
  }

}
