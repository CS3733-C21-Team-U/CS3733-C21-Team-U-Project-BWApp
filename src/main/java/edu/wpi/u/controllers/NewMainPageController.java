package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import edu.wpi.u.App;
import edu.wpi.u.uiComponents.ZoomableScrollPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import net.kurobako.gesturefx.GesturePane;

import java.io.IOException;

public class NewMainPageController {

    @FXML
    public AnchorPane mainAnchorPane;
    @FXML
    public JFXHamburger leftMenuHamburger;
    @FXML
    public JFXDrawer leftMenuDrawer;
    @FXML
    public JFXDrawer serviceRequestDrawer;

    public ImageView mapView;

    AnchorPane rightServiceRequestPane;


    public void initialize() throws IOException {
        AnchorPane leftMenuPane;
        leftMenuPane = FXMLLoader.load(getClass().getResource("../views/LeftDrawerMenu.fxml"));
        leftMenuDrawer.setSidePane(leftMenuPane);
        leftMenuDrawer.open();
        rightServiceRequestPane= FXMLLoader.load(getClass().getResource("../views/ViewRequest.fxml"));
        serviceRequestDrawer.setSidePane(rightServiceRequestPane);
        serviceRequestDrawer.open();



        Node node = new ImageView(getClass().getResource("/edu/wpi/u/views/Images/FaulknerCampus.png").toExternalForm());
        GesturePane map = new GesturePane(node);

//        mapView.setFitWidth(4000.0);
//        mapView.setFitHeight(4000.0);
//        mapView.setPreserveRatio(true);
//
//        AnchorPane scrollPaneRoot = new AnchorPane(mapView);
//        ZoomableScrollPane map = new ZoomableScrollPane(scrollPaneRoot);
        map.setPrefWidth(1420);
        map.setPrefHeight(1000);
        map.setFitMode("FIT");
//        map.setPannable(true);
        mainAnchorPane.getChildren().add(map);
        map.toBack();

        App.rightDrawerRoot.addListener((observable, oldValue, newValue)  ->
        {
            try {
                rightServiceRequestPane = FXMLLoader.load(getClass().getResource(newValue));
                serviceRequestDrawer.setSidePane(rightServiceRequestPane);


            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }



    @FXML
    public void leftMenuToggle() throws Exception {
        if(leftMenuDrawer.isOpened()){
            leftMenuDrawer.close();
        } else{
            leftMenuDrawer.open();
        }

    }




    @FXML
    public void handleExitButton() {
        App.getInstance().stop();
    }

    @FXML
    public void handleZoomOutButton() {
        mapView.setFitHeight(mapView.getFitHeight()/1.4);
        mapView.setFitWidth(mapView.getFitWidth()/1.4);
    }

    @FXML
    public void handleZoomInButton() {
        mapView.setFitHeight(mapView.getFitHeight()*1.4);
        mapView.setFitWidth(mapView.getFitWidth()*1.4);
    }
}