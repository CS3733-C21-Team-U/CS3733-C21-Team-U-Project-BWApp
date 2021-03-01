package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import javafx.fxml.FXML;

public class NodeContextMenuController {
    Node node1;

    @FXML
    JFXTextField longNameText;
    @FXML
    JFXTextField shortNameText;
    @FXML
    JFXComboBox nodeTypeDrop;

    /**
     * To be run when save button in node context menu is pressed. Needs to check if a node can be saved, then saves a node.
     */
    @FXML


    public void handleSaveButton(){
        // App.mapService.updateNode() TODO: Use Charlie's undo/redo stuff
    }



}
