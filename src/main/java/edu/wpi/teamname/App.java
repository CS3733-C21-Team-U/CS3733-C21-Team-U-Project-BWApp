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
    BWdb db = new BWdb();
  }

  @Override
  public void start(Stage primaryStage) {
    Parameters p = getParameters();
    List<String> l = p.getRaw();
    UDB db = new UDB(l);
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
