package edu.wpi.u.controllers;

import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

public class NewController {

  @FXML public TextField author;
  @FXML public TextField message;

  @FXML
  private void saveTextToModel() throws Exception {
    App.savedData.author = author.getText();
    App.savedData.message = message.getText();
  }

  @FXML
  private void initialize() {
    if (App.savedData.author != null && App.savedData.message != null) {
      author.setText(App.savedData.author);
      message.setText(App.savedData.message);
    }
  }

  @FXML
  private void toMainScene() throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/MainView.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }
}
