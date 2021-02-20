package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class NewMainController {
    @FXML
    JFXHamburger leftHamburgerMenu;

    @FXML
    JFXDrawer leftDrawer;

    HamburgerBasicCloseTransition transition = new HamburgerBasicCloseTransition(leftHamburgerMenu);

    public void initialize(URL url, ResourceBundle rb) throws Exception{
        AnchorPane anchor = FXMLLoader.load(getClass().getResource("LeftDrawerMenu.fxml"));
        leftDrawer.setSidePane(anchor);
    }



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
