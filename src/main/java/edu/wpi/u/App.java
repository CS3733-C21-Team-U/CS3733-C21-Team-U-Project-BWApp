package edu.wpi.u;

import edu.wpi.u.algorithms.Graph;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.models.Message;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {
  public static Graph graph = new Graph();

  public App(){
    Node A = new Node("A", 0, 0);
    Node B = new Node("B", 1, 0);
    Node C = new Node("C", 2, 0);
    Node D = new Node("D", 2, 1);
    Node E = new Node("E", 1, 30);
    Node F = new Node("F", 0, 1);

    graph.makeEdge("1", A, B);
    graph.makeEdge("2", B, C);
    graph.makeEdge("3", D, C);
    graph.makeEdge("4", D, E);
    graph.makeEdge("5", E, F);
    graph.makeEdge("6", F, A);
    graph.makeEdge("7", F, B);
    graph.makeEdge("8", E, A);
    graph.makeEdge("9", B, D);
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
