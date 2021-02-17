package edu.wpi.u.controllers;

import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class NodeController {

  @FXML public TextField enterNodeID;

  @FXML public TextField enterXCoo;

  @FXML public TextField enterYCoo;

  @FXML public Label errorLabel;

  @FXML public Button editNodeButton;

  @FXML public Button deleteNodeButton;

  public void initialize() {
    update();
  }

  public void update() {
    // Uncomment when graph implements empty check or properly implements getNodes()
    // If getNodes() gets functionality first then put
    //
    // App.getInstance().graph.getNodes().isEmpty() into if statement below
    /*
    if (App.getInstance().graph.isEmpty()) {
      editNodeButton.setDisable(true);
      deleteNodeButton.setDisable(true);
    } else {
      editNodeButton.setDisable(false);
      deleteNodeButton.setDisable(false);
    }
     */
  }

  @FXML
  public void addNode() {

    String tempID = enterNodeID.getText();

    if (tempID.equals("")) {
      errorLabel.setText("Missing Node ID.");
      return;
    }
    if (checkTextBoxesErrorCoordiantes()) return;

    int tempX = Integer.parseInt(enterXCoo.getText());
    int tempY = Integer.parseInt(enterYCoo.getText());

    String ret = App.getInstance().graphService.addNode(tempID, tempX, tempY);
    if (ret.equals(tempID)) {
      errorLabel.setText("Node already exists");
      return;
    }

    update();
    errorLabel.setText("Node added successfully.");
  }

  @FXML
  public void editNode() {
    String tempID = enterNodeID.getText();
    if (tempID.equals("")) {
      errorLabel.setText("Missing Node ID.");
      return;
    }
    if (checkTextBoxesErrorCoordiantesEmpty()) return;
    if (checkTextBoxesErrorCoordiantes()) return;
    int tempX = Integer.parseInt(enterXCoo.getText());
    int tempY = Integer.parseInt(enterYCoo.getText());
    if (App.getInstance().graphService.updateNode(tempID, tempX, tempY).equals(tempID))
      errorLabel.setText("Node does not exists.");
    else {
      update();
      errorLabel.setText("node edited successfully.");
    }
  }

  @FXML
  public void deleteNode() {
    String tempID = enterNodeID.getText();
    if (tempID.equals("")) errorLabel.setText("Missing Node ID.");
    else {
      if (App.getInstance().graphService.deleteNode(tempID).equals(tempID))
        errorLabel.setText("Node does not exists.");
      else {
        update();
        errorLabel.setText("node deleted successfully.");
      }
    }
  }

  private boolean checkTextBoxesErrorCoordiantes() {
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

  private boolean checkTextBoxesErrorCoordiantesEmpty() {
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
}
