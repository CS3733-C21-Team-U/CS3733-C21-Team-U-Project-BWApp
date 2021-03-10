package edu.wpi.u.controllers.request;

import edu.wpi.u.App;
import edu.wpi.u.controllers.NewMainPageController;
import edu.wpi.u.requests.Request;
import edu.wpi.u.requests.SpecificRequest;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RequestListItemContainerController extends AnchorPane implements Initializable{

    public VBox masterList;
    public SpecificRequest request;
    public Parent expandedNode;
    public Parent collapsedNode;
    public Parent editNode;
    public Parent hiddenNode;
    public SimpleBooleanProperty needUpdate = new SimpleBooleanProperty(true);

    public RequestListItemContainerController(SpecificRequest request, VBox sampleRequestItem) throws IOException {
        this.request = request; //MUST BE FIRST!!!
        this.masterList = sampleRequestItem;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/request/RequestListItemContainer.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();
        //"this" is both an anchor pane and controller
        //https://github.com/CS3733-C21-Team-U/CS3733-C21-Team-U-Project-BWApp/pull/226

        expandedNode = new RequestListItemExpandedController(this);
        collapsedNode = new RequestListItemCollapsedController(this);
        editNode = new RequestListItemEditController(this);
        hiddenNode = new RequestListItemHiddenController(this);

        this.getChildren().add(collapsedNode);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void switchToExpanded() {
        //For duration question look at: https://material.io/design/motion/speed.html#controlling-speed
        runAnimation(this,300,100,96,590,collapsedNode, expandedNode);

    }
    public void switchToCollapsed() {
        runAnimation(this,250,80,590,96,expandedNode,collapsedNode);
    }

    public void switchToEdit() {
        runAnimation(this,250,80,590,590,expandedNode,editNode);
    }

    public void switchFromEditToExpanded() {
        runAnimation(this,250,80,590,590, editNode, expandedNode);
    }

    public void switchGoneToCollapsed() {
        System.out.println("Testing gone to collapes");
        this.masterList.getChildren().add(this);
    }

    public void switchCollapsedToGone() {
        System.out.println("Testing collapse to gpme");
        this.masterList.getChildren().remove(this);
    }

    public void runAnimation(RequestListItemContainerController anchor, int totalTimeMS, int fadeOutTimeMS, int startSizePx, int endSizePx, Parent outgoing, Parent incoming){
        //https://material.io/design/motion/the-motion-system.html#container-transform
        //BASED ON "FADE THROUGH TRANSFORM"
        final Animation anim = new Transition() {
            {
                setCycleDuration(Duration.millis(totalTimeMS));
                setInterpolator(Interpolator.EASE_BOTH);
            }
            @Override
            protected void interpolate(double frac) {
                double prefHeight = startSizePx + (endSizePx-startSizePx) *frac;
                anchor.setPrefHeight(prefHeight);
            }
        };
        FadeTransition fadeOut = new FadeTransition(Duration.millis(fadeOutTimeMS),outgoing);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setInterpolator(Interpolator.EASE_IN);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(totalTimeMS-fadeOutTimeMS),incoming);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setInterpolator(Interpolator.EASE_BOTH);

        PauseTransition pause = new PauseTransition(Duration.millis(1)); //TO AVOID FLASHING OF INCOMING

        fadeOut.setOnFinished(event -> {
            anchor.getChildren().clear();
            incoming.setVisible(false);
            anchor.getChildren().add(incoming);
            fadeIn.playFromStart();
            pause.play();
        });


        pause.setOnFinished(event -> {
            incoming.setVisible(true);
        });

        anim.play();
        fadeOut.play();
    }

}
