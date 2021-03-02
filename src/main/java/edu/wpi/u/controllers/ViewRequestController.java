package edu.wpi.u.controllers;

import edu.wpi.u.App;
import edu.wpi.u.requests.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
            Request req = listOfRequests.get(i).getGenericRequest();
            //This is how you add title panes here
            FXMLLoader requestLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewRequestItem.fxml"));
            AnchorPane request = requestLoader.load();
            RequestItemController controller = requestLoader.getController();
            String temp = req.getTitle();
            controller.myRequestID = req.getRequestID();
            controller.requestItemTitleLabel.setText(temp);
            //Read ChipView Again
            //controller.requestItemLocationChipView.setText(listOfRequests.get(i).getLocation().toString());
            controller.requestItemDescriptionLabel.setText(req.getDescription());
            controller.requestItemCreatorLabel.setText(req.getCreator());
            controller.requestItemDate2BCompletedLabel.setText(null);   //listOfRequests.get(i).getDate2BCompleted());
            controller.requestItemRequestTypeLabel.setText(req.getType());
            //controller.myID = i;
            requestList.getChildren().add(request);

            /*
            final int index = i;
            controller.editRequestButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    App.getInstance().requestClicked = index;
                }
            });
            */
        }
    }

    /*
    @FXML
    public void handleChangeToEditRequest(){
        //Switch to a new right drawer
        App.rightDrawerRoot.set( "/edu/wpi/u/views/EditRequest.fxml");

    }
     */

    @FXML
    public void handleNewRequestButton() {
        new FXMLLoader(getClass().getResource("/edu/wpi/u/views/MakeANewRequest.fxml"));
    }
}
