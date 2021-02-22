package edu.wpi.u;

import edu.wpi.u.models.GraphService;
import edu.wpi.u.models.RequestService;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  // Allows for globally accessible instance of app to allow instance based editing
  // (separate info per open application)
  // Can be accessed by all controllers and classes by calling App.getInstance();
  public static App app_instance = null;

  public static SimpleStringProperty rightDrawerRoot = new SimpleStringProperty("../views/ViewRequest.fxml");//This is where we store what scene the right drawer is in.

  private static Stage primaryStage;
  // We only ever have one primary stage, each time we switch scenes, we swap this out

  public static GraphService graphService = new GraphService();
  public static RequestService requestService = new RequestService();



  public App(){
    System.out.println("App constructor");
  }

  public static App getInstance(){
    if(app_instance == null){
      app_instance = new App();
    }
    return app_instance;
  }

  @Override
  public void init() {
    log.info("Starting Up");
  }




  //    @Override
  //    public void init() throws Exception {
  //
  //    }

//  Font.loadFont(getClass().getResourceAsStream("/resources/fonts/marck.ttf"), 14);


  @Override
  public void start(Stage stage) throws Exception {
    App.primaryStage = stage; // stage is the window given to us
    Parent root = FXMLLoader.load(getClass().getResource("views/NewMainPage.fxml"));
    Scene scene = new Scene(root);
    App.primaryStage.setScene(scene);
    App.primaryStage.setFullScreen(true);
    App.primaryStage.show();

    Font.loadFont(App.class.getResource("/edu/wpi/u/views/css/Rubik-Regular.ttf").toExternalForm(), 10);

    App.primaryStage.getScene().setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ESCAPE) {
        System.out.println("Escape button pressed, exiting");
        App.getInstance().stop();
      }
    });
  }

  public static Stage getPrimaryStage() {
    return primaryStage;
  }

  public void stop() {
    System.out.println("Shutting Down");
    graphService.saveAndExitDB(); //TODO: change where we stop
    Stage stage = (Stage) App.primaryStage.getScene().getWindow();
    stage.close();
  }

}
