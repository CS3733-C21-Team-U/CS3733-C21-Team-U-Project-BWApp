package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleNode;
import edu.wpi.u.App;
import javafx.fxml.FXML;

import com.jfoenix.controls.JFXToggleNode;
import edu.wpi.u.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class LeftDrawerMenuController {

    @FXML
    JFXToggleNode navigationButton;
    @FXML
    JFXToggleNode serviceRequestButton;



    public void handleNavigationButton() throws Exception {
        //Switch to a new right drawer
        App.rightDrawerRoot.set( "../views/PathfindingRightPage.fxml");
    }

    public void handleServiceRequestButton() throws Exception {
        //Switch to a new right drawer
        App.rightDrawerRoot.set( "../views/ViewRequest.fxml");
    }











    public JFXToggleNode toPathPlanningBtn;
    @FXML public Rectangle flair1;
    @FXML public Rectangle flair2;
    @FXML public Rectangle flair3;
    @FXML public Rectangle flair4;

    @FXML public Label text1;
    @FXML public Label text2;
    @FXML public Label text3;
    @FXML public Label text4;

    @FXML public JFXToggleNode toggle1;
    @FXML public JFXToggleNode toggle2;
    @FXML public JFXToggleNode toggle3;
    @FXML public JFXToggleNode toggle4;

    @FXML
    public void initialize() throws IOException{
//        toPathPlanningBtn.onActionProperty()
//        toggle4.setDisable(true);
        setTextColor(-1);
//        setRectVisibility(-1);
//        toggle4.setDisableAnimation(true);
    }


    private void setRectVisibility(int activeRect){
        flair1.setVisible(false);
        flair2.setVisible(false);
        flair3.setVisible(false);
        flair4.setVisible(false);
        switch (activeRect){
            case 1: flair1.setVisible(true); break;
            case 2: flair2.setVisible(true); break;
            case 3: flair3.setVisible(true); break;
            case 4: flair4.setVisible(true); break;

        }
    }

    private void setTextColor(int activeText){
        text1.setTextFill(Color.web("#8a93a0"));
        text2.setTextFill(Color.web("#8a93a0"));
        text3.setTextFill(Color.web("#8a93a0"));
        text4.setTextFill(Color.web("#8a93a0"));
        switch (activeText){
            case 1: text1.setTextFill(Color.web("#2e5bff")); break;
            case 2: text2.setTextFill(Color.web("#2e5bff")); break;
            case 3: text3.setTextFill(Color.web("#2e5bff")); break;
            case 4: text4.setTextFill(Color.web("#2e5bff")); break;

        }
    }


    public void handleChangeToPathPlanning(ActionEvent actionEvent) {
        App.rightDrawerRoot.set( "../views/PathfindingRightPage.fxml");
        setRectVisibility(1);
        setTextColor(1);
    }

    public void handleChangeToRequests(ActionEvent actionEvent) {
        App.rightDrawerRoot.set( "../views/ViewRequest.fxml");
        setRectVisibility(2);
        setTextColor(2);
    }

    public void handleChangeToAdmin(ActionEvent actionEvent) {
        App.rightDrawerRoot.set( "../views/AdminTools.fxml");
        setRectVisibility(3);
        setTextColor(3);
    }

    public void handleChangeToSettings(ActionEvent actionEvent) {
        App.rightDrawerRoot.set( "../views/Settings.fxml");
        setRectVisibility(4);
        setTextColor(4);
    }
}
