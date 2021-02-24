package edu.wpi.u.controllers;

import edu.wpi.u.App;
import edu.wpi.u.models.AdminToolStorage;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EditNodeController {

    @FXML public TextField NodeIDField;
    @FXML public TextField XCoordinate;
    @FXML public TextField YCoordinate;

    public void initialize(){
        NodeIDField.setText(App.lastSelectedNode);
        XCoordinate.setText(App.nodeField1);
        XCoordinate.setText(App.nodeField2);
    }


    public void handleNodeSubmitButton(){
        App.graphService.updateNode(AdminToolStorage.id, AdminToolStorage.xloc, AdminToolStorage.yloc);
    }
    public void handleNodeCancelButton(){
        App.rightDrawerRoot.set( "/edu/wpi/u/views/AdminTools.fxml");
    }

}
