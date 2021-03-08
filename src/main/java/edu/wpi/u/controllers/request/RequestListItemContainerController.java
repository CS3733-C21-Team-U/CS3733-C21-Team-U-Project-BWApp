package edu.wpi.u.controllers.request;

import edu.wpi.u.App;
import edu.wpi.u.controllers.NewMainPageController;
import edu.wpi.u.requests.Request;
import edu.wpi.u.requests.SpecificRequest;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RequestListItemContainerController extends AnchorPane implements Initializable{

    public SpecificRequest request;
    public Parent expandedNode;
    public Parent collapsedNode;

    public RequestListItemContainerController(SpecificRequest request) throws IOException {
        this.request = request; //MUST BE FIRST!!!

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/request/RequestListItemContainer.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();

        expandedNode = new RequestListItemExpandedController(this);
        collapsedNode = new RequestListItemCollapsedController(this);

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
