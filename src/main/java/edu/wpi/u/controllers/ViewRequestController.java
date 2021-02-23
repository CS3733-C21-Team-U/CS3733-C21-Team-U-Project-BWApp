package edu.wpi.u.controllers;

import edu.wpi.u.App;
import edu.wpi.u.models.Request;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ViewRequestController {


    @FXML public VBox requestList;

    public void initialize() throws IOException {

        ArrayList<Request> listOfRequests = App.requestService.getRequests();
        for (int i = 0; i < listOfRequests.size(); i++) {
            //This is how you add title panes here
            FXMLLoader requestLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/RequestItem.fxml"));
            AnchorPane request = requestLoader.load();
            RequestItemController controller = requestLoader.getController();
            String temp = listOfRequests.get(i).getTitle();
            controller.titleLabel.setText(temp);
            controller.locationLabel.setText(listOfRequests.get(i).getLocation());
            controller.descriptionLabel.setText(listOfRequests.get(i).getDescription());
            requestList.getChildren().add(request);

            final int index = i;
            controller.editRequestButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    App.getInstance().requestClicked = index;
                }
            });
        }
    }


    @FXML
    public void handleChangeToEditRequest(){
        //Switch to a new right drawer
        App.rightDrawerRoot.set( "/edu/wpi/u/views/EditRequest.fxml");

    }

    @FXML
    public void handleNewRequestButton() {
        App.rightDrawerRoot.set( "/edu/wpi/u/views/NewRequest.fxml");
    }
}
