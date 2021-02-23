package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import edu.wpi.u.App;
import edu.wpi.u.uiComponents.ZoomableScrollPane;
import javafx.animation.Interpolator;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

import java.io.IOException;
import java.util.Observable;

public class NewMainPageController {

    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private JFXHamburger leftMenuHamburger;
    @FXML
    private JFXDrawer leftMenuDrawer;
    @FXML
    private JFXDrawer serviceRequestDrawer;


    public ImageView mapView;

    static final Duration DURATION = Duration.millis(300);

    AnchorPane rightServiceRequestPane;


    public void initialize() throws IOException {
        AnchorPane leftMenuPane;
        leftMenuPane = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/LeftDrawerMenu.fxml"));
        leftMenuDrawer.setSidePane(leftMenuPane);
        leftMenuDrawer.open();
        rightServiceRequestPane= FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/ViewRequest.fxml"));
        serviceRequestDrawer.setSidePane(rightServiceRequestPane);
        serviceRequestDrawer.open();



        Node node = new ImageView(getClass().getResource("/edu/wpi/u/views/Images/FaulknerCampus.png").toExternalForm());
        App.pane = new AnchorPane(node);
        App.pane.getStyleClass().add("blue");
        App.pane.applyCss();
        GesturePane map = new GesturePane(App.pane);
        map.setMinScale(0.3);
        map.setMaxScale(2);
//        mapView.setFitWidth(4000.0);
//        mapView.setFitHeight(4000.0);
//        mapView.setPreserveRatio(true);
//
//        AnchorPane scrollPaneRoot = new AnchorPane(mapView);
//        ZoomableScrollPane map = new ZoomableScrollPane(scrollPaneRoot);
        map.setPrefWidth(1920);
        map.setPrefHeight(1000);
        map.setFitMode(GesturePane.FitMode.UNBOUNDED);
        map.setScrollMode(GesturePane.ScrollMode.ZOOM);
//        map.setPannable(true);
        mainAnchorPane.getChildren().add(map);
        map.toBack();

        map.setOnMouseClicked(e -> {
            Point2D pivotOnTarget = map.targetPointAt(new Point2D(e.getX(), e.getY()))
                    .orElse(map.targetPointAtViewportCentre());
            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                // increment of scale makes more sense exponentially instead of linearly
                map.animate(DURATION)
                        .interpolateWith(Interpolator.EASE_BOTH)
                        .zoomBy(map.getCurrentScale(), pivotOnTarget);
            } else if (e.getButton() == MouseButton.SECONDARY && e.getClickCount() == 1) {
                map.animate(DURATION)
                        .interpolateWith(Interpolator.EASE_BOTH)
                        .zoomTo(map.getMinScale(), pivotOnTarget);
            }
        });

        App.rightDrawerRoot.addListener((observable, oldValue, newValue)  ->
        {
            try {
                rightServiceRequestPane = FXMLLoader.load(getClass().getResource(newValue));
                serviceRequestDrawer.setSidePane(rightServiceRequestPane);
                serviceRequestDrawer.open();
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