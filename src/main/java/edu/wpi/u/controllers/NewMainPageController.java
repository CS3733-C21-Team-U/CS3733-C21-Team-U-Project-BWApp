package edu.wpi.u.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import javafx.animation.Interpolator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Text;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Observable;

public class NewMainPageController {


    public GesturePane map;

    static final Duration DURATION = Duration.millis(300);
    //    @FXML public SVGPath leftMenuHamburger;
//    @FXML public AnchorPane mainAnchorPane;
//    @FXML public JFXDrawer leftMenuDrawer;
//    @FXML public JFXDrawer serviceRequestDrawer;
//    @FXML public Tab nonActiveValue;
//    @FXMLViewFlowContext
//    private ViewFlowContext context;
    @FXML
    public JFXTabPane mainTabPane;
    public JFXButton openDialogue;
    public JFXDialog dialog;
    public JFXListView listViewDemo;
    public JFXTextField validationFeild;
    public StackPane newMainPageStackPane;

    AnchorPane rightServiceRequestPane;
    AnchorPane leftMenuPane;

    ObservableList<String> listView = FXCollections.observableArrayList("Doesn't work", "For me. Let me know", "if it shows Material Design!", "And not just the default list");


    public void initialize() throws IOException {

        listViewDemo.setItems(listView);

        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Input Required");
        validationFeild.getValidators().add(validator);
        validationFeild.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                validationFeild.validate();
            }
        });

        JFXDatePicker a = new JFXDatePicker();
        LocalDate b = a.getValue();
        mainTabPane.getStylesheets().add("-fx-text-fill: white;");

        this.openDialogue.setOnAction((action) -> {
            this.dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
//            this.dialog.show((StackPane)this.context.getRegisteredObject("ContentPane"));
        });


    }

    public void handleThemeSwitch(ActionEvent actionEvent) {
        App.getInstance().switchTheme();
    }

    public void handleExit(ActionEvent actionEvent) throws IOException {

        JFXDialogLayout dialog = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/ExitDialog.fxml"));
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
                App.getInstance().end();
            }
        });
        button.setStyle("-fx-background-color: #4dadf7");
        content.setActions(button);
        dialog.show();

    }
}




//    @FXML
//    public void leftMenuToggle() throws Exception {
//        if(App.leftDrawerRoot.getValue().equals("/edu/wpi/u/views/LeftDrawerMenu.fxml")){
//            leftMenuDrawer.setPrefSize(80,1000);
//            App.leftDrawerRoot.setValue("/edu/wpi/u/views/LeftDrawerMenuSmall.fxml");
//        }else{
//            App.leftDrawerRoot.setValue("/edu/wpi/u/views/LeftDrawerMenu.fxml");
//        }
//    }
//
//
//    @FXML
//    public void handleZoomOutButton() {
////        map.currentScaleProperty().setValue(map.getCurrentScale()/1.4);
////        mapView.setFitHeight(mapView.getFitHeight()/1.4);
////        mapView.setFitWidth(mapView.getFitWidth()/1.4);
//        Point2D pivotOnTarget = map.targetPointAtViewportCentre();
//        // increment of scale makes more sense exponentially instead of linearly
//        map.animate(DURATION)
//                .interpolateWith(Interpolator.EASE_BOTH)
//                .zoomBy(-0.35, pivotOnTarget);
//    }
//
//    @FXML
//    public void handleZoomInButton() {
////        map.currentScaleProperty().setValue(map.getCurrentScale()*1.4);
////        mapView.setFitHeight(mapView.getFitHeight()*1.4);
////        mapView.setFitWidth(mapView.getFitWidth()*1.4);
//        Point2D pivotOnTarget = map.targetPointAtViewportCentre();
//        // increment of scale makes more sense exponentially instead of linearly
//        map.animate(DURATION)
//                .interpolateWith(Interpolator.EASE_BOTH)
//                .zoomBy(0.35, pivotOnTarget);
//    }
