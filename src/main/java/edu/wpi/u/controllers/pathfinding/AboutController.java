package edu.wpi.u.controllers.pathfinding;

import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class AboutController {

    /**
     * Brings AboutPage back to Pathfinding.
     * @throws Exception
     * Author Lily
     */
    @FXML public void handleAboutCancel() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }
}
