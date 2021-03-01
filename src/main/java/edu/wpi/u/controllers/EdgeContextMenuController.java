package edu.wpi.u.controllers;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.users.StaffType;
import javafx.fxml.FXML;

import java.util.ArrayList;

public class EdgeContextMenuController {
    Node node1;

    @FXML
    JFXComboBox nodeTypeDrop;

    @FXML
    public void handleSaveButton(){
        App.mapService.updateEdgePermissions("",new ArrayList<StaffType>()); // TODO: update edges properly
    }

}
