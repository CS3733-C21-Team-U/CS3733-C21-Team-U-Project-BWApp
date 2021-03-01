package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.models.MapService;
import javafx.animation.Interpolator;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

import java.io.IOException;
import java.util.Collection;
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
    AnchorPane pane = new AnchorPane();

    /**
     * Initializes the admin map screen with map zoom, and all node and edge placement
     * @throws IOException
     */
    public void initialize() throws IOException {
        // Loading the map
        ImageView node = new ImageView(String.valueOf(getClass().getResource("/edu/wpi/u/views/Images/FaulknerCampus.png")));
        node.setFitWidth(2987);
        node.setPreserveRatio(true);
        pane.getChildren().add(node);

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
        App.mapService.getNodes().stream().forEach(n -> {
            try {
                placeNodes(n);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        App.mapService.getEdges().stream().forEach(e -> placeEdges(e));
        //Stream<Edge> edgeStream = App.mapService.getEdges().parallelStream();

//        for (Node n :  App.mapService.getNodes()){
//            this.placeNodes(n);
//        }
//        nodeStream.forEach(n -> placeNodes(n));
        // edgeStream.forEach(n ->placeEdges(n));

    } // End of initialize

    /**
     * Sets the position, radius, id, fill, etc., of the node, and sets its action when clicked
     * @param n - Node that is being place
     * @throws IOException
     */
    public void placeNodes(Node n) throws IOException{
            Circle node = new Circle();
            node.setCenterX(n.getCords()[0]);
            node.setCenterY(n.getCords()[1]);
            node.setRadius(7.0);
            node.setId(n.getNodeID());
            node.toFront();
            node.setFill(Paint.valueOf("Black"));
            node.setVisible(true);
            node.setOnMouseClicked(event -> {
                try {
                    handleNodeClicked(n);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        pane.getChildren().add(node);
    }

    /**
     * Sets the x vector, y vector, and other positional fields of the edge, and sets its action when clicked
     * @param ed - Edge that is clicked (variable named e is reserved for the exception thrown)
     */
    public void placeEdges(Edge ed){
        double xdiff = ed.getEndNode().getCords()[0]-ed.getStartNode().getCords()[0];
        double ydiff = ed.getEndNode().getCords()[1]-ed.getStartNode().getCords()[1];
        Line edge = new Line();
        edge.setLayoutX(ed.getStartNode().getCords()[0]);
        edge.setStartX(0);
        edge.setLayoutY(ed.getStartNode().getCords()[1]);
        edge.setStartY(0);
        edge.setEndX(xdiff);
        edge.setEndY(ydiff);
        edge.setId(ed.getEdgeID());
        edge.toFront();
        edge.setFill(Paint.valueOf("Black"));
        edge.setVisible(true);

        edge.setOnMouseClicked(event -> {
            try {
                handleEdgeClicked(ed);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        pane.getChildren().add(edge);

    }

    /**
     * Function called when and edge is clicked on. This brings up the context menu.
     * @param e - Edge that is clicked on
     * @throws IOException
     */
    public void handleEdgeClicked(Edge e) throws IOException {
        System.out.println("You clicked on an edge");
        FXMLLoader edgeContextMenu = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/EdgeContextMenu.fxml"));
        AnchorPane contextAnchor = new AnchorPane();
        contextAnchor = edgeContextMenu.load();
        EdgeContextMenuController controller = edgeContextMenu.getController();

        contextAnchor.setLayoutX(e.getEndNode().getCords()[0]);
        contextAnchor.setLayoutY(e.getEndNode().getCords()[1]);

        pane.getChildren().add(contextAnchor);
    }

    /**
     * Function called when a node is clicked. This brings up the context menu.
     * @param n - Node that is clicked on
     * @throws IOException
     */
    public void handleNodeClicked(Node n) throws IOException {
        System.out.println("You clicked on a node");
        FXMLLoader nodeContextMenu = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NodeContextMenu.fxml"));
        AnchorPane contextAnchor = new AnchorPane();
        contextAnchor = nodeContextMenu.load();
        NodeContextMenuController controller = nodeContextMenu.getController();

        contextAnchor.setLayoutX(n.getCords()[0]);
        contextAnchor.setLayoutY(n.getCords()[1]);

        pane.getChildren().add(contextAnchor);
    }

}