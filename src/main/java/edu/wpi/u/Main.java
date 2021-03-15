package edu.wpi.u;

import com.sun.javafx.application.LauncherImpl;
import edu.wpi.u.controllers.AutoClose;
import javafx.scene.layout.VBox;

public class Main {
  public static void main(String[] args) {
    //System.setProperty("javafx.preloader", FirstPreloader.class.getCanonicalName());
    //LauncherImpl.launchApplication(App.class, FirstPreloader.class, args);
    App.launch(App.class, args);


  }
}
