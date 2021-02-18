package edu.wpi.u;

import edu.wpi.u.models.DatabaseManager;
import edu.wpi.u.models.GraphService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  // Allows for globally accessible instance of app to allow instance based editing
  // (separate info per open application)
  // Can be accessed by all controllers and classes by calling App.getInstance();
  public static GraphService graphService = new GraphService();

  @Override
  public void init() {
    log.info("Starting Up");
  }

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
    Parent root = FXMLLoader.load(getClass().getResource("views/MainPage.fxml"));
    Scene scene = new Scene(root);
    App.primaryStage.setScene(scene);
    App.primaryStage.show();
  }


  @Override
  public void stop() {
    System.out.println("Shutting Down");
    graphService.saveAndExitDB();
  }
}
