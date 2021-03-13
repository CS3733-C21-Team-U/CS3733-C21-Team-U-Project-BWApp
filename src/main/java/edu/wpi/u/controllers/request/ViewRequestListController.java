package edu.wpi.u.controllers.request;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.u.App;
import edu.wpi.u.requests.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewRequestListController {

    public AnchorPane noItemsGraphic;
    public VBox sampleRequestItem;
    public JFXComboBox<String> typeOption;
    public JFXComboBox<String> assignOption;
    public JFXComboBox<String> resolveOption;
    private Group requests;
    @FXML public VBox newRequestVBox;
    ArrayList<SpecificRequest> listOfRequests = App.requestService.getRequests();

    /**
     *Gets each request and attaches it to a NewRequestItem
     * @throws IOException
     */
    public void initialize() throws IOException {

        sampleRequestItem.getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Node> c) {
                if(c.getList().size() < 2){//New list after filtering is emppty
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

        App.requestRedrawFlag.addListener(e->{
            sampleRequestItem.getChildren().clear();
            generateRequests();
        });

        System.out.println("In Init for View Request");
        App.newReqVBox = newRequestVBox;

        ArrayList<SpecificRequest> listOfRequests = App.requestService.getRequests();

        typeOption.getItems().addAll(
                "All", "Maintenance", "Laundry", "Security", "Sanitation", "Computer", "Medical",
                "AudioVisual", "Religious", "Language", "Gift", "Floral", "CovidSurvey");
        assignOption.getItems().addAll(
                "All", "Assigned to You", "Unassigned");
        resolveOption.getItems().addAll(
                "All", "Active", "Resolved");

        App.VBoxChanged.addListener((observable, oldValue, newValue) -> {
            //newRequestVBox.getChildren().setAll(App.newReqVBox.getChildren());
            newRequestVBox = App.newReqVBox;
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
        if(newRequestVBox.getChildren().size()<1) {
            newRequestVBox.getChildren().add(new RequestListItemChooseNewController());

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
