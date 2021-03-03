package edu.wpi.u.controllers;

import edu.wpi.u.App;
import edu.wpi.u.requests.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class ViewRequestController {

    @FXML public VBox requestList;

    /**
     *Gets each request and attaches it to a NewRequestItem
     * @throws IOException
     */
    public void initialize() throws IOException {
        System.out.println("In Init for View Request");

        ArrayList<IRequest> listOfRequests = App.requestService.getRequests();
        App.lastClickedRequestNumber = 0;
        for (int i = 0; i < listOfRequests.size(); i++) {
            //This is how you add title panes here
            FXMLLoader requestLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewRequestItem.fxml"));
            AnchorPane request = requestLoader.load();
            Request req = listOfRequests.get(i).getGenericRequest();
            RequestItemController controller = requestLoader.getController();
            String temp = req.getTitle();
            controller.myRequestID = req.getRequestID();
            controller.requestItemTitleLabel.setText(temp);
            controller.requestItemLocationChipView.getChips().addAll(req.getLocation());
            controller.requestItemDescriptionLabel.setText(req.getDescription());
            controller.requestItemCreatorLabel.setText(req.getCreator());

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            Date today = req.getDateNeeded();
            System.out.println(today);
            String dateAsString = df.format(today);
            controller.requestItemDate2BCompletedLabel.setText(dateAsString);
            controller.requestItemRequestTypeLabel.setText(req.getType());
            //controller.myID = i;
            requestList.getChildren().add(request);
            App.lastClickedRequestNumber++;

            switch (req.getType()) {
                case "Maintenance":
                    controller.requestIcon.setContent("M21.67,18.17l-5.3-5.3h-0.99l-2.54,2.54v0.99l5.3,5.3c0.39,0.39,1.02,0.39,1.41,0l2.12-2.12 C22.06,19.2,22.06,18.56,21.67,18.17z M18.84,19.59l-4.24-4.24l0.71-0.71l4.24,4.24L18.84,19.59zM17.34,10.19l1.41-1.41l2.12,2.12c1.17-1.17,1.17-3.07,0-4.24l-3.54-3.54l-1.41,1.41V1.71L15.22,1l-3.54,3.54l0.71,0.71 h2.83l-1.41,1.41l1.06,1.06l-2.89,2.89L7.85,6.48V5.06L4.83,2.04L2,4.87l3.03,3.03h1.41l4.13,4.13l-0.85,0.85H7.6l-5.3,5.3 c-0.39,0.39-0.39,1.02,0,1.41l2.12,2.12c0.39,0.39,1.02,0.39,1.41,0l5.3-5.3v-2.12l5.15-5.15L17.34,10.19z M9.36,15.34 l-4.24,4.24l-0.71-0.71l4.24-4.24l0,0L9.36,15.34L9.36,15.34z");
                case "Laundry":
                    controller.requestIcon.setContent();
                case "Computer":
                    controller.requestIcon.setContent();
                case "Medicine":
                    controller.requestIcon.setContent();
                case "Gift":
                    controller.requestIcon.setContent();
                case "Audio":
                    controller.requestIcon.setContent();
                case "Security":
                    controller.requestIcon.setContent();
                case "Sanitation":
                    controller.requestIcon.setContent();
                case "":
                    controller.requestIcon.setContent();
                case "":
                    controller.requestIcon.setContent();
                case "":
                    controller.requestIcon.setContent();
            }

            /*
            final int index = i;
            controller.viewRequestButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
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

    /**
     * Sends Users to Button Page to add a new request
     * @throws Exception
     */
    @FXML public void handleNewRequestButton() throws Exception {
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/ButtonPageForNewRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }
}
