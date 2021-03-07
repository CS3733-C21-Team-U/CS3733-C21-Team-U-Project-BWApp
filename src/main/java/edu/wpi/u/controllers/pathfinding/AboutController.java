package edu.wpi.u.controllers.pathfinding;

import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class AboutController {

    @FXML public void handleAboutCancel() throws Exception{
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/pathfinding/PathfindingBase.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }
}
