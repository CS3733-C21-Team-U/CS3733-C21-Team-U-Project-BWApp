package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import edu.wpi.u.App;
import javafx.animation.Interpolator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

import java.io.IOException;

public class NewMainPageController {

    @FXML
    public SVGPath leftMenuHamburger;
    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private JFXDrawer leftMenuDrawer;
    @FXML
    private JFXDrawer serviceRequestDrawer;


    public GesturePane map;

    static final Duration DURATION = Duration.millis(300);

    AnchorPane rightServiceRequestPane;
    AnchorPane leftMenuPane;



    public void initialize() throws IOException {
        leftMenuPane = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/LeftDrawerMenu.fxml"));
        leftMenuDrawer.setSidePane(leftMenuPane);
        leftMenuDrawer.open();

        rightServiceRequestPane= FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/ViewRequest.fxml"));
        serviceRequestDrawer.setSidePane(rightServiceRequestPane);
        serviceRequestDrawer.open();



        ImageView node = new ImageView(String.valueOf(getClass().getResource("/edu/wpi/u/views/Images/FaulknerCampus.png")));
        node.setFitWidth(2987);
        node.setPreserveRatio(true);
        AnchorPane pane = new AnchorPane(node);
        //The black path behind--------------
        App.pathFindingPath = new SVGPath();
        App.pathFindingPath.setContent(App.PathHandling.SVGPathString);
        App.pathFindingPath.setStrokeWidth(5);
        App.pathFindingPath.setStroke(Color.web("#f6c037", 1.0));
        App.pathFindingPath.setStrokeLineJoin(StrokeLineJoin.ROUND);
        App.pathFindingPath.setStrokeLineCap(StrokeLineCap.ROUND);
        //The yellow path behind--------------
        App.pathFindingPath2 = new SVGPath();
        App.pathFindingPath2.setContent(App.PathHandling.SVGPathString);
        App.pathFindingPath2.setStrokeWidth(12);
        App.pathFindingPath2.setStroke(Color.web("#1d1d1d", 1.0));
        App.pathFindingPath2.setStrokeLineJoin(StrokeLineJoin.ROUND);
        App.pathFindingPath2.setStrokeLineCap(StrokeLineCap.ROUND);
        //The loading of the paths
        pane.getChildren().add(App.pathFindingPath);
        pane.getChildren().add(App.pathFindingPath2);
        App.pathFindingPath2.toFront();
        App.pathFindingPath.toFront();
        map = new GesturePane(pane);
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

        App.leftDrawerRoot.addListener((observable, oldValue, newValue)  ->
        {
            try {
                leftMenuPane = FXMLLoader.load(getClass().getResource(newValue));
                leftMenuDrawer.setSidePane(leftMenuPane);
                leftMenuDrawer.open();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });



    }



    @FXML
    public void leftMenuToggle() throws Exception {
        if(App.leftDrawerRoot.getValue().equals("/edu/wpi/u/views/LeftDrawerMenu.fxml")){
            leftMenuDrawer.setPrefSize(80,1000);
            App.leftDrawerRoot.setValue("/edu/wpi/u/views/LeftDrawerMenuSmall.fxml");
        }else{
            App.leftDrawerRoot.setValue("/edu/wpi/u/views/LeftDrawerMenu.fxml");
        }
    }




    @FXML
    public void handleExitButton() {
        App.getInstance().stop();
    }

    @FXML
    public void handleZoomOutButton() {
//        map.currentScaleProperty().setValue(map.getCurrentScale()/1.4);
//        mapView.setFitHeight(mapView.getFitHeight()/1.4);
//        mapView.setFitWidth(mapView.getFitWidth()/1.4);
        Point2D pivotOnTarget = map.targetPointAtViewportCentre();
        // increment of scale makes more sense exponentially instead of linearly
        map.animate(DURATION)
                .interpolateWith(Interpolator.EASE_BOTH)
                .zoomBy(-0.35, pivotOnTarget);
    }

    @FXML
    public void handleZoomInButton() {
//        map.currentScaleProperty().setValue(map.getCurrentScale()*1.4);
//        mapView.setFitHeight(mapView.getFitHeight()*1.4);
//        mapView.setFitWidth(mapView.getFitWidth()*1.4);
        Point2D pivotOnTarget = map.targetPointAtViewportCentre();
        // increment of scale makes more sense exponentially instead of linearly
        map.animate(DURATION)
                .interpolateWith(Interpolator.EASE_BOTH)
                .zoomBy(0.35, pivotOnTarget);
    }
}