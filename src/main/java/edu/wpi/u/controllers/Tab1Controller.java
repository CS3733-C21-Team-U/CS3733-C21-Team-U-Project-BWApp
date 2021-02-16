package edu.wpi.u.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Tab1Controller {

  @FXML public TextField author;
  @FXML public TextField message;
  public TextField authorLabel;
  public TextField messageLabel;
  private MainController mainController;

  public void injectMainController(MainController mainController) {
    this.mainController = mainController;
    authorLabel.textProperty().bind(mainController.sceneData.author);
    messageLabel.textProperty().bind(mainController.sceneData.message);
  }

  public void initialize() {
    System.out.println("Tab 1 Init");
  }

  @FXML
  public void saveData(ActionEvent actionEvent) {
    mainController.sceneData.author.setValue(author.getText());
    mainController.sceneData.message.setValue(message.getText());
  }
}
