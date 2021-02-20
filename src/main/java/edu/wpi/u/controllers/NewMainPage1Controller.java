package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.fxml.FXML;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewMainPage1Controller {


    @FXML
    JFXHamburger leftMenuHamburger;

    @FXML
    JFXDrawer leftMenuDrawer;


    public void initialize() throws IOException {
        AnchorPane leftMenuPane;
        leftMenuPane = FXMLLoader.load(getClass().getResource("edu/wpi/u/views/LeftDrawerMenu.fxml"));
        leftMenuDrawer.setSidePane(leftMenuPane);
    }

    @FXML
    public void leftMenuToggle() throws Exception {
        if(leftMenuDrawer.isOpened()){
            leftMenuDrawer.close();
        } else{
            leftMenuDrawer.open();
        }

    }







}