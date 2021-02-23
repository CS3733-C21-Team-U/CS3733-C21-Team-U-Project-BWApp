package edu.wpi.u.controllers;
import edu.wpi.u.models.GraphManager;
import edu.wpi.u.models.GraphService;

import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class NewEdgeController {

    @FXML public TextField EdgeIDField;
    @FXML public TextField StartingNode;
    @FXML public TextField EndingNode;

    @FXML
    public void handleEdgeSubmitButton() {
        App.graphService.addEdge(EdgeIDField.getText(), StartingNode.getText(), EndingNode.getText());
        App.rightDrawerRoot.set( "../views/AdminTools.fxml");
    }
    @FXML
    public void handleEdgeCancelButton() {
        App.rightDrawerRoot.set( "../views/AdminTools.fxml");
    }
}
