package edu.wpi.u.controllers.request;

import animatefx.animation.FadeInDown;
import animatefx.animation.FadeOut;
import animatefx.animation.FadeOutDown;
import animatefx.animation.FadeOutDownBig;
import edu.wpi.u.App;
import edu.wpi.u.controllers.NewMainPageController;
import edu.wpi.u.requests.Request;
import edu.wpi.u.requests.SpecificRequest;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
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
        this.getChildren().add(expandedNode);
        final RequestListItemContainerController temp = this;
        final Animation anim = new Transition() {
            {
                setCycleDuration(Duration.millis(250));
            }
            @Override
            protected void interpolate(double frac) {
                double prefHeight = 96.0 + (590.0-96.0)*frac;

                temp.setPrefHeight(prefHeight);
            }
        };
        anim.setOnFinished(event -> {
                temp.getChildren().remove(collapsedNode);
        });
        FadeTransition fadeOut = new FadeTransition(Duration.millis(150),collapsedNode);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(250),expandedNode);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        fadeIn.play();
        fadeOut.play();
        anim.play();

    }
    public void switchToCollapsed() {
        this.getChildren().add(collapsedNode);
        final RequestListItemContainerController temp = this;
        final Animation anim = new Transition() {
            {
                setCycleDuration(Duration.millis(250));
            }
            @Override
            protected void interpolate(double frac) {
                double prefHeight = 590 - (590-96) *frac;
                temp.setPrefHeight(prefHeight);
            }
        };
        anim.setOnFinished(event -> {
            temp.getChildren().remove(expandedNode);
        });
        FadeTransition fadeOut = new FadeTransition(Duration.millis(150),expandedNode);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(250),collapsedNode);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        fadeIn.play();
        fadeOut.play();
        anim.play();
    }
}
