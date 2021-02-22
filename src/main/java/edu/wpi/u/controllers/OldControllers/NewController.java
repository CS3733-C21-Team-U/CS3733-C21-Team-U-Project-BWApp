package edu.wpi.u.controllers.OldControllers;

import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

public class NewController {

  @FXML public TextField author;
  @FXML public TextField message;
  @FXML public TextField authorLabel;
  @FXML public TextField messageLabel;

  @FXML
  private void initialize() {
    System.out.println("New Init");
  }

  @FXML
  private void toMainScene() throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("edu/wpi/u/views/MainPage.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }
}
