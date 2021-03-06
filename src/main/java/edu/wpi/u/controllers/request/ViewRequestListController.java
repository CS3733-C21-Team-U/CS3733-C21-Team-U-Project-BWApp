package edu.wpi.u.controllers.request;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.u.App;
import edu.wpi.u.requests.*;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class ViewRequestListController {

    public AnchorPane noItemsGraphic;
    public VBox sampleRequestItem;
    public JFXComboBox<String> typeOption;
    public JFXComboBox<String> assignOption;
    public JFXComboBox<String> resolveOption;
    public JFXButton newRequestButton;
    private Group requests;
    @FXML public VBox newRequestVBox;
    ArrayList<SpecificRequest> listOfRequests = App.requestService.getRequests();
    LinkedList<String> generatedRequests = new LinkedList<>();
    /**
     *Gets each request and attaches it to a NewRequestItem
     * @throws IOException
     */
    public void initialize() throws IOException {

        sampleRequestItem.getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Node> c) {
                if(c.getList().size() < 1 && newRequestVBox.getChildren().size() < 1){//New list after filtering is emppty
                    System.out.println("Request list is empty");//TODO Debug to fix the little request dude not showing up after making a new request
                    noItemsGraphic.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    noItemsGraphic.setVisible(true);
                }else{
                    noItemsGraphic.setPrefHeight(0);
                    noItemsGraphic.setVisible(false);
                }
            }

        });

        generateRequests();

        App.addNewRequestToList.addListener(e->{
            listOfRequests = App.requestService.getRequests();
            try {
                sampleRequestItem.getChildren().add(new RequestListItemContainerController(listOfRequests.get(listOfRequests.size()-1), sampleRequestItem));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        System.out.println("In Init for View Request");
        App.newReqVBox = newRequestVBox;

        ArrayList<SpecificRequest> listOfRequests = App.requestService.getRequests();

        typeOption.getItems().addAll(
                "All", "Maintenance", "Laundry", "Security", "Sanitation", "Computer", "Medical",
                "AudioVisual", "Religious", "Language", "Gift", "Floral", "CovidSurvey");
        typeOption.setValue("All");
        assignOption.getItems().addAll(
                "All", "Assigned to You", "Unassigned");
        assignOption.setValue("All");
        resolveOption.getItems().addAll(
                "All", "Active", "Resolved");
        resolveOption.setValue("All");

        App.VBoxChanged.addListener((observable, oldValue, newValue) -> {
            newRequestVBox = App.newReqVBox;
            if(sampleRequestItem.getChildren().size() < 1 && newRequestVBox.getChildren().size() < 1){//New list after filtering is emppty
                System.out.println("Request list is empty");//TODO Debug to fix the little request dude not showing up after making a new request
                noItemsGraphic.setPrefHeight(Region.USE_COMPUTED_SIZE);
                noItemsGraphic.setVisible(true);
            }else{
                noItemsGraphic.setPrefHeight(0);
                noItemsGraphic.setVisible(false);
            }
            System.out.println("Listener in View Request Running");
        });


    }

    public void generateRequests() {
        for (SpecificRequest request: listOfRequests) {
            try {
                sampleRequestItem.getChildren().add(new RequestListItemContainerController(request, sampleRequestItem));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sends Users to Button Page to add a new request
     * @throws Exception
     */
    @FXML public void handleNewRequestButton() throws Exception {
        if(newRequestVBox.getChildren().size()<=1) {
            //Added same functionality as listener
            noItemsGraphic.setPrefHeight(0);
            noItemsGraphic.setVisible(false);

            newRequestVBox.getChildren().add(new RequestListItemNewController());
            App.newReqVBox = newRequestVBox;

            App.VBoxChanged.set(!App.VBoxChanged.get());
        }
    }

    /**
     * ComboBox functions below
     */
    public void onTypeFilter(){
        App.requestService.requestType.set(typeOption.getSelectionModel().getSelectedItem());
    }
    public void onResolveFilter(){
        App.requestService.resolveStatus.set(resolveOption.getSelectionModel().getSelectedItem());
    }
    public void onAssignFilter(){
        App.requestService.assignedStatus.set(assignOption.getSelectionModel().getSelectedItem());
    }

}
