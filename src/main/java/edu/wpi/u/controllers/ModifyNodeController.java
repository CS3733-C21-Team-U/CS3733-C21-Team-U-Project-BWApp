package edu.wpi.u.controllers;

import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ModifyNodeController {

    @FXML public TextField modifyNodeID;
    @FXML public TextField XCoordinate;
    @FXML public TextField YCoordinate;
    @FXML public TextField FloorField;
    @FXML public TextField BuildingField;
    @FXML public TextField NodeTypeField;
    @FXML public TextField ShortNameField;
    @FXML public TextField LongNameField;

    @FXML
    public void handleNodeSubmitButton() {
        App.graphService.updateNode(modifyNodeID.getText(), Integer.parseInt(XCoordinate.getText()), Integer.parseInt(YCoordinate.getText()));
        App.rightDrawerRoot.set( "../views/AdminTools.fxml");
    }
    @FXML
    public void handleNodeCancelButton() {
        App.rightDrawerRoot.set( "../views/AdminTools.fxml");
    }


}
