package edu.wpi.u.controllers.request;

import edu.wpi.u.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RequestListItemEditController extends AnchorPane implements Initializable {

    public RequestListItemContainerController parent;
    public Region pushDown1;

    public RequestListItemEditController(RequestListItemContainerController parent) throws IOException {
        this.parent = parent; //THIS SHOULD ALWAYS BE FIRST
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/request/RequestListItemEdit.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();
    }

    public void handleSaveButton(){

    }
    public void handleCancelButton(){
        parent.switchToCollapsed();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        parent.request.
        //Set Existing values for fields
    }


}
