package edu.wpi.u;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  private Parent root;

  @Override
  public void init() throws Exception {
    //Imporant to load during init so the main controller class can access sub-cotroller thorugh FXID
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/MainView.fxml"));
    root = fxmlLoader.load();
  }

  @Override
  public void start(Stage stage) throws Exception {
    stage.setScene(new Scene(root));
    stage.show();
  }


  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
