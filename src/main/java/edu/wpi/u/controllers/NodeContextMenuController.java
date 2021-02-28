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
    @FXML
    public void handleSaveButton(){
        // App.mapService.updateNode() TODO: uppdate updateNode function
    }



}
