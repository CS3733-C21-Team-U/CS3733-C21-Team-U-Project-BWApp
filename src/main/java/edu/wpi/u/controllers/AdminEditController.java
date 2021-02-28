package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.models.MapService;
import javafx.animation.Interpolator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

public class AdminEditController {


    public GesturePane map;

    static final Duration DURATION = Duration.millis(300);
    @FXML public SVGPath leftMenuHamburger;
    @FXML public AnchorPane mainAnchorPane;
    @FXML public JFXDrawer leftMenuDrawer;
    @FXML public JFXDrawer serviceRequestDrawer;

    AnchorPane rightServiceRequestPane;
    AnchorPane leftMenuPane;



    public void initialize() throws IOException {
        // Loading the map
        ImageView node = new ImageView(String.valueOf(getClass().getResource("/edu/wpi/u/views/Images/FaulknerCampus.png")));
        node.setFitWidth(2987);
        node.setPreserveRatio(true);
        AnchorPane pane = new AnchorPane(node);

        map = new GesturePane(pane);
        map.setMinScale(0.3);
        map.setMaxScale(2);
        map.centreOn(new Point2D(700, 4000));
        map.zoomTo(0.5, map.targetPointAtViewportCentre());

        map.setPrefWidth(1920);
        map.setPrefHeight(1000);
        map.setFitMode(GesturePane.FitMode.UNBOUNDED);
        map.setScrollMode(GesturePane.ScrollMode.ZOOM);

        mainAnchorPane.getChildren().add(map);
        map.toBack();



        // Click and scroll map view functionality
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

        // Creating nodes
        Stream<Node> nodeStream = App.mapService.getNodes().parallelStream();
        //Stream<Edge> edgeStream = App.mapService.getEdges().parallelStream();

        for (Node n :  App.mapService.getNodes()){
            this.placeNodes(n);
        }
        //nodeStream.forEach(n -> placeNodes(n));
        // edgeStream.forEach(n ->placeEdges(n));

    } // End of initialize

    public void placeNodes(Node n){
        try {
            Circle node = new Circle();
            node.setCenterX(n.getCords()[0]);
            node.setCenterY(n.getCords()[1]);
            node.setRadius(500.0);
            node.setId(n.getNodeID());
            node.toFront();
            node.setFill(Paint.valueOf("Black"));
            node.setVisible(true);
            mainAnchorPane.getChildren().add(node);
        }catch (Exception e){
            System.out.println(n.getNodeID());
            throw e;
        }

    }




}