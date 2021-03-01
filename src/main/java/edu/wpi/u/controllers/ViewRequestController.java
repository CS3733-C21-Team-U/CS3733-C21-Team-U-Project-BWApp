package edu.wpi.u.controllers;

import edu.wpi.u.App;
import edu.wpi.u.requests.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.ArrayList;

public class ViewRequestController {


    @FXML public VBox requestList;




    public void initialize() throws IOException {
        System.out.println("In Init for View Request");

        ArrayList<IRequest> listOfRequests = App.requestService.getRequests();
        for (int i = 0; i < listOfRequests.size(); i++) {
            //This is how you add title panes here
            FXMLLoader requestLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/RequestItem.fxml"));
            AnchorPane request = requestLoader.load();
            Request req = listOfRequests.get(i).getGenericRequest();
            RequestItemController controller = requestLoader.getController();
            String temp = req.getTitle();
            controller.myRequestID = req.getRequestID();
            controller.titleLabel.setText(temp);
            controller.locationLabel.setText(req.getLocation().toString());
            controller.descriptionLabel.setText(req.getDescription());
            controller.myID = i;
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
