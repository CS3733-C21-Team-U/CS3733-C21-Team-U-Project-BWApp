package edu.wpi.u;

import com.jfoenix.controls.JFXTabPane;
import edu.wpi.u.database.Database;
import edu.wpi.u.models.*;
import edu.wpi.u.users.Employee;
import edu.wpi.u.users.Guest;
import edu.wpi.u.web.EmailService;
import edu.wpi.u.web.TextingService;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.ocpsoft.prettytime.PrettyTime;

@Slf4j
public class App extends Application {

  // Allows for globally accessible instance of app to allow instance based editing
  // (separate info per open application)
  // Can be accessed by all controllers and classes by calling App.getInstance();
  public static App app_instance = null;

  // DO NOT CALL THIS UNLESS ADDING NEW REQUEST
  public static SimpleBooleanProperty addNewRequestToList = new SimpleBooleanProperty(false);
  public static boolean isEdtingGuest;
  public static SimpleBooleanProperty mobileUpdateParkingSpot = new SimpleBooleanProperty(true);
  private static Stage primaryStage;
  public static StackPane throwDialogHerePane;
  public static StackPane loadingSpinnerHerePane;

  // We only ever have one primary stage, each time we switch scenes, we swap this out
  public static Database db = Database.getDB();
  public static UserService userService = new UserService();
  public static MapService mapService = new MapService();
  public static EmailService emailService = new EmailService();
  public static TextingService textingService = new TextingService();
  public static MapInteractionModel mapInteractionModel = new MapInteractionModel();
  public static RequestService requestService = new RequestService();
  public static UndoRedoService undoRedoService = new UndoRedoService();
  public static SVGPath pathFindingPath;
  public static SVGPath pathFindingPath2;
  public static VBox newReqVBox;
  public static SimpleBooleanProperty VBoxChanged = new SimpleBooleanProperty(true);

  public static SVGPath themeSVG;

  public static SimpleBooleanProperty mobileUpdateDestinationField = new SimpleBooleanProperty(false);

  public static JFXTabPane tabPaneRoot;
  public static String pathfindingAlgorithm = "ASTAR";//this will be set in the setting to be ASTAR BFS or DFS

  public static Guest selectedGuest;
  public static Employee selectedEmployee;

  public static PrettyTime prettyTime = new PrettyTime();

  public static String test = "hello there";
  public static Parent base;
  public static SimpleBooleanProperty updateEmail = new SimpleBooleanProperty(false);
  public static SimpleBooleanProperty updatePhoneNumber= new SimpleBooleanProperty(false);
  public static String themeString;
  public static SimpleBooleanProperty isLoggedIn = new SimpleBooleanProperty(false);
  public static SimpleBooleanProperty useCache = new SimpleBooleanProperty(true);
  public static ClassLoader classLoader = new CachingClassLoader(FXMLLoader.getDefaultClassLoader());

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
  public void init()  {
    System.out.println("Starting Up");
  }

  @Override
  public void start(Stage stage) throws Exception {
    if (useCache.get()){
      FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/edu/wpi/u/views/NewMainPage.fxml"));
      fxmlLoader.setClassLoader(classLoader);
      fxmlLoader.load();
      fxmlLoader.getController();
      base = fxmlLoader.getRoot();
    }

    System.out.println("App start");
    App.primaryStage = stage; // stage is the window given to us
    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/SelectUserScreen.fxml"));

    mapService.loadCSVFile("MapUAllNodes.csv", "Nodes");
    mapService.loadCSVFile("MapUAllEdges.csv", "Edges");

    Scene scene = new Scene(root);
    App.primaryStage.setScene(scene);
    App.primaryStage.getScene().getStylesheets().add(getClass().getResource("/edu/wpi/u/views/css/BaseStyle.css").toExternalForm());
    loadCorrectThmeme();
    App.primaryStage.setFullScreen(true);
    App.primaryStage.setFullScreenExitHint("");
    App.primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    App.primaryStage.show();

    App.getPrimaryStage().getScene().setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.Q && e.isControlDown()) {
        App.getInstance().exitApp();
      }
    });
  }

  private void loadCorrectThmeme() {
    switch (App.themeString){
      case "PURPLE":
        App.primaryStage.getScene().getStylesheets().add(getClass().getResource("/edu/wpi/u/views/css/LightTheme.css").toExternalForm());
        break;
      case "DARK":
        App.primaryStage.getScene().getStylesheets().add(getClass().getResource("/edu/wpi/u/views/css/DarkTheme.css").toExternalForm());
        break;
      case "YELLOW":
        App.primaryStage.getScene().getStylesheets().add(getClass().getResource("/edu/wpi/u/views/css/Theme2.css").toExternalForm());
        break;
      case "BLUE":
        App.primaryStage.getScene().getStylesheets().add(getClass().getResource("/edu/wpi/u/views/css/Theme1.css").toExternalForm());
        break;
      default:
        App.primaryStage.getScene().getStylesheets().add(getClass().getResource("/edu/wpi/u/views/css/LightTheme.css").toExternalForm());
    }
  }

  public static Stage getPrimaryStage() {
    return primaryStage;
  }

  public void exitApp() {
    App.isLoggedIn.set(false);
    System.out.println("Shutting Down");
    Database.getDB().stop();
    Stage stage = (Stage) App.primaryStage.getScene().getWindow();
    stage.close();
  }
}
