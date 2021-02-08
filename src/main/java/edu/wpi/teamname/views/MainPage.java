package edu.wpi.teamname.views;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainPage {

  @FXML private Button btnHome;

  @FXML private Button btnKohmei;

  @FXML private Button btnKaamil;

  // Kaamil section
  @FXML private TextField inputKaamil;

  @FXML private Label msgKaamil;

  @FXML private AnchorPane anchor;

  private String cssSheet =
      "file:/C:/Users/Kaam9/Documents/WPI/CS/Soft/CS3733-C21-Team-U-Project-BWApp/build/resources/main/edu/wpi/teamname/views/styles/Kaamil2.css";

  @FXML
  private void updateMessage() {
    msgKaamil.setText(inputKaamil.getText());
  }

  @FXML
  private void toggleColor() {
    // edit css code here
    String oldCss = cssSheet;
    cssSheet = anchor.getStylesheets().get(0);
    anchor.getStylesheets().set(0, oldCss); // switch css sheet
  }

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

    } else if (btnKaamil == source) {

      // get reference to the button's stage
      stage = (Stage) btnKohmei.getScene().getWindow();
      // load up OTHER FXML document
      root = FXMLLoader.load(getClass().getResource("../views/Kaamil.fxml"));

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
