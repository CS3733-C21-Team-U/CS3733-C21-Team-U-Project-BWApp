package edu.wpi.u.controllers;
import edu.wpi.u.models.GraphManager;
import edu.wpi.u.models.GraphService;

import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class NewNodeController {

    @FXML public TextField NodeIDField;
    @FXML public TextField XCoordinate;
    @FXML public TextField YCoordinate;
    @FXML public TextField FloorField;
    @FXML public TextField BuildingField;
    @FXML public TextField NodeTypeField;
    @FXML public TextField ShortNameField;
    @FXML public TextField LongNameField;

    @FXML
    public void handleNodeSubmitButton() {
        GraphService g = new GraphService();
        g.addNode(NodeIDField.getText(), Integer.parseInt(XCoordinate.getText()), Integer.parseInt(YCoordinate.getText()));
        App.rightDrawerRoot.set( "../views/AdminTools.fxml");
    }
    @FXML
    public void handleNodeCancelButton() {
        App.rightDrawerRoot.set( "../views/AdminTools.fxml");
    }


}
