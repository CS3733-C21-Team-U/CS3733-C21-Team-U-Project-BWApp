package edu.wpi.teamname;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
public class App extends Application {

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) {
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
