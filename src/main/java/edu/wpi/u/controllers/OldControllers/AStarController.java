package edu.wpi.u.controllers.OldControllers;

import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.models.GraphService;
import java.util.LinkedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class AStarController {



  @FXML public TextField startNode;
  @FXML public TextField endNode;
  @FXML public Label errorMessage;
  @FXML public ListView listOfNodes;

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
  public void buttonPressEdge() throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/Oldfxml/EditEdgeNick.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }

  @FXML
  public void buttonPressFind() throws Exception {
    listOfNodes.getItems().clear();
    GraphService g = App.graphService;
    if (startNode.getText().equals("") || endNode.getText().equals("")) {
      errorMessage.setText("Please input nodes!");
    } else {
      // try {
      LinkedList<Node> N = new LinkedList<Node>();
      N = g.aStar(startNode.getText(), endNode.getText());
      /*
      N.add(new Node("Ay", 100, 200));
      N.add(new Node(" Yo", 100, 200));
      N.add(new Node("Wassup", 100, 200));
      */
      if(N == null){//This is temporary until the Astar function throws actual exceptions.
        errorMessage.setText("Pathfinding error");
        return;
      }
      for (int i = 0; i < N.size(); i++) {
        listOfNodes.getItems().add(N.get(i).getNodeID());
      }
      // } This is code that allows for an exception to be thrown from the A* part.
      // It will be caught here and an error message will be displayed.
      // catch (Exception) {
      // errorMessage.setText("Please input valid nodes!");
      // }
      errorMessage.setText("Pathfinding complete!");
    }
  }
}
