package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXToggleNode;
import edu.wpi.u.App;
import edu.wpi.u.models.AdminToolStorage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class NodeItemController {

    @FXML public AnchorPane nodeAnchor;
    @FXML public Label nodeID;
    @FXML public Label nodeLocation;
    @FXML public JFXToggleNode toggleNodeNode;
    public int XPos;
    public int YPos;


    @FXML
    public void handleSelectNode(){
        App.AdminStorage.selectedNode.toggleNodeNode.setSelected(false);
        App.AdminStorage.selectedNode = this;
        App.AdminStorage.xloc = XPos;
        App.AdminStorage.yloc = YPos;
        if(this.toggleNodeNode.isSelected()){
            App.AdminStorage.nodeIsSelected = true;
            App.AdminStorage.haveSelectedNode.set(AdminToolStorage.haveSelectedNode.get()+1);
        }else{
            App.AdminStorage.nodeIsSelected = false;
            App.AdminStorage.haveSelectedNode.set(AdminToolStorage.haveSelectedNode.get()+1);
        }

    }

}
