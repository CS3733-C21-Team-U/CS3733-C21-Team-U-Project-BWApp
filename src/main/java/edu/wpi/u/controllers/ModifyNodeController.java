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

    public void initialize(){
        modifyNodeID.setText(App.lastSelectedNode);
        XCoordinate.setText(App.nodeField1);
        YCoordinate.setText(App.nodeField2);
    }


    @FXML
    public void handleNodeSubmitButton() {
        App.graphService.updateNode(modifyNodeID.getText(), (int)Double.parseDouble(XCoordinate.getText()), (int)Double.parseDouble(YCoordinate.getText()));
        App.rightDrawerRoot.set( "../views/AdminTools.fxml");
    }
    @FXML
    public void handleNodeCancelButton() {
        App.rightDrawerRoot.set( "../views/AdminTools.fxml");
    }


}
