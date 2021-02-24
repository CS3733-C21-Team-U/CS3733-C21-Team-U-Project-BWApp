package edu.wpi.u;

import edu.wpi.u.database.Database;
import edu.wpi.u.models.AdminToolStorage;
import edu.wpi.u.models.GraphService;
import edu.wpi.u.models.PathHandling;
import edu.wpi.u.models.RequestService;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  // Allows for globally accessible instance of app to allow instance based editing
  // (separate info per open application)
  // Can be accessed by all controllers and classes by calling App.getInstance();
  public static App app_instance = null;

  public static int leftMenuScreenNum = 2; //Start on the 2nd screen (Service Requests)
  public static SimpleStringProperty leftDrawerRoot = new SimpleStringProperty("/edu/wpi/u/views/LeftDrawerMenu.fxml");
  public static SimpleStringProperty rightDrawerRoot = new SimpleStringProperty("/edu/wpi/u/views/ViewRequest.fxml");//This is where we store what scene the right drawer is in.
  private static Stage primaryStage;
  // We only ever have one primary stage, each time we switch scenes, we swap this out
  public static Database db = new Database();
  public static GraphService graphService = new GraphService();
  public static RequestService requestService = new RequestService();
  public static AdminToolStorage AdminStorage = new AdminToolStorage();
  public static PathHandling PathHandling = new PathHandling();
  public static SVGPath pathFindingPath;
  public static SVGPath pathFindingPath2;

  public App(){
    System.out.println("App constructor");
    app_instance = this;
  }

  public static App getInstance(){
    if(app_instance == null){
      app_instance = new App();
    }
    return app_instance;
  }

  @Override
  public void init() {
    System.out.println("Starting Up");
//    Font.loadFont(App.class.getResource("/edu/wpi/u/views/css/Rubik-VariableFont_wght.ttf").toExternalForm(), 12);
  }




//      @Override
//      public void init() throws Exception {
//
//      }

//  Font.loadFont(getClass().getResourceAsStream("/resources/fonts/marck.ttf"), 14);


  @Override
  public void start(Stage stage) throws Exception {
    App.primaryStage = stage; // stage is the window given to us
    Parent root = FXMLLoader.load(getClass().getResource("views/NewMainPage.fxml"));
    Scene scene = new Scene(root);
//    Label label = new Label("Hello World");
//    label.setStyle("-fx-font-family: Akaya Telivigala; -fx-font-size: 100;");
//    label.setFont(Font.font("Rubik", FontWeight.NORMAL, 50));
//    Scene scene = new Scene(label);
//    scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Akaya+Telivigala&display=swap");
//    scene.getStylesheets().add("/edu/wpi/u/views/css/RegularTheme.css");
    scene.getStylesheets().add(getClass().getResource("/edu/wpi/u/views/css/RegularTheme.css").toExternalForm());
    App.primaryStage.setScene(scene);
    App.primaryStage.setFullScreen(true);
    App.primaryStage.show();

//    Font.loadFont(App.class.getResource("/edu/wpi/u/views/css/Rubik-Regular.ttf").toExternalForm(), 10);

    App.primaryStage.getScene().setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ESCAPE) {
        System.out.println("Escape button pressed, exiting");
        App.getInstance().end();
      }
    });
  }





  public static Stage getPrimaryStage() {
    return primaryStage;
  }

  public void end() {
    System.out.println("Shutting Down");
    requestService.saveCSVFile("Requests.csv", "Requests");
    requestService.saveCSVFile("Assignments.csv", "Assignments");
    requestService.saveCSVFile("Locations.csv", "Locations");
    graphService.saveCSVFile("Nodes.csv", "Nodes");
    graphService.saveCSVFile("Edges.csv", "Edges");
    db.stop();
    Stage stage = (Stage) App.primaryStage.getScene().getWindow();
    stage.close();
  }


  public int requestClicked;

}
