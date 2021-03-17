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
import javafx.beans.property.SimpleStringProperty;
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

  public static int leftMenuScreenNum = 1; //Start on the 1st screen (Path Planning)
  public static SimpleStringProperty leftDrawerRoot = new SimpleStringProperty("/edu/wpi/u/views/Oldfxml/LeftDrawerMenu.fxml");
  public static SimpleStringProperty rightDrawerRoot = new SimpleStringProperty("/edu/wpi/u/views/ViewRequest.fxml");//This is where we store what scene the right drawer is in.
  // DO NOT CALL THIS UNLESS ADDING NEW REQUEST
  public static SimpleBooleanProperty addNewRequestToList = new SimpleBooleanProperty(false);
  public static boolean isEdtingGuest;
  public static SimpleBooleanProperty mobileUpdateParkingSpot = new SimpleBooleanProperty(true);
  public static boolean loadedAlready = false;
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
  public static CovidService covidService = new CovidService();
  public static AdminToolStorage AdminStorage = new AdminToolStorage();
  public static PathHandling PathHandling = new PathHandling();
  public static UndoRedoService undoRedoService = new UndoRedoService();
  public static SVGPath pathFindingPath;
  public static SVGPath pathFindingPath2;
  public static VBox newReqVBox;
  public static SimpleBooleanProperty VBoxChanged = new SimpleBooleanProperty(true);

  public static SVGPath themeSVG;

  public static String newNodeType;

  public static SimpleBooleanProperty mobileUpdateDestinationField = new SimpleBooleanProperty(false);


  public static String lastSelectedNode;
  public static String nodeField1;
  public static String nodeField2;
  public static String lastSelectedEdge;
  public static String edgeField1;
  public static String edgeField2;

  public static JFXTabPane tabPaneRoot;
  public static String pathfindingAlgorithm = "ASTAR";//this will be set in the setting to be ASTAR BFS or DFS


  public static Integer lastClickedRequestNumber;
  public static Guest selectedGuest;
  public static Employee selectedEmployee;

  public static PrettyTime prettyTime = new PrettyTime();

  public static String test = "hello there";
  public static Parent base;
  public static Parent loginBase;
  public static Parent guestBase;
  public static SimpleBooleanProperty updateEmail = new SimpleBooleanProperty(false);
  public static SimpleBooleanProperty updatePhoneNumber= new SimpleBooleanProperty(false);
  public static String themeString;
  public static SimpleBooleanProperty loginFlag = new SimpleBooleanProperty(false);
  public static SimpleBooleanProperty isLoggedIn = new SimpleBooleanProperty(false);
  public static SimpleBooleanProperty useCache = new SimpleBooleanProperty(true);
  public static ClassLoader classLoader = new CachingClassLoader(FXMLLoader.getDefaultClassLoader());
  //public static FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/edu/wpi/u/views/NewMainPage.fxml"));

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
//    themeString.addListener((observable, oldVal, newVal)->{
//      App.primaryStage.getScene().getStylesheets().remove(1);
//      switch (newVal){
//        case "PURPLE":
//          App.primaryStage.getScene().getStylesheets().add(getClass().getResource("/edu/wpi/u/views/css/LightTheme.css").toExternalForm());
//          break;
//        case "DARK":
//          App.primaryStage.getScene().getStylesheets().add(getClass().getResource("/edu/wpi/u/views/css/DarkTheme.css").toExternalForm());
//          break;
//        case "BLUE":
//          App.primaryStage.getScene().getStylesheets().add(getClass().getResource("/edu/wpi/u/views/css/Theme1.css").toExternalForm());
//          break;
//        case "YELLOW":
//          App.primaryStage.getScene().getStylesheets().add(getClass().getResource("/edu/wpi/u/views/css/Theme2.css").toExternalForm());
//          break;
//        default:
//          App.primaryStage.getScene().getStylesheets().add(getClass().getResource("/edu/wpi/u/views/css/LightTheme.css").toExternalForm());
//          break;
//      }
//    });

//    fxmlLoader.setClassLoader(classLoader);
//    try {
//      fxmlLoader.load();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
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
    if (useCache.get()){
      FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/edu/wpi/u/views/NewMainPage.fxml"));
      fxmlLoader.setClassLoader(classLoader);
      fxmlLoader.load();
      fxmlLoader.getController();
      base = fxmlLoader.getRoot();

//      FXMLLoader fxmlLoader2 = new FXMLLoader(App.class.getResource("/edu/wpi/u/views/login/UserLoginScreen.fxml"));
//      fxmlLoader2.setClassLoader(classLoader);
//      fxmlLoader2.load();
//      fxmlLoader2.getController();
//      loginBase = fxmlLoader2.getRoot();
//
//      FXMLLoader fxmlLoader3 = new FXMLLoader(App.class.getResource("/edu/wpi/u/views/login/GuestSigninScreen.fxml"));
//      fxmlLoader3.setClassLoader(classLoader);
//      fxmlLoader3.load();
//      fxmlLoader3.getController();
//      guestBase = fxmlLoader3.getRoot();
    }

    System.out.println("App start");
    // App.getPrimaryStage.setScene(scene)
    App.primaryStage = stage; // stage is the window given to us
    //Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/UserLoginScreen.fxml"));
    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/SelectUserScreen.fxml"));
    //Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/pathfinding/TreeViewList.fxml"));

    mapService.loadCSVFile("src/main/resources/edu/wpi/u/MapUAllNodes.csv", "Nodes");
    mapService.loadCSVFile("src/main/resources/edu/wpi/u/MapUAllEdges.csv", "Edges");

    Scene scene = new Scene(root);
    App.primaryStage.setScene(scene);
//    Label label = new Label("Hello World");
//    label.setStyle("-fx-font-family: Akaya Telivigala; -fx-font-size: 100;");
//    label.setFont(Font.font("Rubik", FontWeight.NORMAL, 50));
//    Scene scene = new Scene(label);
//    scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Akaya+Telivigala&display=swap");

//    scene.getStylesheets().add("/edu/wpi/u/views/css/LightTheme.css");



//    scene.getStylesheets().add("/edu/wpi/u/views/css/BaseStyle.css");
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


    //Font.loadFont(App.class.getResource("/edu/wpi/u/views/css/Rubik-Regular.ttf").toExternalForm(), 10);
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
    //Database.getDB().saveAll();
//    Database.getDB().stop();
    Stage stage = (Stage) App.primaryStage.getScene().getWindow();
    stage.close();
  }


  public int requestClicked;
}
