package edu.wpi.u.controllers;

import edu.wpi.u.App;
import edu.wpi.u.models.Graph;
import java.util.LinkedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AStarController {

  Graph g = new Graph();

  @FXML public TextField startNode;
  @FXML public TextField endNode;
  @FXML public Label errorMessage;

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
  public void buttonPressFind() throws Exception {
    if (startNode.toString() == ""
        || endNode.toString() == ""
        || startNode.toString() == null
        || endNode.toString() == null) {
      errorMessage.setText("Please input nodes!");
    } else {
      // try {
      LinkedList N = g.aStar(startNode.toString(), endNode.toString());
      // }
      // catch (Exception) {
      errorMessage.setText("Please input valid nodes!");
      // }

      errorMessage.setText("Pathfinding complete!");
    }

    // Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/AStarTyler.fxml"));
    // App.getPrimaryStage().getScene().setRoot(root);
  }
}
