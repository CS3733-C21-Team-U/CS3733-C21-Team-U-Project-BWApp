package edu.wpi.u.controllers;

import edu.wpi.u.App;
import edu.wpi.u.models.AdminToolStorage;

public class EditNodeController {



    public void handleNodeSubmitButton(){
        App.graphService.updateNode(AdminToolStorage.id, AdminToolStorage.xloc, AdminToolStorage.yloc);
    }
    public void handleNodeCancelButton(){
        App.rightDrawerRoot.set( "/edu/wpi/u/views/AdminTools.fxml");
    }

}
