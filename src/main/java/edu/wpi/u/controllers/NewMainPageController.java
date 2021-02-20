package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class NewMainPageController {

    public void initialize() throws Exception{
        AnchorPane anchor = FXMLLoader.load(getClass().getResource("LeftDrawerMenu.fxml"));
        leftDrawer.setSidePane(anchor);
    }
    @FXML
    JFXHamburger leftHamburgerMenu;

    @FXML
    JFXDrawer leftDrawer;

    HamburgerBasicCloseTransition transition = new HamburgerBasicCloseTransition(leftHamburgerMenu);






    @FXML
    public void menuButtonPressed() throws Exception {
        transition.setRate(transition.getRate()*-1);
        transition.play();
        if(leftDrawer.isOpened()){
            leftDrawer.close();
        } else {
            leftDrawer.open();
        }

    }











}
