package edu.wpi.u.controllers;

import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ModifyEdgeController {

    @FXML public TextField modifyNodeID;
    @FXML public TextField sNode;
    @FXML public TextField eNode;

    public void initialize(){
        modifyNodeID.setText(App.lastSelectedEdge);
        sNode.setText(App.edgeField1);
        eNode.setText(App.edgeField2);
    }

    @FXML
    public void handleEdgeSubmitButton() {
        App.graphService.updateStartEdge(modifyNodeID.getText(), sNode.getText());
        App.graphService.updateEndEdge(modifyNodeID.getText(), eNode.getText());
        App.rightDrawerRoot.set( "/edu/wpi/u/views/AdminTools.fxml");
    }
    @FXML
    public void handleEdgeCancelButton() {
        App.rightDrawerRoot.set( "/edu/wpi/u/views/AdminTools.fxml");
    }


}
