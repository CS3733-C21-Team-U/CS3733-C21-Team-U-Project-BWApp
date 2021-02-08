package edu.wpi.teamname.views;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainPage {

  @FXML private Button btnHome;

  @FXML private Button btnKohmei;

  @FXML private Button btnTyler;

  @FXML
  private void handleButtonAction(ActionEvent event) throws IOException {
    Stage stage;
    Parent root;

    Object source = event.getSource();
    if (btnHome == source) {

      // get reference to the button's stage
      stage = (Stage) btnHome.getScene().getWindow();
      // load up OTHER FXML document
      root = FXMLLoader.load(getClass().getResource("../views/MainPage.fxml"));

    } else if (btnKohmei == source) {

      // get reference to the button's stage
      stage = (Stage) btnKohmei.getScene().getWindow();
      // load up OTHER FXML document
      root = FXMLLoader.load(getClass().getResource("../views/Kohmei.fxml"));

    } else if (btnTyler == source) {

      // get reference to the button's stage
      stage = (Stage) btnTyler.getScene().getWindow();
      // load up OTHER FXML document
      root = FXMLLoader.load(getClass().getResource("../views/tyler.fxml"));

    } else { // code block
      System.out.println("Switch for button handler had a non-exsistent source");
      return;
    }

    // create a new scene with root and set the stage
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
