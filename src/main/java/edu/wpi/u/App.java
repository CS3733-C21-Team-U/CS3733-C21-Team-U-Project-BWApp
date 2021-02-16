package edu.wpi.u;

import edu.wpi.u.algorithms.Node;
import edu.wpi.u.models.GraphService;
import edu.wpi.u.models.Message;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {
  // Allows for globally accessible instance of app to allow instance based editing (separate info
  // per open application)
  // Can be accessed by all controllers and classes by calling App.getInstance();
  private static final App instance = new App();
  public GraphService graphService = new GraphService();

  // Gets the current instance of App
  public static App getInstance() {
    return instance;
  }

  public App() {
    Node A = new Node("A", 0, 0);
    Node B = new Node("B", 1, 0);
    Node C = new Node("C", 2, 0);
    Node D = new Node("D", 2, 1);
    Node E = new Node("E", 1, 30);
    Node F = new Node("F", 0, 1);

    graphService.addEdge("1", "A", "B");
    graphService.addEdge("2", "B", "C");
    graphService.addEdge("3", "D", "C");
    graphService.addEdge("4", "D", "E");
    graphService.addEdge("5", "E", "F");
    graphService.addEdge("6", "F", "A");
    graphService.addEdge("7", "F", "B");
    graphService.addEdge("8", "E", "A");
    graphService.addEdge("9", "B", "D");
  }

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
    Parent root = FXMLLoader.load(getClass().getResource("views/MainPage.fxml"));
    Scene scene = new Scene(root);
    App.primaryStage.setScene(scene);
    App.primaryStage.show();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
