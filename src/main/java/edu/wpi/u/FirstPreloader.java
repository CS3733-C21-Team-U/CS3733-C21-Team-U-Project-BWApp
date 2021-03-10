package edu.wpi.u;

import edu.wpi.u.controllers.NewMainPageController;
import edu.wpi.u.users.Role;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

public class FirstPreloader extends Preloader {
    ProgressBar bar;
    Stage stage;

    private Scene createPreloaderScene() {
        bar = new ProgressBar();
        BorderPane p = new BorderPane();
        p.setCenter(bar);
        return new Scene(p, 300, 150);
    }

    public void start(Stage stage) throws Exception {
        /*
        todo : load fxml here
         */

        //Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/NewMainPage.fxml"));
//        App.userService.setEmployee("debug");
//        System.out.println(App.userService.getActiveUser().getName());
        //App.userService.getActiveUser().setType(Role.ADMIN);
//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/edu/wpi/u/views/NewMainPage.fxml"));
////        fxmlLoader.setLocation(fxml);
//        fxmlLoader.load();
//        fxmlLoader.getController();
//        App.base = fxmlLoader.getRoot();
        this.stage = stage;
        stage.setScene(createPreloaderScene());
        stage.show();
    }

    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        bar.setProgress(pn.getProgress());
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
    }


}
