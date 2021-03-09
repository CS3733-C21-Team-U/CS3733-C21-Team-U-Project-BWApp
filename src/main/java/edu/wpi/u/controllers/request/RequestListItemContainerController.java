package edu.wpi.u.controllers.request;

import edu.wpi.u.App;
import edu.wpi.u.requests.Request;
import edu.wpi.u.requests.SpecificRequest;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RequestListItemContainerController extends AnchorPane implements Initializable{

    public SpecificRequest request;
    public Parent expandedNode;
    public Parent collapsedNode;
    public Parent editNode;
    public SimpleBooleanProperty needUpdate = new SimpleBooleanProperty(true);


    public RequestListItemContainerController(SpecificRequest request) throws IOException {
        this.request = request; //MUST BE FIRST!!!

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/request/RequestListItemContainer.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        //"this" is both an anchor pane and controller
        //https://github.com/CS3733-C21-Team-U/CS3733-C21-Team-U-Project-BWApp/pull/226
        loader.load();

        expandedNode = new RequestListItemExpandedController(this);
        collapsedNode = new RequestListItemCollapsedController(this);
        editNode = new RequestListItemEditController(this);

        this.getChildren().add(collapsedNode);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void switchToExpanded() {
        this.getChildren().clear();
        this.getChildren().add(expandedNode);

    }
    public void switchToCollapsed() {
        this.getChildren().clear();
        this.getChildren().add(collapsedNode);
    }
    public void switchToEdit() {
        this.getChildren().clear();
        this.getChildren().add(editNode);
    }

}
