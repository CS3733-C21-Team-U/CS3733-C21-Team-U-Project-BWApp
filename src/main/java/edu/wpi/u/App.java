package edu.wpi.u;

import javafx.application.Application;
import edu.wpi.u.models.Message;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {


  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override

  public static Message savedData = new Message();

  private static Stage primaryStage; // This is a static variable!!
  // We only ever have one primary stage, each time we switch scenes, we swap this out

  public static Stage getPrimaryStage() {
    return primaryStage;
  }

  //    @Override
  //    public void init() throws Exception {
  //
  //    }

  @Override
  public void start(Stage stage) throws Exception {
    App.primaryStage = stage; // stage is the window given to us
    Parent root = FXMLLoader.load(getClass().getResource("views/MainView.fxml"));
    Scene scene = new Scene(root);
    App.primaryStage.setScene(scene);
    App.primaryStage.show();
  }


  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
