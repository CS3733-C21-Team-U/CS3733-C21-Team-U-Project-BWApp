package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import edu.wpi.u.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class NewMainPageController {


    @FXML
    JFXHamburger leftMenuHamburger;
    @FXML
    JFXDrawer leftMenuDrawer;
    @FXML
    JFXDrawer serviceRequestDrawer;

    AnchorPane rightServiceRequestPane;


    public void initialize() throws IOException {
        AnchorPane leftMenuPane;
        leftMenuPane = FXMLLoader.load(getClass().getResource("../views/LeftDrawerMenu.fxml"));
        leftMenuDrawer.setSidePane(leftMenuPane);
        rightServiceRequestPane= FXMLLoader.load(getClass().getResource("../views/ViewServiceRequests.fxml"));
        serviceRequestDrawer.setSidePane(rightServiceRequestPane);
        serviceRequestDrawer.open();

        App.rightDrawerRoot.addListener((observable, oldValue, newValue)  ->
        {
            try {
                rightServiceRequestPane = FXMLLoader.load(getClass().getResource(newValue));
                serviceRequestDrawer.setSidePane(rightServiceRequestPane);


                /*
                serviceRequestDrawer.close();
                serviceRequestDrawer.onDrawerClosedProperty(new EventHandler<JFXDrawerEvent>() {

                    public void handle(JFXDrawerEvent me) {
                        System.out.println("Mouse exited");
                    }
                });
*/
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }



    @FXML
    public void leftMenuToggle() throws Exception {
        if(leftMenuDrawer.isOpened()){
            leftMenuDrawer.close();
        } else{
            leftMenuDrawer.open();
        }

    }


    //Listener here for the global drawerstare variable
    @FXML
    public void rightDrawerSwitchEdit() throws Exception {


        serviceRequestDrawer.close();
        AnchorPane rightServiceRequestPane = FXMLLoader.load(getClass().getResource("../views/EditServiceRequests.fxml"));
        serviceRequestDrawer.setSidePane(rightServiceRequestPane);
        serviceRequestDrawer.open();
    }

    @FXML
    public void handleExitButton() {
        App.getInstance().stop();
    }

}